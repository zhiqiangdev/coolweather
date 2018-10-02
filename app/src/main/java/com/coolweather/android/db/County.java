package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2018/10/2.
 */

public class County extends LitePalSupport {
    private int id;
    private String countyName;
    private int cityCode;
    private String weatherId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
