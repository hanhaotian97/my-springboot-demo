package com.hht.myspringbootdemo.service;

import com.hht.myspringbootdemo.entity.User;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 测试用表(User)表服务接口
 *
 * @author hanhaotian
 * @since 2019-11-08 14:50:59
 */
public interface UserService {

    boolean transactionTest();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void sendSms();
}