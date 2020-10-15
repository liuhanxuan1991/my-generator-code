package com.lhx.study.mygeneratorcode.service;

import com.lhx.study.mygeneratorcode.dao.MysqlCodePathConfigMapper;
import com.lhx.study.mygeneratorcode.entity.po.MysqlCodePathConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlCodePathConfigService {
    @Autowired
    private MysqlCodePathConfigMapper mysqlCodePathConfigDao;

    public MysqlCodePathConfigService() {
    }

    public int insertSelective(MysqlCodePathConfig entity) {
        return this.mysqlCodePathConfigDao.insertSelective(entity);
    }

    public void insertBatch(List<MysqlCodePathConfig> list) {
        this.mysqlCodePathConfigDao.insertBatch(list);
    }

    public int updateByPrimaryKey(MysqlCodePathConfig entity) {
        return this.mysqlCodePathConfigDao.updateByPrimaryKey(entity);
    }

    public void updateBatch(List<MysqlCodePathConfig> list) {
        this.mysqlCodePathConfigDao.updateBatch(list);
    }

    public MysqlCodePathConfig selectByPrimaryKey(Integer toid) {
        return (MysqlCodePathConfig)this.mysqlCodePathConfigDao.selectByPrimaryKey(toid);
    }

    public MysqlCodePathConfig selectByEntity(MysqlCodePathConfig entity) {
        return (MysqlCodePathConfig)this.mysqlCodePathConfigDao.selectByEntity(entity);
    }

    public List<MysqlCodePathConfig> selectByEntityList(MysqlCodePathConfig entity) {
        return this.mysqlCodePathConfigDao.selectByEntityList(entity);
    }

    public Integer selectById(MysqlCodePathConfig entity) {
        return (Integer)this.mysqlCodePathConfigDao.selectById(entity);
    }

    public List<Integer> selectByIds(MysqlCodePathConfig entity) {
        return this.mysqlCodePathConfigDao.selectByIds(entity);
    }

    public void deleteByPrimaryKey(Integer toid) {
        this.mysqlCodePathConfigDao.deleteByPrimaryKey(toid);
    }

    public void deleteBatch(List<Integer> list) {
        this.mysqlCodePathConfigDao.deleteBatch(list);
    }

    public void deleteBatch(MysqlCodePathConfig entity) {
        this.mysqlCodePathConfigDao.deleteByEntity(entity);
    }
}
