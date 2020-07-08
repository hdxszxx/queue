package com.queue.demo.entity;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.lang.Nullable;

/**
 * @author zxx
 * @create 2020-07-07 15:13
 */
public class QueueTyped<V> implements ZSetOperations.TypedTuple<V> {

    @Nullable
    private final Long score;
    @Nullable
    private final V value;

    public QueueTyped(@Nullable V value, @Nullable Long score) {
        this.score = score;
        this.value = value;
    }

    @Override
    @Nullable
    public Double getScore() {
        return null;
    }

    @Nullable
    public Long getScoreLong() {
        return this.score;
    }

    @Override
    @Nullable
    public V getValue() {
        return this.value;
    }


    public int compareTo(Double o) {
        double thisScore = this.score == null ? 0.0D : this.score;
        double otherScore = o == null ? 0.0D : o;
        return Double.compare(thisScore, otherScore);
    }

    @Override
    public int compareTo(ZSetOperations.TypedTuple<V> o) {
        return o == null ? this.compareTo(0.0D) : this.compareTo(o.getScore());
    }
}