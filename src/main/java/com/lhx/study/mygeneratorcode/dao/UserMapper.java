package com.lhx.study.mygeneratorcode.dao;

import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.vo.request.account.AccountListReqVo;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByEntity(User user);

    List<User> selectByEntityList(User user);

    /**
     * 根据id查询用户名
     * @param id
     * @return
     */
    String selectNameById(Integer id);

    /**
     * 分页查询
     * @param req
     * @return
     */
    List<User> accountListPage(AccountListReqVo req);



}