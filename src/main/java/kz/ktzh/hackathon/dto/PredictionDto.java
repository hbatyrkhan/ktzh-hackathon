package kz.ktzh.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class PredictionDto {
    String station;
    String carClass;
    String carClassName;
    Integer ticketsSold;
    Integer count;

    public String getCarClassName() {
        return carClassName;
    }

    public void setCarClassName(String carClassName) {
        this.carClassName = carClassName;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public Integer getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(Integer ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
