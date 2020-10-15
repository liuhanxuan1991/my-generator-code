package com.lhx.study.mygeneratorcode.service;

import com.lhx.study.mygeneratorcode.dao.RoleMapper;
import com.lhx.study.mygeneratorcode.dao.UserMapper;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.utils.PropertiesUtil;
import com.lhx.study.mygeneratorcode.vo.request.account.AccountListReqVo;
import com.lhx.study.mygeneratorcode.vo.response.account.AccountListResVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: lhx
 * @Date: 2018/12/14 17:34
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;




    public User selectByEntity(User user){
        return userMapper.selectByEntity(user);
    }


    public User selectByPrimaryKey(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public boolean updateByPrimaryKeySelective(User user){
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }

    /**
     * 根据id查询用户名
     * @param id
     * @return
     */
    public String selectNameById(Integer id){
        return userMapper.selectNameById(id);
    }

    /**
     * 分页查询用户信息
     * @param req
     * @return
     */
    public List<AccountListResVo> accountListPage(AccountListReqVo req){
        List<User> users = userMapper.accountListPage(req);
        if (CollectionUtils.isNotEmpty(users)){
            List<AccountListResVo> collect = users.stream().map(user -> {
                AccountListResVo resVo = new AccountListResVo();
                PropertiesUtil.copyProperties(user, resVo);
                resVo.setCreateUser(userMapper.selectNameById(user.getCreateBy()));
                resVo.setRoleName(roleMapper.selectNameByKey(user.getRoleKey()));
                return resVo;
            }).collect(Collectors.toList());
            return collect;
        }
        return new ArrayList<>();
    }


    /**
     * 新增用户
     * @param user
     * @return
     */
    public Boolean addAccount(User user){
        return userMapper.insertSelective(user) > 0;
    }

}
