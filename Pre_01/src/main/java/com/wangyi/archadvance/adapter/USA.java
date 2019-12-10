package com.wangyi.archadvance.adapter;

import com.wangyi.archadvance.adapter.charge.USACharge;

public class USA extends USACharge {
    @Override
    public int usaCharge() {
        System.out.println("变压器改造了电压...");
        return 220;
    }
}
