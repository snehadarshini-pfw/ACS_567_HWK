package edu.spm.model;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.CsvBindByName;

public class ForestFire {
    
    @CsvBindByName(column = "ID")
    private String id;

    @CsvBindByName(column = "month")
    private String month;

    @CsvBindByName(column = "day")
    private String day;

    @CsvBindByName(column = "RH")
    private String rh;

    

    public ForestFire() {
    }

    public ForestFire(String id, String month, String day, String rh) {
        this.id = id;
        this.month = month;
        this.day = day;
        this.rh = rh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = StringUtils.trim(id);
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = StringUtils.trim(month);
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = StringUtils.trim(day);
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = StringUtils.trim(rh);
    }

    

    
}
