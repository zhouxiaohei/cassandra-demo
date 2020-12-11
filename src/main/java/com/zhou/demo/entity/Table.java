package com.zhou.demo.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther:
 * @description:
 * @date: 15:16 2019/1/9
 */

@AllArgsConstructor
@NoArgsConstructor
public class Table implements Serializable{
    private String keyspace_name;
    private String table_name;

    public String getKeyspace_name() {
        return keyspace_name;
    }

    public void setKeyspace_name(String keyspace_name) {
        this.keyspace_name = keyspace_name;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
