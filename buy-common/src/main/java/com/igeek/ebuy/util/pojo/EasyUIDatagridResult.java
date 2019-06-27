package com.igeek.ebuy.util.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author hftang
 * @date 2019-06-26 14:51
 * @desc
 */
public class EasyUIDatagridResult implements Serializable {

    private long total;
    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "EasyUIDatagridResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
