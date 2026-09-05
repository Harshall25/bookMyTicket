package org.booking.entities;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public class Train {

    private String trainId;
    private String trainNumber;
    private List<List<Integer>> trainSeats;
    private Map<String, Time> stationTime;
    private List<String> stations;

    public Train() {
    }

    public Train(String trainId, String trainNumber, List<List<Integer>> trainSeats, Map<String, Time> stationTime, List<String> stations) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainSeats = trainSeats;
        this.stationTime = stationTime;
        this.stations = stations;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public List<List<Integer>> getTrainSeats() {
        return trainSeats;
    }

    public void setTrainSeats(List<List<Integer>> trainSeats) {
        this.trainSeats = trainSeats;
    }

    public Map<String, Time> getStationTime() {
        return stationTime;
    }

    public void setStationTime(Map<String, Time> stationTime) {
        this.stationTime = stationTime;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    // Convenience aliases
    public String getTrainNo() {
        return trainNumber;
    }

    public void setTrainNo(String trainNo) {
        this.trainNumber = trainNo;
    }

    public List<List<Integer>> getSeats() {
        return trainSeats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.trainSeats = seats;
    }

    public Map<String, Time> getStationTimes() {
        return stationTime;
    }

    public void setStationTimes(Map<String, Time> stationTimes) {
        this.stationTime = stationTimes;
    }

    public String getTrainInfo() {
        return String.format("Train ID: %s Train No: %s", trainId, trainNumber);
    }
}
