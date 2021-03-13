package kz.ktzh.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class WagonDto {
    String wagonNumber;
    String carClass;
    String carClassName;
    String ticketsRemaining;

    public WagonDto(){

    }

    public void show(){
        System.out.println("WNumber: " + wagonNumber);
        System.out.println("carClass: " + carClass);
        System.out.println("carClassName: " + carClassName);
        System.out.println("ticketsRem: " + ticketsRemaining);
    }

    public WagonDto(String wagonNumber, String carClass, String carClassName, String ticketsRemaining) {
        this.wagonNumber = wagonNumber;
        this.carClass = carClass;
        this.carClassName = carClassName;
        this.ticketsRemaining = ticketsRemaining;
    }

    public String getWagonNumber() {
        return wagonNumber;
    }

    public void setWagonNumber(String wagonNumber) {
        this.wagonNumber = wagonNumber;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public String getCarClassName() {
        return carClassName;
    }

    public void setCarClassName(String carClassName) {
        this.carClassName = carClassName;
    }

    public String getTicketsRemaining() {
        return ticketsRemaining;
    }

    public void setTicketsRemaining(String ticketsRemaining) {
        this.ticketsRemaining = ticketsRemaining;
    }
}

