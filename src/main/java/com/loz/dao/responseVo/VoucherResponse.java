package com.loz.dao.responseVo;

import com.loz.dao.model.VoucherData;

import java.util.Date;

/**
 * Created by loz on 14/02/15.
 */
public class VoucherResponse {
    private Date date;
    private Iterable<VoucherData> data;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<VoucherData> getData() {
        return data;
    }

    public void setData(Iterable<VoucherData> data) {
        this.data = data;
    }
}
