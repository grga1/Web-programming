package mk.ukim.finki.wp.june2025g1.service;

import mk.ukim.finki.wp.june2025g1.model.Industry;
import mk.ukim.finki.wp.june2025g1.model.Startup;
import mk.ukim.finki.wp.june2025g1.model.exceptions.InvalidStartupIdException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StartupService {

    /**
     * @return List of all startups in the database
     */
    List<Startup> listAll();

    /**
     * @param id The id of the startup that we want to obtain
     * @return The startup with the appropriate id
     * @throws InvalidStartupIdException when there is no startup with the given id
     */
    Startup findById(Long id);

    /**
     * This method is used to create a new startup and save it in the database.
     *
     * @param name        The name of the startup
     * @param valuation   The valuation of the startup
     * @param yearFounded The year the startup was founded
     * @param industry    The industry of the startup
     * @param founderId   The id of the founder of the startup
     * @return The startup that is created. The id should be generated when the startup is created.
     */
    Startup create(String name, Double valuation, Integer yearFounded, Industry industry, Long founderId);

    /**
     * This method is used to update a startup and save it in the database.
     *
     * @param id          The id of the startup that is being updated
     * @param name        The name of the startup
     * @param valuation   The valuation of the startup
     * @param yearFounded The year the startup was founded
     * @param industry    The industry of the startup
     * @param founderId   The id of the founder of the startup
     * @return The startup that is updated.
     * @throws InvalidStartupIdException when there is no startup with the given id
     */
    Startup update(Long id, String name, Double valuation, Integer yearFounded, Industry industry, Long founderId);

    /**
     * This method deletes a startup from the database.
     *
     * @param id The id of the startup that we want to delete
     * @return The startup that is deleted.
     * @throws InvalidStartupIdException when there is startup with the given id
     */
    Startup delete(Long id);

    /**
     * This method closes a startup.
     *
     * @param id The ID of the startup we want to deactivate.
     * @return The updated startup.
     * @throws InvalidStartupIdException If the startup with the given ID does not exist.
     */
    Startup deactivate(Long id);

    /**
     * Returns a page of startups that match the given criteria.
     *
     * @param name        Filters startups whose names contain the specified text.
     * @param valuation   Filters startups with a valuation greater than the specified value.
     * @param yearFounded Filters startups founded after the specified year.
     * @param industry    Filters startups by the specified industry.
     * @param founderId   Filters startups by the specified founder ID.
     * @param pageNum     The page number.
     * @param pageSize    The number of items per page.
     * @return The page of startups that match the given criteria.
     */
    Page<Startup> findPage(String name, Double valuation, Integer yearFounded, Industry industry, Long founderId, int pageNum, int pageSize);
}
