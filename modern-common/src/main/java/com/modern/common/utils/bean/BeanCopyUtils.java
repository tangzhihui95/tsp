package com.modern.common.utils.bean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：tzh
 * @Package：com.modern.common.utils.bean
 * @Project：tsp
 * @name：BeanCopyUtils
 * @Date：2024/8/29 15:16
 * @Filename：BeanCopyUtils
 */
public class BeanCopyUtils {
    public static <T, V> V oneCopy(T source, CopyOptions copyOptions, Class<V> desc) {
        V v = (V) ReflectUtil.newInstanceIfPossible(desc);
        return oneCopy(source, copyOptions, v);
    }

    public static <T, V> V oneCopy(T source, CopyOptions copyOptions, V desc) {
        return (V) BeanCopier.create(source, desc, copyOptions).copy();
    }

    public static <T, V> List<V> listCopy(List<T> sourceList, CopyOptions copyOptions, Class<V> desc) {
        return (List<V>) sourceList.stream()
                .map(source -> oneCopy(source, copyOptions, desc))
                .collect(Collectors.toList());
    }

    public static <T> List<T> listToListDTO(List<T> list, Class<T> clazz) {
        return JSON.parseArray(JSON.toJSONString(list), clazz);
    }

    public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
        List<T> output = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(input))
            for (E source : input) {
                T target = (T) BeanUtils.instantiate(clzz);
                BeanUtil.copyProperties(source, target, new String[0]);
                output.add(target);
            }
        return output;
    }
}
