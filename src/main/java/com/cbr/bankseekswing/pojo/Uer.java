package com.cbr.bankseekswing.pojo;

import com.cbr.bankseekswing.annotation.Description;

/**
 * Created by e.vassaev on 11/13/17.
 */
public class Uer implements ReferenceItem {

    @Description(value = "Код", PK = true)
    private String uer;
    @Description("Наименование")
    private String uername;

    public Uer(String code, String name) {
        this.uer = code;
        this.uername = name;
    }

    public String getUer() {
        return uer;
    }

    public void setUer(String uer) {
        this.uer = uer;
    }

    public String getUername() {
        return uername;
    }

    public void setUername(String uername) {
        this.uername = uername;
    }

    public Uer() {
    }

    @Override
    public String getCode() {
        return uer;
    }

    @Override
    public String getName() {
        return uername;
    }

    @Override
    public String toString() {
        return uername;
    }

}
