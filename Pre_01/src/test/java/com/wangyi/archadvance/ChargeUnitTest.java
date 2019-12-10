package com.wangyi.archadvance;

import com.wangyi.archadvance.adapter.USA;
import com.wangyi.archadvance.adapter.charge.ChargeAdapter;

import org.junit.Test;

public class ChargeUnitTest {
    @Test
    public void charge(){
        //简单适配
        ChargeAdapter chinaAdapter = new ChargeAdapter(new USA());
        chinaAdapter.chinaCharge();
    }
}
