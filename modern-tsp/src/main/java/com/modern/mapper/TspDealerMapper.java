package com.modern.mapper;

import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspDealer;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspDealerMapper
 * @Date：2024/11/7 13:47
 * @Filename：TspDealerMapper
 */
public interface TspDealerMapper extends BaseMapperPlus<TspDealer> {
    @Select({"SELECT id,dealer_name as 'dealerName' FROM tsp_dealer WHERE is_delete = 0"})
    List<Map<String, String>> saleNameList();

    @Select({"select dealer_name as 'dealerName',id from tsp_dealer where is_delete = 0 and dealer_address like concat(#{address},'%')"})
    List<TspDealer> saleNameListByLikeAddress(String paramString);

    @Select({"SELECT dealer_address as 'dealerAddress' FROM tsp_dealer WHERE is_delete = 0 AND dealer_name = #{dealerName}"})
    Map<String, String> saleNameGetAddress(String paramString);
}
