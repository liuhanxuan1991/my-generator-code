package com.lhx.study.mygeneratorcode.dao.base;

import java.util.List;

public interface BaseDao<T, K> {
    <T> T selectByPrimaryKey(K key);

    List<T> selectByEntityList(T t);

    T selectByEntity(T t);

    K selectById(T t);

    List<K> selectByIds(T t);

    void deleteByPrimaryKey(K key);

    int insert(T t);

    int insertSelective(T t);

    int updateByPrimaryKey(T t);

    int insertBatch(List<T> t);

    int updateBatch(List<T> t);

    int deleteBatch(List<K> list);

    int deleteByEntity(T t);
}
