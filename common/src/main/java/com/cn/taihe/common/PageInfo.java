package com.cn.taihe.common;
import lombok.Data;
import java.util.List;

@Data
public class PageInfo<T> {
    private int pageNum;       // 当前页
    private int pageSize;      // 每页数量
    private long total;        // 总记录数
    private int pages;         // 总页数
    private List<T> list;      // 数据列表

    public PageInfo(List<T> list) {
        if (list instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page<T> page = (com.github.pagehelper.Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.list = page;
        } else {
            this.list = list;
        }
    }
}
