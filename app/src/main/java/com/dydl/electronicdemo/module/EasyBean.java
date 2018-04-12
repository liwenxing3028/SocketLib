package com.dydl.electronicdemo.module;

/**
 * Created by lwx on 2018/4/4.
 */

public class EasyBean {

    private String name;
    private String hehe;
    private String tata;

    public EasyBean() {

    }

    public EasyBean(String name, String hehe, String tata) {
        this.name = name;
        this.hehe = hehe;
        this.tata = tata;
    }

    public String getName() {
        return name;
    }

    public EasyBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getHehe() {
        return hehe;
    }

    public EasyBean setHehe(String hehe) {
        this.hehe = hehe;
        return this;
    }

    public String getTata() {
        return tata;
    }

    public EasyBean setTata(String tata) {
        this.tata = tata;
        return this;
    }

    @Override
    public String toString() {
        return
                name + '&' +
                 hehe + '&' +
                 tata + '&';
    }
}
