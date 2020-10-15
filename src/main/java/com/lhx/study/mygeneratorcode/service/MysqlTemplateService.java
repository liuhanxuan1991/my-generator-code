package com.lhx.study.mygeneratorcode.service;

import com.lhx.study.mygeneratorcode.dao.MysqlTemplateMapper;
import com.lhx.study.mygeneratorcode.entity.po.MysqlTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlTemplateService {


    @Autowired
    private MysqlTemplateMapper mysqlTemplateDao;


    public int insertSelective(MysqlTemplate entity) {
        return this.mysqlTemplateDao.insertSelective(entity);
    }

    public void insertBatch(List<MysqlTemplate> list) {
        this.mysqlTemplateDao.insertBatch(list);
    }

    public int updateByPrimaryKey(MysqlTemplate entity) {
        return this.mysqlTemplateDao.updateByPrimaryKey(entity);
    }

    public void updateBatch(List<MysqlTemplate> list) {
        this.mysqlTemplateDao.updateBatch(list);
    }

    public MysqlTemplate selectByPrimaryKey(Integer toid) {
        return (MysqlTemplate)this.mysqlTemplateDao.selectByPrimaryKey(toid);
    }

    public MysqlTemplate selectByEntity(MysqlTemplate entity) {
        return (MysqlTemplate)this.mysqlTemplateDao.selectByEntity(entity);
    }

    public List<MysqlTemplate> selectByEntityList(MysqlTemplate entity) {
        return this.mysqlTemplateDao.selectByEntityList(entity);
    }

    public Integer selectById(MysqlTemplate entity) {
        return (Integer)this.mysqlTemplateDao.selectById(entity);
    }

    public List<Integer> selectByIds(MysqlTemplate entity) {
        return this.mysqlTemplateDao.selectByIds(entity);
    }

    public void deleteByPrimaryKey(Integer toid) {
        this.mysqlTemplateDao.deleteByPrimaryKey(toid);
    }

    public void deleteBatch(List<Integer> list) {
        this.mysqlTemplateDao.deleteBatch(list);
    }

    public void deleteBatch(MysqlTemplate entity) {
        this.mysqlTemplateDao.deleteByEntity(entity);
    }
}
