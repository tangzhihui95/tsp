package com.modern.common.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.*;

/**
 * @Author：tzh
 * @Package：com.modern.common.core.page
 * @Project：tsp
 * @name：PagePlus
 * @Date：2024/8/29 15:19
 * @Filename：PagePlus
 */
public class PagePlus<T, K> implements IPage<T> {
    public PagePlus<T, K> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public PagePlus<T, K> setRecordsDTO(List<K> recordsDTO) {
        this.recordsDTO = recordsDTO;
        return this;
    }

    public PagePlus<T, K> setTotal(long total) {
        this.total = total;
        return this;
    }

    public PagePlus<T, K> setSize(long size) {
        this.size = size;
        return this;
    }

    public PagePlus<T, K> setCurrent(long current) {
        this.current = current;
        return this;
    }

    public PagePlus<T, K> setOrders(List<OrderItem> orders) {
        this.orders = orders;
        return this;
    }

    public PagePlus<T, K> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    public PagePlus<T, K> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public PagePlus<T, K> setHitCount(boolean hitCount) {
        this.hitCount = hitCount;
        return this;
    }

    public PagePlus<T, K> setCountId(String countId) {
        this.countId = countId;
        return this;
    }

    public PagePlus<T, K> setMaxLimit(Long maxLimit) {
        this.maxLimit = maxLimit;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagePlus<?, ?> pagePlus = (PagePlus<?, ?>) o;
        return total == pagePlus.total && size == pagePlus.size && current == pagePlus.current && optimizeCountSql == pagePlus.optimizeCountSql && isSearchCount == pagePlus.isSearchCount && hitCount == pagePlus.hitCount && Objects.equals(records, pagePlus.records) && Objects.equals(recordsDTO, pagePlus.recordsDTO) && Objects.equals(orders, pagePlus.orders) && Objects.equals(countId, pagePlus.countId) && Objects.equals(maxLimit, pagePlus.maxLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(records, recordsDTO, total, size, current, orders, optimizeCountSql, isSearchCount, hitCount, countId, maxLimit);
    }

    @Override
    public String toString() {
        return "PagePlus{" +
                "records=" + records +
                ", recordsDTO=" + recordsDTO +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                ", orders=" + orders +
                ", optimizeCountSql=" + optimizeCountSql +
                ", isSearchCount=" + isSearchCount +
                ", hitCount=" + hitCount +
                ", countId='" + countId + '\'' +
                ", maxLimit=" + maxLimit +
                '}';
    }

    private List<T> records = Collections.emptyList();

    public List<T> getRecords() {
        return this.records;
    }

    private List<K> recordsDTO = Collections.emptyList();

    public List<K> getRecordsDTO() {
        return this.recordsDTO;
    }

    private long total = 0L;

    public long getTotal() {
        return this.total;
    }

    private long size = 10L;

    public long getSize() {
        return this.size;
    }

    private long current = 1L;

    public long getCurrent() {
        return this.current;
    }

    private List<OrderItem> orders = new ArrayList<>();

    public List<OrderItem> getOrders() {
        return this.orders;
    }

    private boolean optimizeCountSql = true;

    public boolean isOptimizeCountSql() {
        return this.optimizeCountSql;
    }

    private boolean isSearchCount = true;

    public boolean isSearchCount() {
        return this.isSearchCount;
    }

    private boolean hitCount = false;

    private String countId;

    private Long maxLimit;

    public boolean isHitCount() {
        return this.hitCount;
    }

    public String getCountId() {
        return this.countId;
    }

    public Long getMaxLimit() {
        return this.maxLimit;
    }

    public PagePlus(long current, long size) {
        this(current, size, 0L);
    }

    public PagePlus(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PagePlus(long current, long size, boolean isSearchCount) {
        this(current, size, 0L, isSearchCount);
    }

    public PagePlus(long current, long size, long total, boolean isSearchCount) {
        if (current > 1L)
            this.current = current;
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    public String countId() {
        return getCountId();
    }

    public Long maxLimit() {
        return getMaxLimit();
    }

    public PagePlus<T, K> addOrder(OrderItem... items) {
        this.orders.addAll(Arrays.asList(items));
        return this;
    }

    public PagePlus<T, K> addOrder(List<OrderItem> items) {
        this.orders.addAll(items);
        return this;
    }

    public List<OrderItem> orders() {
        return getOrders();
    }

    public boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }

    public long getPages() {
        return IPage.super.getPages();
    }

    public static <T, K> PagePlus<T, K> of(long current, long size) {
        return of(current, size, 0L);
    }

    public static <T, K> PagePlus<T, K> of(long current, long size, long total) {
        return of(current, size, total, true);
    }

    public static <T, K> PagePlus<T, K> of(long current, long size, boolean searchCount) {
        return of(current, size, 0L, searchCount);
    }

    public static <T, K> PagePlus<T, K> of(long current, long size, long total, boolean searchCount) {
        return new PagePlus<>(current, size, total, searchCount);
    }

    public PagePlus() {
    }

}
