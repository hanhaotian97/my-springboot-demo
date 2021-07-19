package com.hht.myspringbootdemo.File;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


/**
 * <br/>Description : 几种文件拷贝方式的性能比较
 * <p>
 * 用户态与内核态切换的三种方式 :
 * 1、发生系统调用时
 * 这是处于用户态的进程主动请求切换到内核态的一种方式。用户态的进程通过系统调用申请使用操作系统提供的系统调用服务例程来处理任务。
 * 而系统调用的机制，其核心仍是使用了操作系统为用户特别开发的一个中断机制来实现的，即软中断。
 * 2、产生异常时
 * 当CPU执行运行在用户态下的程序时，发生了某些事先不可知的异常，这时会触发由当前运行的进程切换到处理此异常的内核相关的程序中，也就是转到了内核态，如缺页异常。
 * 3、外设产生中断时
 * 当外围设备完成用户请求的操作后，会向CPU发出相应的中断信号，这时CPU会暂停执行下一条即将要执行的指令转而去执行与中断信号对应的处理程序，
 * 如果先前执行的指令是用户态下的程序，那么这个转换的过程自然也就发生了由用户态到内核态的切换。比如硬盘读写操作的完成，系统会切换到硬盘读写的中断处理程序中执行后续操作等。
 * <br/>CreateTime : 2021/7/11
 * @author hanhaotian
 */
public class FileCopyDemo {

    public static final String source = "/Users/hanhaotian/Documents/testImg/awkDemo/demo1.txt";
    public static final String dest = "/Users/hanhaotian/Documents/testImg/demo1.txt";

    public static void main(String[] args) throws IOException {
        OldFileCopy();
        deleteFile();

        MmapFileCopy();
        deleteFile();

        SendFileCopy();
        deleteFile();

        SendFileCopy2();
        deleteFile();

        long start = System.currentTimeMillis();
        FileUtils.copyFile(new File(source), new File(dest));
        System.out.println("Apache.commons.io.fileUtil拷贝方式, 耗时：" + (System.currentTimeMillis() - start));
        deleteFile();
    }

    private static void deleteFile() {
        File file = new File(dest);
        long length = file.length();
        if (FileUtils.deleteQuietly(file)) {
            System.out.println("该文件已被删除:" + dest + ", 文件大小为:" + length + "\n");
        }
    }


    private static void OldFileCopy() {
        try {
            FileInputStream inputStream = new FileInputStream(source);
            FileOutputStream outputStream = new FileOutputStream(dest);

            long start = System.currentTimeMillis();
            byte[] buff = new byte[4096];
            long read = 0, total = 0;
            while ((read = inputStream.read(buff)) >= 0) {
                total += read;
                outputStream.write(buff);
            }
            outputStream.flush();
            System.out.println("传统拷贝方式, 耗时：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * mmap 就是在用户态直接引用文件句柄，也就是用户态和内核态共享内核态的数据缓冲区，此时数据不需要复制到用户态空间。当应用程序往 mmap 输出数据时，此时就直接输出到了内核态数据，如果此时输出设备是磁盘的话，会直接写盘（flush间隔是30秒）。
     * 很多工具都使用了mmap, 比如kafka的写入消息, redis的aof持久化落盘, nginx的主线程向工作线程发信号
     */
    private static void MmapFileCopy() {
        try {
            FileChannel sourceChannel = new RandomAccessFile(source, "rw").getChannel();
            FileChannel destChannel = new RandomAccessFile(dest, "rw").getChannel();
            long start = System.currentTimeMillis();
            MappedByteBuffer map = destChannel.map(FileChannel.MapMode.READ_WRITE, 0, sourceChannel.size());
            sourceChannel.write(map);
            map.flip();

            System.out.println("mmap拷贝方式, 耗时：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对于sendfile 而言，数据不需要在应用程序做业务处理，仅仅是从一个 DMA 设备传输到另一个 DMA设备。 此时数据只需要复制到内核态，用户态不需要复制数据，并且也不需要像 mmap 那样对内核态的数据的句柄（文件引用）。
     * 很多地方都是用了transferTo减少上下文切换, 比如kafka的读取消息, tomcat内部的文件拷贝,
     */
    private static void SendFileCopy() {
        try {
            FileChannel sourceChannel = new RandomAccessFile(source, "rw").getChannel();
            FileChannel destChannel = new RandomAccessFile(dest, "rw").getChannel();
            long start = System.currentTimeMillis();

            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
            System.out.println("sendFile拷贝方式-transferTo, 耗时：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * transferTo 是将当前通道数据写到另一个通道, 对象是当前通道, 所以我们不用考虑另一个通道的什么, 我就把文件数据写给你就行了,
     * 而 transferFrom 是从另一个通道拿数据到当前通道, 在复制文件的情况下, 新建立的当前通道是空白的, 我们只知道要写到哪个文件,但是不知道文件的大小, 所以默认每一次就从0开始写入,
     * 从而导致文件最后的大小是线程分配大小. 所以在建立文件的同时,我们就给它指定一个大小, 然后每一次从指定的位置开始写入, 那么就可以完整的复制一个文件了
     */
    private static void SendFileCopy2() {
        try {
            RandomAccessFile srcRaf = new RandomAccessFile(source, "rw");
            FileChannel sourceChannel = srcRaf.getChannel();
            RandomAccessFile destRaf = new RandomAccessFile(FileCopyDemo.dest, "rw");
            destRaf.setLength(srcRaf.length());
            FileChannel destChannel = destRaf.getChannel();
            long start = System.currentTimeMillis();

            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
            System.out.println("sendFile拷贝方式-transferFrom, 耗时：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
