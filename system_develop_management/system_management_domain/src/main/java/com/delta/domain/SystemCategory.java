package com.delta.domain;

public class SystemCategory {

    private String num;
    private String s_category;

    public SystemCategory() {
    }

    public SystemCategory(String num, String s_category) {
        this.num = num;
        this.s_category = s_category;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getS_category() {
        return s_category;
    }

    public void setS_category(String s_category) {
        this.s_category = s_category;
    }
}
