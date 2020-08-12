package com.hht.myspringbootdemo.juc;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 不安全的SimpleDateFormat
 * <br/>CreateTime 2020/8/11
 */
public class UnsafeSimpleDateFormatDemo {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 使用threadLocal解决 SimpleDateFormat的线程不安全问题
     */
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * SimpleDateFormat的parse()方法中，calb.establish(calendar).getTime() 其使用了父类的calendar对象，而CalendarBuilder的establish方法并不是一个原子操作。
     * 这也就导致了在多线程执行过程中，establish方法的原子性无法保证，也就导致了parse()方法报错。
     */
    @Test
    public void test() {
        while (true) {
            poolExecutor.execute(() -> {
                SimpleDateFormat simpleDateFormat = threadLocal.get();
                String dateString = simpleDateFormat.format(new Date());
                try {
                    Date parseDate = simpleDateFormat.parse(dateString);
                    String dateString2 = simpleDateFormat.format(parseDate);
                    System.out.println("dateString equal dateString2? : " + dateString.equals(dateString2) + " , dateString: " + dateString + "\t parseDate: " + parseDate + "\t dateString2: " + dateString2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void test2() throws ParseException {
        SimpleDateFormat simpleDateFormat = threadLocal.get();
        String dateString = simpleDateFormat.format(new Date());
        Date parseDate = simpleDateFormat.parse(dateString);
        String dateString2 = simpleDateFormat.format(parseDate);
        System.out.println("dateString: " + dateString + "\t parseDate: " + parseDate + "\t dateString2: " + dateString2);
    }


    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 使用线程安全的LocalDateTime
     */
    @Test
    public void safeDateTimeFormatter() {
        //格式化日期
        LocalDateTime localDateTime = LocalDateTime.now();
        String strDate = localDateTime.format(dtf);
        // 解析日期
        LocalDateTime localDateTime2 = LocalDateTime.parse(strDate, dtf);
        String strDate2 = localDateTime.format(dtf);

        System.out.println("strDate equal strDate2? : " + strDate.equals(strDate2) + " , localDateTime: " + localDateTime + "\t localDateTime2: " + localDateTime2 + "\t strDate: " + strDate + "\t strDate2: " + strDate2);
    }

    /**
     * 使用线程安全的LocalDateTime
     */
    @Test
    public void test4() {
        while (true) {
            poolExecutor.execute(this::safeDateTimeFormatter);
        }
    }
}
