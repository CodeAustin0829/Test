package com.atguigu.test;

import com.atguigu.mapper.UserMapper;
import com.atguigu.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Description
 * @Author Austin
 * @Date 2020/11/30 10:32
 */
public class UserMapperTest01 {
    @Test
    public void selectUserAll() throws IOException {
        /*
        （1）第一步：加载 mybatis 全局配置文件（也就是 mybatis-config.xml），得到配置文件流
        【有可能找不到配置文件，抛出异常IOException】
        */
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        //（2）第二步：构建流对象，得到 SqlSessionFactory 工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        /*
        （3）第三步：
        从 SqlSessionFactory 工厂中获取 SqlSession 实例，来直接执行已经映射的sql语句
        sqlSession：当java与db产生连接时创建的会话【获取和数据库的一次会话】
        相当于JDBC中的获取 Connection：
        Connection conn = DriverManager.getConnection(url, username, password);
        */
        SqlSession sqlSession = sessionFactory.openSession();

        //（4）第四步：使用SqlSession操作数据库，获取到dao接口的实现
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> list = userMapper.selectUserAll();
        list.forEach(user -> {
            System.out.println(user);
            //输出结果：User{id=1, lastName='Austin', sex=1}
        });
        //（5）第五步：关闭会话
        sqlSession.close();
    }
}
