package com.cbr.bankseekswing.pojo;

import com.cbr.bankseekswing.annotation.Description;

/**
 * Created by e.vassaev on 11/13/17.
 */
public class Tnp implements ReferenceItem {

    @Description(value = "Код", PK = true)
    private String tnp;

    @Description("Наименование")
    private String fullname;

    public Tnp(String code, String name) {
        this.tnp = code;
        this.fullname = name;
    }

    public Tnp() {
    }

    public String getTnp() {
        return tnp;
    }

    public void setTnp(String tnp) {
        this.tnp = tnp;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String getCode() {
        return tnp;
    }

    @Override
    public String getName() {
        return fullname;
    }

    @Override
    public String toString() {
        return fullname;
    }
}
