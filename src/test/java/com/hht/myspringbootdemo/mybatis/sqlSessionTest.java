package com.hht.myspringbootdemo.mybatis;

import com.hht.myspringbootdemo.BaseTest;
import com.hht.myspringbootdemo.dao.UserDao;
import com.hht.myspringbootdemo.entity.User;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <br/>Description : 描述
 * <br/>CreateTime : 2023/10/23
 * @author hanhaotian
 */
public class sqlSessionTest extends BaseTest {
    @Resource
    private UserDao userDao;

    /**
     * 开启事务，测试一级缓存效果
     * 一级缓存在 mybatis 中默认是开启的并且是 session 级别，它的作用域为一次 sqlSession 会话。
     * springboot使用了hikariCP连接池每次查询完自动提交, 所以需要加上事务让两次查询都在同一个 sqlSession 中
     * 缓存命中顺序：二级缓存---> 一级缓存---> 数据库
     **/
    @Test
    @Transactional(rollbackFor = Throwable.class)
    public void testFistCache() {
        System.out.println("----start----");
        // 第一次查询，缓存到一级缓存
        User user = userDao.queryById(1);
        // 第二次查询，直接读取一级缓存
        User user1 = userDao.queryById(1);
        System.out.println(user);
        System.out.println(user1);
        System.out.println("----end----");
    }

    /**
     * 测试二级缓存效果
     * 作用域是 namespace，需要*Mapper.xml开启二级缓存, 配置<cache></cache>
     * 多表级联查询需要加上标签<cache-ref></cache-ref>
     *
     * 分布式部署下本地缓存很容易产生脏数据, 实际上二级缓存用处不大
     **/
    @Test
    public void testSecondCache() {
        System.out.println("----start----");
        User user = userDao.queryById(1);
        User user1 = userDao.queryById(1);
        System.out.println(user);
        System.out.println(user1);
        System.out.println("----end----");
    }

}
