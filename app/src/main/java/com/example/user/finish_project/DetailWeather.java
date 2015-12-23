package com.example.user.finish_project;

public class DetailWeather {
    private Integer windSpeed;
    private Integer windDirection;
    private Integer pressure;
    private Integer humidity;

    public DetailWeather() {
        this.windSpeed = 0;
        this.windDirection = 0;
        this.pressure = 0;
        this.humidity = 0;
    }

    public DetailWeather(Integer windSpeed, Integer windDirection, Integer pressure,
                                 Integer humidity) {
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

}
