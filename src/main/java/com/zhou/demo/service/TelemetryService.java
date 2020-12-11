package com.zhou.demo.service;

import com.zhou.demo.entity.TsKvLatestCf;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.cassandra.core.query.Criteria.where;

/**
 * @ClassName TelemetryService
 * @Author JackZhou
 * @Date 2020/8/4  13:53
 **/

@Service
public class TelemetryService {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    private static final String ENTITY_TYPE = "DEVICE";

    public List<TsKvLatestCf> getLatestRecord(String entityId, String keys){
        Query query = Query
                .query(where("entity_id").is(UUID.fromString(entityId)))
                .and(where("entity_type").is(ENTITY_TYPE));
        if(StringUtils.isNotBlank(keys)){
            // 一定要记得重新赋值
            query = query.and(where("key").in(keys.split(",")));
        }
        return cassandraTemplate.select(query, TsKvLatestCf.class);
    }

    public Map<String, Object> getKvRecord(String entityId, String keys){
        List<TsKvLatestCf> latestRecord = getLatestRecord(entityId, keys);
        return latestRecord.stream().collect(Collectors.toMap(bean -> bean.getKey(), bean -> getValue(bean)));
    }

   public static Object getValue(TsKvLatestCf tsKvLatestCf){
       String strV = tsKvLatestCf.getStr_v();
       if(StringUtils.isNotBlank(strV)){
           return strV;
       }
       Double dblV = tsKvLatestCf.getDbl_v();
       if(dblV != null){
           return dblV;
       }
       Long longV = tsKvLatestCf.getLong_v();
       if(longV != null){
           return longV;
       }
       Boolean boolV = tsKvLatestCf.getBool_v();
       if(boolV != null){
           return boolV;
       }
       return null;
   }

}
