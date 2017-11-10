package com.cbr.bankseekswing.pojo;

import com.cbr.bankseekswing.annotation.Description;

import java.util.Date;

/**
 * @author vassaeve
 */
public class BnkSeek {

    @Description(value = "Банковский идентификационный код (БИК)", PK = true)
    private String newnum;

    @Description("Код контроля допустимости проведения расчетных операций")
    private String real;

    @Description("Код типа участника расчетов")
    private String pzn;
    @Description
    private String uer;
    @Description("Код территории Российской Федерации")
    private String rgn;
    @Description("индекс")
    private String ind;
    @Description("Код типа населенного пункта")
    private String tnp;
    @Description("Населенный пункт")
    private String nnp;
    @Description("Адрес")
    private String adr;
    @Description
    private String rkc;
    @Description("Наименование участника расчетов")
    private String namep;

    @Description("Телефон")
    private String telef;
    @Description("Регистрационный номер")
    private String regn;
    @Description("Код ОКПО")
    private String okpo;
    @Description("Дата последнего изменения записи")
    private Date dt_izm;
    @Description("Номер счета")
    private String ksnp;
    @Description
    private Date date_in;
    @Description("Дата контроля")
    private Date date_ch;

    private BnkSeek() {
    }

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        this.real = real;
    }

    public String getPzn() {
        return pzn;
    }

    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    public String getUer() {
        return uer;
    }

    public void setUer(String uer) {
        this.uer = uer;
    }

    public String getRgn() {
        return rgn;
    }

    public void setRgn(String rgn) {
        this.rgn = rgn;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getTnp() {
        return tnp;
    }

    public void setTnp(String tnp) {
        this.tnp = tnp;
    }

    public String getNnp() {
        return nnp;
    }

    public void setNnp(String nnp) {
        this.nnp = nnp;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getRkc() {
        return rkc;
    }

    public void setRkc(String rkc) {
        this.rkc = rkc;
    }

    public String getNamep() {
        return namep;
    }

    public void setNamep(String namep) {
        this.namep = namep;
    }

    public String getNewnum() {
        return newnum;
    }

    public void setNewnum(String newnum) {
        this.newnum = newnum;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }

    public String getRegn() {
        return regn;
    }

    public void setRegn(String regn) {
        this.regn = regn;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }


    public String getKsnp() {
        return ksnp;
    }

    public void setKsnp(String ksnp) {
        this.ksnp = ksnp;
    }

    public Date getDt_izm() {
        return dt_izm;
    }

    public void setDt_izm(Date dt_izm) {
        this.dt_izm = dt_izm;
    }

    public Date getDate_in() {
        return date_in;
    }

    public void setDate_in(Date date_in) {
        this.date_in = date_in;
    }

    public Date getDate_ch() {
        return date_ch;
    }

    public void setDate_ch(Date date_ch) {
        this.date_ch = date_ch;
    }


}
