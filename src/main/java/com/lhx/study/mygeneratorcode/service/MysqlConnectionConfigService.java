package com.lhx.study.mygeneratorcode.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.dao.MysqlConnectionConfigMapper;
import com.lhx.study.mygeneratorcode.dao.UserMapper;
import com.lhx.study.mygeneratorcode.entity.po.MysqlConnectionConfig;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.utils.PropertiesUtil;
import com.lhx.study.mygeneratorcode.vo.ReturnResult;
import com.lhx.study.mygeneratorcode.vo.request.database.DataBaseListReqVo;
import com.lhx.study.mygeneratorcode.vo.response.database.DataBaseListResVo;
import com.lhx.study.mygeneratorcode.vo.response.database.DataBasesResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MysqlConnectionConfigService {


    @Autowired
    private MysqlConnectionConfigMapper mysqlConnectionConfigDao;

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询数据库配置
     * @param req
     * @param user
     * @return
     */
    public ReturnResult<List<DataBaseListResVo>> dataBaseList(DataBaseListReqVo req, User user){
        ReturnResult<List<DataBaseListResVo>> result = new ReturnResult<>();
        Page<List<MysqlConnectionConfig>> page = PageHelper.startPage(req.getPage(), req.getLimit());
        boolean flag = false;
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())
                || BaseConstant.SYS_ROLE_KEY_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
        List<MysqlConnectionConfig> mysqlConnectionConfigs = mysqlConnectionConfigDao.dataBaseListPage(flag, user.getId(), req.getName(), req.getStatus(), req.getStartTime(), req.getEndTime());
        List<DataBaseListResVo> resultList = mysqlConnectionConfigs.stream().map(mysql -> {
            DataBaseListResVo resVo = new DataBaseListResVo();
            PropertiesUtil.copyProperties(mysql,resVo);
            resVo.setCreateUser(userMapper.selectNameById(mysql.getCreateBy()));
            return resVo;
        }).collect(Collectors.toList());
        result.setCount(page.getTotal());
        result.setData(resultList);
        return result;
    }


    /**
     * 查询当前用户可用的所有数据库配置
     * @param user
     * @return
     */
    public List<DataBasesResVo> dataBases(User user){
        boolean flag = false;
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())
                || BaseConstant.SYS_ROLE_KEY_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
        return mysqlConnectionConfigDao.dataBases(flag, user.getId());
    }


    /**
     * 根据id查询详情
     * @param id
     * @param user
     * @return
     */
    public DataBaseListResVo queryDataBaseById(Integer id,User user){
        DataBaseListResVo result = new DataBaseListResVo();
        boolean flag = false;
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())
                || BaseConstant.SYS_ROLE_KEY_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
        MysqlConnectionConfig mysql = mysqlConnectionConfigDao.queryDataBaseById(flag, id, user.getId());
        if (null != mysql){
            PropertiesUtil.copyProperties(mysql,result);
            result.setCreateUser(userMapper.selectNameById(mysql.getCreateBy()));
            result.setUpdateUser(userMapper.selectNameById(mysql.getUpdateBy()));
        }
        return result;
    }



    public int insertSelective(MysqlConnectionConfig entity) {
        return this.mysqlConnectionConfigDao.insertSelective(entity);
    }

    public void insertBatch(List<MysqlConnectionConfig> list) {
        this.mysqlConnectionConfigDao.insertBatch(list);
    }

    public int updateByPrimaryKey(MysqlConnectionConfig entity) {
        return this.mysqlConnectionConfigDao.updateByPrimaryKey(entity);
    }

    public void updateBatch(List<MysqlConnectionConfig> list) {
        this.mysqlConnectionConfigDao.updateBatch(list);
    }

    public MysqlConnectionConfig selectByPrimaryKey(Integer toid) {
        return (MysqlConnectionConfig)this.mysqlConnectionConfigDao.selectByPrimaryKey(toid);
    }

    public MysqlConnectionConfig selectByEntity(MysqlConnectionConfig entity) {
        return (MysqlConnectionConfig)this.mysqlConnectionConfigDao.selectByEntity(entity);
    }

    public List<MysqlConnectionConfig> selectByEntityList(MysqlConnectionConfig entity) {
        return this.mysqlConnectionConfigDao.selectByEntityList(entity);
    }

    public Integer selectById(MysqlConnectionConfig entity) {
        return (Integer)this.mysqlConnectionConfigDao.selectById(entity);
    }

    public List<Integer> selectByIds(MysqlConnectionConfig entity) {
        return this.mysqlConnectionConfigDao.selectByIds(entity);
    }

    public void deleteByPrimaryKey(Integer toid) {
        this.mysqlConnectionConfigDao.deleteByPrimaryKey(toid);
    }

    public void deleteBatch(List<Integer> list) {
        this.mysqlConnectionConfigDao.deleteBatch(list);
    }

    public void deleteBatch(MysqlConnectionConfig entity) {
        this.mysqlConnectionConfigDao.deleteByEntity(entity);
    }
}
