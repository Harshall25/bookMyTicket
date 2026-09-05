package org.booking.service;

import org.booking.entities.Train;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class TrainServiceTest {

    private TrainService trainService;
    private File tempTrainFile;

    @BeforeMethod
    public void setUp() throws IOException {
        tempTrainFile = File.createTempFile("test_trains", ".json");
        tempTrainFile.deleteOnExit();
        Files.writeString(tempTrainFile.toPath(), "[]");
        
        // Pass temp file to ensure clean state and avoid touching local db files
        trainService = new TrainService(tempTrainFile.getAbsolutePath());
    }

    private Map<String, String> createStationMap(String... stations) {
        Map<String, String> map = new LinkedHashMap<>();
        for (String station : stations) {
            map.put(station, "10:00:00");
        }
        return map;
    }

    @Test(testName = "Search Train - Valid Direct Route", description = "Verifies searchTrains returns matching train when source precedes destination on the station list")
    public void testSearchTrainsWithValidRoute() {
        Train train = new Train();
        train.setTrainId("T101");
        train.setTrainNumber("12345");
        train.setStationTimes(createStationMap("Delhi", "Jaipur", "Ahmedabad", "Mumbai"));
        train.setStations(Arrays.asList("Delhi", "Jaipur", "Ahmedabad", "Mumbai"));

        trainService.getTrainList().clear();
        trainService.getTrainList().add(train);

        List<Train> results = trainService.searchTrains("Delhi", "Mumbai");
        assertEquals(results.size(), 1, "Should find 1 train between Delhi and Mumbai");
        assertEquals(results.get(0).getTrainId(), "T101");
    }

    @Test(testName = "Search Train - Intermediate Route", description = "Verifies searchTrains correctly identifies trains when searching between intermediate stops")
    public void testSearchTrainsWithIntermediateStations() {
        Train train = new Train();
        train.setTrainId("T102");
        train.setTrainNumber("54321");
        train.setStationTimes(createStationMap("Delhi", "Jaipur", "Ahmedabad", "Mumbai"));
        train.setStations(Arrays.asList("Delhi", "Jaipur", "Ahmedabad", "Mumbai"));

        trainService.getTrainList().clear();
        trainService.getTrainList().add(train);

        List<Train> results = trainService.searchTrains("Jaipur", "Ahmedabad");
        assertEquals(results.size(), 1, "Should find train between Jaipur and Ahmedabad");
    }

    @Test(testName = "Search Train - Reverse Route Rejection", description = "Verifies searchTrains rejects trains if destination comes before source in station ordering")
    public void testSearchTrainsWithReverseRouteShouldFail() {
        Train train = new Train();
        train.setTrainId("T103");
        train.setTrainNumber("11223");
        train.setStationTimes(createStationMap("Delhi", "Jaipur", "Mumbai"));
        train.setStations(Arrays.asList("Delhi", "Jaipur", "Mumbai"));

        trainService.getTrainList().clear();
        trainService.getTrainList().add(train);

        // Mumbai is after Delhi in the list, searching Mumbai to Delhi should return 0 results
        List<Train> results = trainService.searchTrains("Mumbai", "Delhi");
        assertEquals(results.size(), 0, "Reverse direction should return no trains");
    }

    @Test(testName = "Search Train - Non-existent Station", description = "Verifies searchTrains returns empty list when station is not part of route")
    public void testSearchTrainsWithNonExistentStation() {
        Train train = new Train();
        train.setTrainId("T104");
        train.setStationTimes(createStationMap("Delhi", "Jaipur", "Mumbai"));
        train.setStations(Arrays.asList("Delhi", "Jaipur", "Mumbai"));

        trainService.getTrainList().clear();
        trainService.getTrainList().add(train);

        List<Train> results = trainService.searchTrains("Kolkata", "Chennai");
        assertTrue(results.isEmpty(), "Non-existent stations should return an empty list");
    }
}
