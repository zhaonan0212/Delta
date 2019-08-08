package com.delta.domain;

import com.delta.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class KnowledgeRepository {
    private Integer id;
    private String knowledgeCat;
    private String exceptionNum;
    private String descriptionCon;
    private String pName;
    @DateTimeFormat( pattern="yyyy-MM-dd")
    private Date pDate;

    private String pDateStr;

    public String getpDateStr() {
       if (pDate == null){
           return  "";
       }
       return DateUtils.dateToString(pDate,DateUtils.simpleDateFormat1);
    }

    public KnowledgeRepository() {
    }

    public KnowledgeRepository(Integer id, String knowledgeCat, String exceptionNum, String descriptionCon, String pName, Date pDate) {
        this.id = id;
        this.knowledgeCat = knowledgeCat;
        this.exceptionNum = exceptionNum;
        this.descriptionCon = descriptionCon;
        this.pName = pName;
        this.pDate = pDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKnowledgeCat() {
        return knowledgeCat;
    }

    public void setKnowledgeCat(String knowledgeCat) {
        this.knowledgeCat = knowledgeCat;
    }

    public String getExceptionNum() {
        return exceptionNum;
    }

    public void setExceptionNum(String exceptionNum) {
        this.exceptionNum = exceptionNum;
    }

    public String getDescriptionCon() {
        return descriptionCon;
    }

    public void setDescriptionCon(String descriptionCon) {
        this.descriptionCon = descriptionCon;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }


    @Override
    public String toString() {
        return "KnowledgeRepository{" +
                "id=" + id +
                ", knowledgeCat=" + knowledgeCat +
                ", exceptionNum='" + exceptionNum + '\'' +
                ", descriptionCon='" + descriptionCon + '\'' +
                ", pName='" + pName + '\'' +
                ", pDate=" + pDate +
                '}';
    }
}

