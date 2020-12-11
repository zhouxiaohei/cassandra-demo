package com.zhou.demo.controller;

import com.zhou.demo.entity.Table;
import com.zhou.demo.entity.TsKvLatestCf;
import com.zhou.demo.service.TelemetryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @auther: jackZhou
 * @description:
 * @date: 13:38 2019/1/9
 */
@Api(tags = "cassandraTemplate简单示例")
@Controller
@RequestMapping("/demo/cassandra/")
@Slf4j
public class CdDemoController {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Autowired
    private TelemetryService telemetryService;

    @ApiOperation("简单查询")
    @RequestMapping(value = "/simpleQuery", method = RequestMethod.GET)
    @ResponseBody
    public List<Table> simpleQuery(){
        String cql = String.format("select * from system_schema.tables limit 10");
        List<Table> select = cassandraTemplate.select(cql, Table.class);
        return select;
    }

    @ApiOperation("查询最新值")
    @RequestMapping(value = "/getLatestRecord", method = RequestMethod.GET)
    @ResponseBody
    public List<TsKvLatestCf> getLatestRecord(@RequestParam String entityId, @RequestParam(required = false) String keys){
        return telemetryService.getLatestRecord(entityId, keys);
    }

    @ApiOperation("查询最新值KV")
    @RequestMapping(value = "/getLatestRecordKV", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getLatestRecordKV(@RequestParam String entityId, @RequestParam(required = false) String keys){
        return telemetryService.getKvRecord(entityId, keys);
    }
    @ApiOperation("简单入库测试")
    @RequestMapping(value = "/simpleInsert", method = RequestMethod.POST)
    @ResponseBody
    public void simpleInsert(@RequestBody TsKvLatestCf tsKvLatestCf){
        cassandraTemplate.insert(tsKvLatestCf);
    }

}
