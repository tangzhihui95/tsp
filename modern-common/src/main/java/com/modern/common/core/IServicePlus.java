package com.modern.common.core;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.modern.common.core.page.PagePlus;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author：tzh
 * @Package：com.modern.common.core
 * @Project：tsp
 * @name：IServicePlus
 * @Date：2024/8/28 16:52
 * @Filename：IServicePlus
 */
public interface IServicePlus<T, K> extends IService<T> {

    K getDTOById(Serializable paramSerializable, CopyOptions paramCopyOptions);

    default K getDTOById(Serializable id) {
        return getDTOById(id, new CopyOptions());
    }

    default K getDTOById(Serializable id, Function<T, K> convertor) {
        return convertor.apply((T) getById(id));
    }

    List<K> listDTOByIds(Collection<? extends Serializable> paramCollection, CopyOptions paramCopyOptions);

    default List<K> listDTOByIds(Collection<? extends Serializable> idList) {
        return listDTOByIds(idList, new CopyOptions());
    }

    default List<K> listDTOByIds(Collection<? extends Serializable> idList, Function<Collection<T>, List<K>> convertor) {
        List<T> list = getBaseMapper().selectBatchIds(idList);
        if (list == null)
            return null;
        return convertor.apply(list);
    }

    List<K> listDTOByMap(Map<String, Object> paramMap, CopyOptions paramCopyOptions);

    default List<K> listDTOByMap(Map<String, Object> columnMap) {
        return listDTOByMap(columnMap, new CopyOptions());
    }

    default List<K> listDTOByMap(Map<String, Object> columnMap, Function<Collection<T>, List<K>> convertor) {
        List<T> list = getBaseMapper().selectByMap(columnMap);
        if (list == null)
            return null;
        return convertor.apply(list);
    }

    K getDTOOne(Wrapper<T> paramWrapper, CopyOptions paramCopyOptions);

    default K getDTOOne(Wrapper<T> queryWrapper) {
        return getDTOOne(queryWrapper, new CopyOptions());
    }

    default K getDTOOne(Wrapper<T> queryWrapper, Function<T, K> convertor) {
        return convertor.apply((T) getOne(queryWrapper, true));
    }

    List<K> listDTO(Wrapper<T> paramWrapper, CopyOptions paramCopyOptions);

    default List<K> listDTO(Wrapper<T> queryWrapper) {
        return listDTO(queryWrapper, new CopyOptions());
    }

    default List<K> listDTO(Wrapper<T> queryWrapper, Function<Collection<T>, List<K>> convertor) {
        List<T> list = getBaseMapper().selectList(queryWrapper);
        if (list == null)
            return null;
        return convertor.apply(list);
    }

    default List<K> listDTO() {
        return listDTO((Wrapper<T>) Wrappers.emptyWrapper());
    }

    default List<K> listDTO(Function<Collection<T>, List<K>> convertor) {
        return listDTO((Wrapper<T>) Wrappers.emptyWrapper(), convertor);
    }

    PagePlus<T, K> pageDTO(PagePlus<T, K> paramPagePlus, Wrapper<T> paramWrapper, CopyOptions paramCopyOptions);

    default PagePlus<T, K> pageDTO(PagePlus<T, K> page, Wrapper<T> queryWrapper) {
        return pageDTO(page, queryWrapper, new CopyOptions());
    }

    default PagePlus<T, K> pageDTO(PagePlus<T, K> page, Wrapper<T> queryWrapper, Function<Collection<T>, List<K>> convertor) {
        PagePlus<T, K> result = (PagePlus<T, K>) getBaseMapper().selectPage((IPage) page, queryWrapper);
        return result.setRecordsDTO(convertor.apply(result.getRecords()));
    }

    default PagePlus<T, K> pageDTO(PagePlus<T, K> page) {
        return pageDTO(page, (Wrapper<T>) Wrappers.emptyWrapper());
    }

    default PagePlus<T, K> pageDTO(PagePlus<T, K> page, Function<Collection<T>, List<K>> convertor) {
        return pageDTO(page, (Wrapper<T>) Wrappers.emptyWrapper(), convertor);
    }

    boolean saveAll(Collection<T> paramCollection);
}
