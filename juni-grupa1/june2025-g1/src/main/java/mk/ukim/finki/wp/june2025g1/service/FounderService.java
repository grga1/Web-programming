package mk.ukim.finki.wp.june2025g1.service;

import mk.ukim.finki.wp.june2025g1.model.Founder;
import mk.ukim.finki.wp.june2025g1.model.exceptions.InvalidFounderIdException;

import java.util.List;

public interface FounderService {

    /**
     * @param id The id of the founder that we want to obtain
     * @return The founder with the appropriate id
     * @throws InvalidFounderIdException when there is no founder with the given id
     */
    Founder findById(Long id);

    /**
     * @return List of all founders in the database
     */
    List<Founder> listAll();

    /**
     * This method is used to create a new founder and save it in the database.
     *
     * @param name  The name of the founder
     * @param email The email of the founder
     * @return The founder that is created. The id should be generated when the founder is created.
     */
    Founder create(String name, String email);
}
