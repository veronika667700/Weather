package com.example.user.finish_project;

public class Temperature {
    private Integer minTemperature;
    private Integer maxTemperature;

    public Temperature() {
        this.minTemperature = 0;
        this.maxTemperature = 0;
    }

    public Temperature(Integer minTemperature, Integer maxTemperature) {
        this.minTemperature = Math.round(minTemperature);
        this.maxTemperature = Math.round(maxTemperature);
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
