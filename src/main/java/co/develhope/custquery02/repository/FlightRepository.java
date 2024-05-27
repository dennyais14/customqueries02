package co.develhope.custquery02.repository;

import co.develhope.custquery02.entities.Flight;
import co.develhope.custquery02.entities.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    List<Flight> findByFlightStatus(FlightStatus flightStatus);
    List<Flight> findByFlightStatusIn(List<FlightStatus> flightStatuses);

}
