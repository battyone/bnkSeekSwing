package com.cbr.bankseekswing.pojo;

import com.cbr.bankseekswing.annotation.Description;

/**
 * Created by e.vassaev on 11/13/17.
 */
public class Pzn implements ReferenceItem {

    @Description(value = "Код", PK = true)
    private String pzn;

    @Description("Наименование")
    private String name;

    public Pzn() {
    }

    public Pzn(String code, String name) {
        this.pzn = code;
        this.name = name;
    }

    public String getPzn() {
        return pzn;
    }

    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return pzn;
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
