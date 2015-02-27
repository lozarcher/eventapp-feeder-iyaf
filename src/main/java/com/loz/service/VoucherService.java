package com.loz.service;

import com.loz.dao.VoucherDao;
import com.loz.dao.model.VoucherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by larcher on 27/02/2015.
 */
@Service
public class VoucherService {
    @Autowired
    VoucherDao voucherDao;

    public Iterable<VoucherData> getVouchers() {
        return voucherDao.findAllOrderByPositionAsc();
    }

}
