package com.atguigu.mapper;

import com.atguigu.pojo.User;

import java.util.List;

/**
 * @Description
 * @Author Austin
 * @Date 2020/11/30 10:19
 */
public interface UserMapper {
    /**
     * 查询 sql中的全部数据
     */
    List<User> selectUserAll();

    /**
     * 通过用户 id 查询一条用户信息
     * @param id
     * @return
     */
    public User selectUserById(Integer id);

    /**
     * 通过用户 id 修改一条用户信息
     * 增、删、改，一般不需要返回值，加上一般是 int
     * 返回 int 是受影响的行数（修改多少行，返回多少行）
     * @param user
     * @return
     */
    public int updateUserById(User user);

    /**
     * 通过用户 id 删除一条用户信息
     * @param id
     * @return
     */
    public int deleteUserById(Integer id);

    /**
     * 过用户 id 添加一条用户信息
     * @param user
     * @return
     */
    public int insertUser(User user);
}
