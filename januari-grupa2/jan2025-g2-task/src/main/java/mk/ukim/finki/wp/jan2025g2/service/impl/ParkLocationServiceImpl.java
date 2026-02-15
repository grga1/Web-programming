package mk.ukim.finki.wp.jan2025g2.service.impl;

import mk.ukim.finki.wp.jan2025g2.model.ParkLocation;
import mk.ukim.finki.wp.jan2025g2.model.exceptions.InvalidParkLocationIdException;
import mk.ukim.finki.wp.jan2025g2.repository.ParkLocationRepository;
import mk.ukim.finki.wp.jan2025g2.service.ParkLocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkLocationServiceImpl implements ParkLocationService {
    private final ParkLocationRepository parkLocationRepository;

    public ParkLocationServiceImpl(ParkLocationRepository parkLocationRepository) {
        this.parkLocationRepository = parkLocationRepository;
    }

    @Override
    public ParkLocation findById(Long id) {
        return parkLocationRepository.findById(id).orElseThrow(InvalidParkLocationIdException::new);
    }

    @Override
    public List<ParkLocation> listAll() {
        return parkLocationRepository.findAll();
    }

    @Override
    public ParkLocation create(String country, String continent) {
        return parkLocationRepository.save(new ParkLocation(country,continent));
    }
}
