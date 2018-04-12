package com.dydl.socketlib.model;

/**
 * Created by lwx on 2018/3/14.
 */

public class OkXmlBean {

    private String transCode;//交易代码
    private String compCode;//渠道商编号
    private String transTime;//交易时间
    private String selrialNo;//交易流水号
    private String spotCode;//网点代码
    private String operNo;//操作员编号
    private String terminalNo;//终端编号
    private String orgNo;//供电单位编号(终端管理单位)
    private String consNo;//用户编号
    private String xml;//发给营销的Xml串

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getSelrialNo() {
        return selrialNo;
    }

    public void setSelrialNo(String selrialNo) {
        this.selrialNo = selrialNo;
    }

    public String getSpotCode() {
        return spotCode;
    }

    public void setSpotCode(String spotCode) {
        this.spotCode = spotCode;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getConsNo() {
        return consNo;
    }

    public void setConsNo(String consNo) {
        this.consNo = consNo;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }


    @Override
    public String toString() {
        return getTransCode() + "|" + getCompCode() + "|" + getTransTime() +
                "|" + getSelrialNo() + "|" + getSpotCode() + "|" + getOperNo() +
                "|" + getTerminalNo() + "|" + getOrgNo() + "|" + getConsNo() +
                "|" + getXml() + "|";
    }
}
