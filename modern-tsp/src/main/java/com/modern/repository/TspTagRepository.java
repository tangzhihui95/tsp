package com.modern.repository;

import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspTag;
import com.modern.mapper.TspTagMapper;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspTagRepository
 * @Date：2024/10/17 17:11
 * @Filename：TspTagRepository
 */
@Service
public class TspTagRepository extends ServicePlusImpl<TspTagMapper, TspTag, TspTag> {
}
