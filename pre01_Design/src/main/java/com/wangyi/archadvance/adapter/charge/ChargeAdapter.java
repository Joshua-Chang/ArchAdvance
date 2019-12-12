package com.wangyi.archadvance.adapter.charge;

public class ChargeAdapter extends ChinaCharge {
    private USACharge usaCharge;

    public ChargeAdapter(USACharge usaCharge) {
        this.usaCharge = usaCharge;
    }

    @Override
    public void chinaCharge() {
        if (usaCharge.usaCharge()==220) {
            System.out.println("符合中国220v用电标准");
        }else {
            System.out.println("用电异常,电器销毁");
        }
    }
}
