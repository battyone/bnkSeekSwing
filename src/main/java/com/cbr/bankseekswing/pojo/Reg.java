package com.cbr.bankseekswing.pojo;

import com.cbr.bankseekswing.annotation.Description;

/**
 * Created by e.vassaev on 11/13/17.
 */
public class Reg implements ReferenceItem {

    @Description(value = "Код", PK = true)
    private String rgn;
    @Description("Наименование")
    private String name;

    public Reg() {
    }

    public Reg(String code, String name) {
        this.rgn = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return rgn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRgn() {
        return rgn;
    }

    public void setRgn(String rgn) {
        this.rgn = rgn;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
