package jpa.dao;

import jpa.repository.DistanceRepository;
import jpa.repository.LineRepository;
import jpa.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Transactional
public class InsertWorker {
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private DistanceRepository distanceRepository;

    public void insertTransaction(String lineName, String lineColor, String[] stationNames) {
        System.out.println("insertTransaction() : " + TransactionSynchronizationManager.getCurrentTransactionName());
        Line line = lineRepository.save(new Line(lineName, lineColor));
        for ( int ix = 0 ; ix < stationNames.length ; ix ++ ) {
            Station station = getStation(stationNames[ix]);
            System.out.println(station);
            distanceRepository.save(new Distance(line, station, ix, ix + 1));
        }
    }

    public Station getStation(String stationName) {
        System.out.println("getStation() : " + TransactionSynchronizationManager.getCurrentTransactionName());
        return stationRepository.findByName(stationName)
                .orElseGet(() -> stationRepository.save(new Station(stationName)));
    }
}
