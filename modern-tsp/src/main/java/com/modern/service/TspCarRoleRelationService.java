package com.modern.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.domain.TspCarRoleRelation;
import com.modern.mapper.TspCarRoleRelationMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspCarRoleRelation
 * @Date：2024/10/16 15:05
 * @Filename：TspCarRoleRelation
 */
@Service
public class TspCarRoleRelationService extends ServiceImpl<TspCarRoleRelationMapper, TspCarRoleRelation> {

    public List<Long> queryCarIdByRoleIds(Set<Long> roleIds) {
        try {
            // 创建 Lambda 查询链
            LambdaQueryChainWrapper<TspCarRoleRelation> queryWrapper = lambdaQuery()
                    .select(TspCarRoleRelation::getCarId)
                    .in(TspCarRoleRelation::getRoleId, roleIds); // 添加查询条件

            // 执行查询并获取结果
            List<TspCarRoleRelation> result = queryWrapper.list();

            // 处理结果集
            if (!CollectionUtils.isEmpty(result)) {
                return result.stream()
                        .map(TspCarRoleRelation::getCarId)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            // 异常处理
            System.err.println("Error occurred while fetching car IDs: " + e.getMessage());
        }
        // 如果没有找到结果或发生异常，返回空列表
        return Collections.emptyList();
    }
}
