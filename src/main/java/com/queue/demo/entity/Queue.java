package com.queue.demo.entity;

import java.util.Date;
import java.util.Objects;

/**
 * @author zxx
 * @create 2020-07-02 17:36
 */
public class Queue {
    private String name;
    private Long date;

    public Queue() {
    }

    public Queue(String name, Long date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Queue queue = (Queue) o;
        return Objects.equals(name, queue.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Queue{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}