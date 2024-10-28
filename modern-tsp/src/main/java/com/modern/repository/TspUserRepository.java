package com.modern.repository;

import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspUser;
import com.modern.mapper.TspUserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspUserRepository
 * @Date：2024/10/23 14:12
 * @Filename：TspUserRepository
 */
@Service
public class TspUserRepository extends ServicePlusImpl<TspUserMapper, TspUser,TspUser> {
}
