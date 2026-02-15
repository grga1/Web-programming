package mk.ukim.finki.wp.jan2025g2.service.impl;

import mk.ukim.finki.wp.jan2025g2.model.NationalPark;
import mk.ukim.finki.wp.jan2025g2.model.ParkLocation;
import mk.ukim.finki.wp.jan2025g2.model.ParkType;
import mk.ukim.finki.wp.jan2025g2.model.exceptions.InvalidNationalParkIdException;
import mk.ukim.finki.wp.jan2025g2.repository.NationalParkRepository;
import mk.ukim.finki.wp.jan2025g2.service.NationalParkService;
import mk.ukim.finki.wp.jan2025g2.service.ParkLocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static mk.ukim.finki.wp.jan2025g2.service.FieldFilterSpecification.*;

@Service
public class NationalParkServiceImpl implements NationalParkService {
    private final NationalParkRepository nationalParkRepository;
    private final ParkLocationService parkLocationService;

    public NationalParkServiceImpl(NationalParkRepository nationalParkRepository, ParkLocationService parkLocationService) {
        this.nationalParkRepository = nationalParkRepository;
        this.parkLocationService = parkLocationService;
    }

    @Override
    public List<NationalPark> listAll() {
        return nationalParkRepository.findAll();
    }

    @Override
    public NationalPark findById(Long id) {
        return nationalParkRepository.findById(id).orElseThrow(InvalidNationalParkIdException::new);
    }

    @Override
    public NationalPark create(String name, Double areaSize, Double rating, ParkType parkType, Long locationId) {
        ParkLocation parkLocation = parkLocationService.findById(locationId);
        return nationalParkRepository.save(new NationalPark(name,areaSize,rating,parkType,parkLocation));
    }

    @Override
    public NationalPark update(Long id, String name, Double areaSize, Double rating, ParkType parkType, Long locationId) {
        ParkLocation parkLocation = parkLocationService.findById(locationId);
        NationalPark nationalPark = nationalParkRepository.findById(id).orElseThrow(InvalidNationalParkIdException::new);
        nationalPark.setName(name);
        nationalPark.setAreaSize(areaSize);
        nationalPark.setRating(rating);
        nationalPark.setParkType(parkType);
        nationalPark.setLocation(parkLocation);
        return nationalParkRepository.save(nationalPark);
    }

    @Override
    public NationalPark delete(Long id) {
        NationalPark nationalPark = nationalParkRepository.findById(id).orElseThrow(InvalidNationalParkIdException::new);
        nationalParkRepository.delete(nationalPark);
        return nationalPark;
    }

    @Override
    public NationalPark close(Long id) {
        NationalPark nationalPark = nationalParkRepository.findById(id).orElseThrow(InvalidNationalParkIdException::new);
       nationalPark.setClosed(true);
       return nationalParkRepository.save(nationalPark);
    }

    @Override
    public Page<NationalPark> findPage(String name, Double areaSize, Double rating, ParkType parkType, Long locationId, int pageNum, int pageSize) {
        Specification<NationalPark> specification = Specification.allOf(
                filterContainsText(NationalPark.class, "name", name),
                greaterThan(NationalPark.class, "areaSize", areaSize),
                greaterThan(NationalPark.class, "rating",rating),
                filterEquals(NationalPark.class, "location.id", locationId),
                filterEqualsV(NationalPark.class, "parkType", parkType)
        );

        return this.nationalParkRepository.findAll(
                specification,
                PageRequest.of(pageNum, pageSize));

    }
}
