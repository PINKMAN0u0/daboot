package com.pinkman.dtboot.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 处理查询参数
 * 此类继承了LinkedHashMap
 * 相当于此类也为Map
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数，将传入的map中的两个参数转为整型
	 * @param params
	 * @return:
	 */
    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        //limit 限制，取的条数
        //offset 偏移量，从第几条开始取
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Integer offset = Integer.parseInt(params.get("offset").toString());
        this.put("limit", limit);
        this.put("offset", offset);
        
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
       /* String sidx = params.get("sidx").toString();
        String order = params.get("order").toString();
        this.put("sidx", SQLFilter.sqlInject(sidx));
        this.put("order", SQLFilter.sqlInject(order));*/
    }
}
