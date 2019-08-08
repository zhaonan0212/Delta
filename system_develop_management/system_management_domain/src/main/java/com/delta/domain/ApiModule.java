package com.delta.domain;

import com.delta.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ApiModule {
    private Integer id;
    private String systemCat;
    private String function;
    private String pName;
    //此注解是讓我的添加方法日期格式轉換
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;
    //為了更新裏面展示日期格式，創建屬性
    private String dateTimeStr;

    public ApiModule() {
    }

    public ApiModule(Integer id, String systemCat, String function, String pName, Date dateTime) {
        this.id = id;
        this.systemCat = systemCat;
        this.function = function;
        this.pName = pName;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemCat() {
        return systemCat;
    }

    public void setSystemCat(String systemCat) {
        this.systemCat = systemCat;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTimeStr() {
        if (dateTime == null){
            return "";
        }
        return DateUtils.dateToString(dateTime,DateUtils.simpleDateFormat1);
    }

    @Override
    public String toString() {
        return "ApiModule{" +
                "id=" + id +
                ", systemCat='" + systemCat + '\'' +
                ", function='" + function + '\'' +
                ", pName='" + pName + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
