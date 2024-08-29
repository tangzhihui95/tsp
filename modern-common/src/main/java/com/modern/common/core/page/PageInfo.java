package com.modern.common.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author：tzh
 * @Package：com.modern.common.core.page
 * @Project：tsp
 * @name：PageInfo
 * @Date：2024/8/29 9:04
 * @Filename：PageInfo
 */
@ApiModel("前端分页数据")
@Data
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("总记录数")
    protected long total;

    @ApiModelProperty("结果集列表")
    protected List<T> list;

    @ApiModelProperty("当前页")
    private long pageNum;

    @ApiModelProperty("每页数量")
    private long pageSize;

    @ApiModelProperty("当页数量")
    private int size;

    @ApiModelProperty("总数量")
    private int pages;

    @ApiModelProperty("前一页")
    private int prePage;

    @ApiModelProperty("下一页")
    private int nextPage;
    @ApiModelProperty("是否为第一页")
    private boolean isFirstPage = false;
    @ApiModelProperty("是否为最后一页")
    private boolean isLastPage = false;
    @ApiModelProperty("是否有前一页")
    private boolean hasPreviousPage = false;
    @ApiModelProperty("是否有下一页")
    private boolean hasNextPage = false;
    @ApiModelProperty("导航页码数")
    private long navigatePages;
    @ApiModelProperty("所有导航页号")
    private int[] navigatepageNums;
    @ApiModelProperty("导航条上第一页")
    private int navigateFirstPage;
    @ApiModelProperty("导航条上的最后一页")
    private int navigateLastPage;
    @ApiModelProperty("分页用，防止重叠数据,默认为0")
    private Long nextSeq = Long.valueOf(0L);

    public PageInfo(List<T> list, long pageNum, long pageSize, long total, Long nextSeq) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
        this.size = list.size();
        this.navigatePages = pageSize;
        this.nextSeq = nextSeq;
        setTotal(total);
        calcNavigatepageNums();
        calcPage();
        judgePageBoudary();
    }

    public PageInfo(List<T> list, long pageNum, long pageSize, Long total) {
        List<T> pages = (List<T>) list.stream().skip(pageSize * (pageNum - 1L)).limit(pageSize).collect(Collectors.toList());
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = pages;
        this.size = list.size();
        this.navigatePages = pageSize;
        setTotal(total.longValue());
    }

    public PageInfo() {
    }

    public static <T> com.modern.common.core.page.PageInfo<T> of(List<T> list, long pageNum, long pageSize, long total) {
        return new com.modern.common.core.page.PageInfo<>(list, pageNum, pageSize, total, Long.valueOf(0L));
    }

    public static <T> com.modern.common.core.page.PageInfo<T> of(List<T> list, long pageNum, long pageSize, Long total, Long nextSeq) {
        return new com.modern.common.core.page.PageInfo<>(list, pageNum, pageSize, total.longValue(), nextSeq);
    }

    public static <T> com.modern.common.core.page.PageInfo<T> of(IPage<T> page) {
        return new com.modern.common.core.page.PageInfo<>(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal(), Long.valueOf(0L));
    }

    public static <T> com.modern.common.core.page.PageInfo<T> physicalPage(List<T> list, int pageNum, int pageSize, long total) {
        return new com.modern.common.core.page.PageInfo<>(list, pageNum, pageSize, Long.valueOf(total));
    }


    public String toString() {
        return "PageInfo(total=" + getTotal() + ", list=" + getList() + ", pageNum=" + getPageNum() + ", pageSize=" + getPageSize() + ", size=" + getSize() + ", pages=" + getPages() + ", prePage=" + getPrePage() + ", nextPage=" + getNextPage() + ", isFirstPage=" + isFirstPage() + ", isLastPage=" + isLastPage() + ", hasPreviousPage=" + isHasPreviousPage() + ", hasNextPage=" + isHasNextPage() + ", navigatePages=" + getNavigatePages() + ", navigatepageNums=" + Arrays.toString(getNavigatepageNums()) + ", navigateFirstPage=" + getNavigateFirstPage() + ", navigateLastPage=" + getNavigateLastPage() + ", nextSeq=" + getNextSeq() + ")";
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (total == -1L) {
            this.pages = 1;
            return;
        }
        if (this.pageSize > 0L) {
            this.pages = (int) (total / this.pageSize + ((total % this.pageSize == 0L) ? 0L : 1L));
        } else {
            this.pages = 0;
        }
        if (this.pageNum > this.pages &&
                this.pages != 0)
            this.pageNum = this.pages;
    }

    public List<T> getList() {
        return this.list;
    }

    public long getPageNum() {
        return this.pageNum;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public int getPages() {
        return this.pages;
    }

    public int getPrePage() {
        return this.prePage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public boolean isFirstPage() {
        return this.isFirstPage;
    }

    public boolean isLastPage() {
        return this.isLastPage;
    }

    public boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public long getNavigatePages() {
        return this.navigatePages;
    }

    public int[] getNavigatepageNums() {
        return this.navigatepageNums;
    }

    public int getNavigateFirstPage() {
        return this.navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return this.navigateLastPage;
    }

    public Long getNextSeq() {
        return this.nextSeq;
    }

    private void calcNavigatepageNums() {
        if (this.pages <= this.navigatePages) {
            this.navigatepageNums = new int[this.pages];
            for (int i = 0; i < this.pages; i++)
                this.navigatepageNums[i] = i + 1;
        } else {
            this.navigatepageNums = new int[(int) this.navigatePages];
            int startNum = (int) (this.pageNum - this.navigatePages / 2L);
            int endNum = (int) (this.pageNum + this.navigatePages / 2L);
            if (startNum < 1) {
                startNum = 1;
                for (int i = 0; i < this.navigatePages; i++)
                    this.navigatepageNums[i] = startNum++;
            } else if (endNum > this.pages) {
                endNum = this.pages;
                for (int i = (int) (this.navigatePages - 1L); i >= 0; i--)
                    this.navigatepageNums[i] = endNum--;
            } else {
                for (int i = 0; i < this.navigatePages; i++)
                    this.navigatepageNums[i] = startNum++;
            }
        }
    }

    private void calcPage() {
        if (this.navigatepageNums != null && this.navigatepageNums.length > 0) {
            this.navigateFirstPage = this.navigatepageNums[0];
            this.navigateLastPage = this.navigatepageNums[this.navigatepageNums.length - 1];
            if (this.pageNum > 1L)
                this.prePage = (int) (this.pageNum - 1L);
            if (this.pageNum < this.pages)
                this.nextPage = (int) (this.pageNum + 1L);
        }
    }

    private void judgePageBoudary() {
        this.isFirstPage = (this.pageNum == 1L);
        this.isLastPage = (this.pageNum == this.pages || this.pages == 0);
        this.hasPreviousPage = (this.pageNum > 1L);
        this.hasNextPage = (this.pageNum < this.pages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageInfo<?> pageInfo = (PageInfo<?>) o;
        return total == pageInfo.total && pageNum == pageInfo.pageNum && pageSize == pageInfo.pageSize && size == pageInfo.size && pages == pageInfo.pages && prePage == pageInfo.prePage && nextPage == pageInfo.nextPage && isFirstPage == pageInfo.isFirstPage && isLastPage == pageInfo.isLastPage && hasPreviousPage == pageInfo.hasPreviousPage && hasNextPage == pageInfo.hasNextPage && navigatePages == pageInfo.navigatePages && navigateFirstPage == pageInfo.navigateFirstPage && navigateLastPage == pageInfo.navigateLastPage && Objects.equals(list, pageInfo.list) && Arrays.equals(navigatepageNums, pageInfo.navigatepageNums) && Objects.equals(nextSeq, pageInfo.nextSeq);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(total, list, pageNum, pageSize, size, pages, prePage, nextPage, isFirstPage, isLastPage, hasPreviousPage, hasNextPage, navigatePages, navigateFirstPage, navigateLastPage, nextSeq);
        result = 31 * result + Arrays.hashCode(navigatepageNums);
        return result;
    }
}

