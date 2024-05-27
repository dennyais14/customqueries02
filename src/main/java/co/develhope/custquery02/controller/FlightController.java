package co.develhope.custquery02.controller;

import co.develhope.custquery02.entities.Flight;
import co.develhope.custquery02.entities.FlightStatus;
import co.develhope.custquery02.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    // Provision n flights
    @GetMapping("/provision")
    public List<Flight> provisionFlights(@RequestParam(defaultValue = "100") int n) {
        List<Flight> flights = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Flight flight = new Flight();
            flight.setDescription("Flight " + i);
            flight.setFromAirport("Airport " + i);
            flight.setToAirport("Airport " + i);
            flight.setFlightStatus(FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)]);
            flights.add(flight);
        }
        return flights;
    }

    // Retrieve all flights in ascending order by fromAirport
    @GetMapping
    public Page<Flight> getAllFlights(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fromAirport").ascending());
        return flightRepository.findAll(pageable);
    }

    // Retrieve all ONTIME flights
    @GetMapping("/ontime")
    public List<Flight> retrieveOntimeFlights() {
        return flightRepository.findByFlightStatus(FlightStatus.ONTIME);
    }

    // Retrieve all flights in p1 or p2 status
    @GetMapping("/status/{p1}/{p2}")
    public List<Flight> retrieveFlightsByStatus(@PathVariable String p1, @PathVariable String p2) {
        return flightRepository.findByFlightStatusIn(Arrays.asList(FlightStatus.valueOf(p1), FlightStatus.valueOf(p2)));
    }
}
