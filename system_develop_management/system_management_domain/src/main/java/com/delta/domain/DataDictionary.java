package com.delta.domain;

public class DataDictionary {
    private int id;
    private String dname;
    private String shortName;
    private String longName;
    private String dataType;
    private String pName;

    public DataDictionary() {
    }

    public DataDictionary(int id, String dname, String shortName, String longName, String dataType, String pName) {
        this.id = id;
        this.dname = dname;
        this.shortName = shortName;
        this.longName = longName;
        this.dataType = dataType;
        this.pName = pName;
    }

    public DataDictionary(int id, String dname, String shortName, String longName, String dataType) {
        this.id = id;
        this.dname = dname;
        this.shortName = shortName;
        this.longName = longName;
        this.dataType = dataType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "DataDictionary{" +
                "id=" + id +
                ", dname='" + dname + '\'' +
                ", shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", pName='" + pName + '\'' +
                '}';
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

}
