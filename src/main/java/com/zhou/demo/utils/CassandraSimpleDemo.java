package com.zhou.demo.utils;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * @auther:
 * @description:
 * @date: 17:46 2019/1/8
 */
public class CassandraSimpleDemo {

    public void simpleQuery(Session session) {
        // 执行CQL语句
        ResultSet rs = session.execute("select * from system_schema.tables");
        // 从返回结果中取出第一条结果
        Row row = rs.one();
        System.out.println("结果----：" + row.getString("keyspace_name"));
    }

    public void testSimpleQuery(Session session) {
        // 无主键sql,提示小心性能问题,使用ALLOW FILTERING才可以
        String noPSql = "select * from system_schema.tables where crc_check_chance = 1";
        String noPSqlAllow =  "select * from system_schema.tables where crc_check_chance = 1 ALLOW FILTERING";
        // 执行CQL语句
        ResultSet rs = session.execute(noPSqlAllow);
        // 从返回结果中取出第一条结果
        Row row = rs.one();
        System.out.println("结果----：" + row.getString("keyspace_name"));
    }

    public void agileQuery(Session session) {
        ResultSet rs = session.execute(QueryBuilder.select().from("system_schema", "tables").where(QueryBuilder.eq("keyspace_name", "system_auth")));
        Row row = rs.one();
        System.out.println("结果----：" + row.getString("table_name"));
    }

//    public void insert(Session session){
//        session.execute(QueryBuilder.insertInto("compaas", "ts_kv_latest_cf").value())
//    }

    public static void main(String[] args) {
        Cluster cluster = null;
        try {
            // 创建连接到Cassandra的客户端
            cluster = Cluster.builder().addContactPoint("10.201.82.63").build();
            Metadata metadata = cluster.getMetadata();
            for (Host host : metadata.getAllHosts()) {
                System.out.println("host------" + host.getAddress());
            }
            // 创建用户会话
            Session session = cluster.connect();
            new CassandraSimpleDemo().testSimpleQuery(session);
        } finally {
            // 调用cluster变量的close()函数并关闭所有与之关联的链接
            if (cluster != null) {
                cluster.close();
            }
        }

    }

}
