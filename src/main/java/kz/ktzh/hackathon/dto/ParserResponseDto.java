package kz.ktzh.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude
public class ParserResponseDto {
    String trainNumber;
    String from;
    String to;
    String date;
    ArrayList<String> stations;
    ArrayList<WagonDto> wagons;

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getStations() {
        return stations;
    }

    public void setStations(ArrayList<String> stations) {
        this.stations = stations;
    }

    public ArrayList<WagonDto> getWagons() {
        return wagons;
    }

    public void setWagons(ArrayList<WagonDto> wagons) {
        this.wagons = wagons;
    }
}
