package com.example.furka.justweather.Models.CurrentWeather;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind implements Serializable
{

    @SerializedName("speed")
    @Expose
    private Double speed;
    private final static long serialVersionUID = 2941817310387867657L;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

}
