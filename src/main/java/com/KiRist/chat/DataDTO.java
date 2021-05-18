package com.KiRist.chat;

public class DataDTO {
    private float humi;
    private float temp;

    public DataDTO() { }

    public void Data(String humi, String temp) {
        this.humi = Float.parseFloat(humi);
        this.temp = Float.parseFloat(temp);
    }

    public float getHumi() {
        return humi;
    }

    public void setUserName(String userName) {
        this.humi = Float.parseFloat(userName);
    }

    public float getTemp() {
        return temp;
    }

    public void setMessage(String temp) {
        this.temp = Float.parseFloat(temp);
    }
}
