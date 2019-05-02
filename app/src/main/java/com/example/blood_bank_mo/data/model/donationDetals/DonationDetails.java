
package com.example.blood_bank_mo.data.model.donationDetals;

import com.example.blood_bank_mo.data.model.donationrequests.Dontaiondata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationDetails {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Dontaiondata data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Dontaiondata getData() {
        return data;
    }

    public void setData(Dontaiondata data) {
        this.data = data;
    }

}
