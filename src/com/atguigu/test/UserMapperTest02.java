package com.atguigu.test;

import com.atguigu.mapper.UserMapper;
import com.atguigu.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 测试增、删、改、查
 * @Author Austin
 * @Date 2020/11/30 16:20
 */
public class UserMapperTest02 {

    //提升变量的作用域
    InputStream inputStream = null;
    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;
    UserMapper userMapper = null;

    @Before
    public void init() throws IOException {
        /*
        提取的四个步骤，但是还不行，还得将局部变量的作用域，提升为成员变量，方便在其它方法【CRUD】里面调用：

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        */
        inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
         /*
        提交事务方式二：在执行openSession()时，将参数设定为true（因为默认是false）
        sqlSession = sqlSessionFactory.openSession();
        */
        sqlSession = sqlSessionFactory.openSession(true);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }


    /**
     * 测试通过用户 id 查询用户信息
     *
     * @throws IOException
     */
    @Test
    public void selectUserById() throws IOException {
        /*
        （1）第一步：加载 mybatis 核心配置文件（也就是mybatis-config.xml）
        有可能找不到配置文件，抛出异常
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        （2）第二步：构建流对象得到 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        （3）第三步：从工厂中得到 SqlSession
        sqlSession:当java与db产生连接时创建的会话
        用于执行SQL :true 表示自动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession();

        （4）第四步：将会话放入接口
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        因为增、删、改都使用以上四步，提取到注解 "@Before" 中（@Before 在 @Test 之前执行）
        然后将关闭会话，提取到注解 "@After" 中（@After 在 @Test 之后执行）
        */

        //（5）调用接口中的方法：public User selectUserById(Integer id);
        User user = userMapper.selectUserById(1);
        System.out.println(user);//输出结果：User{id=1, lastName='Austin', sex=1}

        /*
        User user = userMapper.selectUserById(2);
        System.out.println(user);//输出结果：null（因为数据库中没有 id 为 2 的数据）
        */
    }

    /**
     * 测试通过用户 id 修改一条用户信息
     * 增、删、改需要提交事务（查询不需要）
     */
    @Test
    public void updateUserById() {
        int result = userMapper.updateUserById(new User(1, "Austin", 1));
        System.err.println(result);
        /*
        以上两行代码执行后，SQL数据库中并未受影响，也就是 id 为 1 的数据，在SQL数据库中并未修改
        想要在数据库中修改，需要提交事务
         */

        /*
        提交事务：有两个方式
        方式一：【手动提交事务】
        sqlSession.commit();
         */
        //或者写为：sqlSession.commit(true); 此处的true表示事务提交并刷新当前Connection


        /*
        方式二：【自动提交事务】
        在执行openSession()时，将参数设定为true（因为默认是false）
         */
    }


    /**
     * 测试通过用户 id 删除一条用户信息
     * 增、删、改需要提交事务（查询不需要）
     */
    @Test
    public void deleteUserById(){
        int result = userMapper.deleteUserById(1);
        System.out.println(result);
        //输出结果：1（表示受影响的行数是1，事务提交后，数据库的表中， id 为 1 的数据也被删除）
    }

    @Test
    public void insertUser(){
        int result = userMapper.insertUser(new User(1, "Austin", 1));
        System.out.println(result);
    }

    @After
    public void destory() {
        //关闭会话
        sqlSession.close();
    }
}
