package org.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class handling train lookup, station route verification,
 * and train inventory operations.
 */
public class TrainService {

    private List<Train> trainList;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private String trainsPath = "app/src/main/java/org/booking/localdb/trains.json";

    public TrainService() throws IOException {
        loadTrains();
    }
    
    public TrainService(String customPath) throws IOException {
        this.trainsPath = customPath;
        loadTrains();
    }

    private void loadTrains() throws IOException {
        File trainsFile = new File(trainsPath);
        if (!trainsFile.exists() && trainsPath.equals("app/src/main/java/org/booking/localdb/trains.json")) {
            trainsFile = new File("src/main/java/org/booking/localdb/trains.json");
        }
        if (trainsFile.exists() && trainsFile.length() > 0) {
            trainList = objectMapper.readValue(trainsFile, new TypeReference<List<Train>>() {});
        } else {
            trainList = new ArrayList<>();
        }
    }

    /**
     * Filters available trains that travel from source to destination in valid route order.
     *
     * @param source      Departure station name.
     * @param destination Arrival station name.
     * @return List of matching Train entities.
     */
    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
    }

    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();
        if (stationOrder == null || stationOrder.isEmpty()) return false;

        int sourceIndex = -1;
        int destIndex = -1;

        for (int i = 0; i < stationOrder.size(); i++) {
            if (stationOrder.get(i).equalsIgnoreCase(source)) {
                sourceIndex = i;
            }
            if (stationOrder.get(i).equalsIgnoreCase(destination)) {
                destIndex = i;
            }
        }

        return sourceIndex != -1 && destIndex != -1 && sourceIndex < destIndex;
    }

    public List<Train> getTrainList() {
        return trainList;
    }
}
