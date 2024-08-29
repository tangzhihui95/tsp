package com.modern.common.core;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.common.core.page.PagePlus;
import com.modern.common.utils.bean.BeanCopyUtils;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author：tzh
 * @Package：com.modern.common.core
 * @Project：tsp
 * @name：ServicePlusImpl
 * @Date：2024/8/28 16:44
 * @Filename：ServicePlusImpl
 */
public class ServicePlusImpl<M extends BaseMapperPlus<T>, T, K> extends ServiceImpl<M, T> implements IServicePlus<T, K> {
    private static final Logger log = LoggerFactory.getLogger(ServicePlusImpl.class);

    @Autowired
    protected M baseMapper;

    public M getBaseMapper() {
        return this.baseMapper;
    }

    protected Class<T> entityClass = currentModelClass();

    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    //protected Class<T> mapperClass = currentMapperClass();

    protected Class<K> dtoClass = currentVoClass();

    public Class<K> getDtoClass() {
        return this.dtoClass;
    }

   /* protected Class<T> currentMapperClass() {
        return (Class<T>) getResolvableType().as(ServicePlusImpl.class).getGeneric(new int[]{0}).getType();
    }*/

    protected Class<T> currentModelClass() {
        return (Class<T>) getResolvableType().as(ServicePlusImpl.class).getGeneric(new int[]{1}).getType();
    }

    protected Class<K> currentVoClass() {
        return (Class<K>) getResolvableType().as(ServicePlusImpl.class).getGeneric(new int[]{2}).getType();
    }

    protected ResolvableType getResolvableType() {
        return ResolvableType.forClass(ClassUtils.getUserClass(getClass()));
    }

    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        return super.saveBatch(entityList, batchSize);
    }

    public boolean saveOrUpdate(T entity) {
        return super.saveOrUpdate(entity);
    }

    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        return super.saveOrUpdateBatch(entityList, batchSize);
    }

    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        return super.updateBatchById(entityList, batchSize);
    }

    public boolean saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, 1000);
    }

    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        return saveOrUpdateBatch(entityList, 1000);
    }

    public boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 1000);
    }

    public boolean saveAll(Collection<T> entityList) {
        return (this.baseMapper.insertAll(entityList) == entityList.size());
    }

    public K getDTOById(Serializable id, CopyOptions copyOptions) {
        T t = (T) getBaseMapper().selectById(id);
        return (K) BeanCopyUtils.oneCopy(t, copyOptions, this.dtoClass);
    }

    public List<K> listDTOByIds(Collection<? extends Serializable> idList, CopyOptions copyOptions) {
        List<T> list = getBaseMapper().selectBatchIds(idList);
        if (list == null)
            return null;
        return BeanCopyUtils.listCopy(list, copyOptions, this.dtoClass);
    }

    public List<K> listDTOByMap(Map<String, Object> columnMap, CopyOptions copyOptions) {
        List<T> list = getBaseMapper().selectByMap(columnMap);
        if (list == null)
            return null;
        return BeanCopyUtils.listCopy(list, copyOptions, this.dtoClass);
    }

    public K getDTOOne(Wrapper<T> queryWrapper, CopyOptions copyOptions) {
        T t = (T) getOne(queryWrapper, true);
        return (K) BeanCopyUtils.oneCopy(t, copyOptions, this.dtoClass);
    }

    public List<K> listDTO(Wrapper<T> queryWrapper, CopyOptions copyOptions) {
        List<T> list = getBaseMapper().selectList(queryWrapper);
        if (list == null)
            return null;
        return BeanCopyUtils.listCopy(list, copyOptions, this.dtoClass);
    }

    public PagePlus<T, K> pageDTO(PagePlus<T, K> page, Wrapper<T> queryWrapper, CopyOptions copyOptions) {
        PagePlus<T, K> result = (PagePlus<T, K>) getBaseMapper().selectPage((IPage) page, queryWrapper);
        List<K> dtos = BeanCopyUtils.listCopy(result.getRecords(), copyOptions, this.dtoClass);
        result.setRecordsDTO(dtos);
        return result;
    }

}
