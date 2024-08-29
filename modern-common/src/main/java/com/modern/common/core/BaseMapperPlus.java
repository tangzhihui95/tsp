package com.modern.common.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Collection;

/**
 * @Author：tzh
 * @Package：com.modern.common.core
 * @Project：tsp
 * @name：BaseMapperPlus
 * @Date：2024/8/28 16:45
 * @Filename：BaseMapperPlus
 */
public interface BaseMapperPlus <T> extends BaseMapper<T> {
    int insertAll(@Param("list") Collection<T> paramCollection);
}
