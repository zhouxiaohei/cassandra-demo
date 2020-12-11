package com.zhou.demo.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

/**
 * @ClassName TsKvLatestCf
 * @Author JackZhou
 * @Date 2019/4/24  18:35
 **/

@Data
@Table("ts_kv_latest_cf")
public class TsKvLatestCf {
    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String entity_type;
    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private UUID entity_id;
    private String key;
    private String str_v;
    private Long long_v;
    private Double dbl_v;
    private Boolean bool_v;
    private long ts;
}
