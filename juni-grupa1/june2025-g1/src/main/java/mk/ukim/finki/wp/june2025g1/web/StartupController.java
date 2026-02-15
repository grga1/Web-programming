package mk.ukim.finki.wp.june2025g1.web;

import mk.ukim.finki.wp.june2025g1.model.Industry;

public class StartupController {

    /**
     * This method should use the "list.html" template to display all startups.
     * The method should be mapped on paths '/' and '/startups'.
     * The arguments that this method takes are optional and can be 'null'.
     * The filtered startups that are the result of the call
     * findPage method from the StartupService should be displayed.
     * If you want to return a paginated result, you should also pass the page number and the page size as arguments.
     *
     * @param name        Filters startups whose names contain the specified text.
     * @param valuation   Filters startups with a valuation greater than the specified value.
     * @param yearFounded Filters startups founded after the specified year.
     * @param industry    Filters startups by the specified industry.
     * @param founderId   Filters startups by the specified founder ID.
     * @param pageNum     The page number.
     * @param pageSize    The number of items per page.
     * @return The view "list.html"
     */
    public String listAll(String name, Double valuation, Integer yearFounded, Industry industry, Long founderId, int pageNum, int pageSize) {
        return "";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/startups/add'.
     *
     * @return The view "form.html".
     */
    public String showAdd() {
        return "";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the startup that is updated.
     * The method should be mapped on path '/startups/edit/[id]'.
     *
     * @return The view "form.html".
     */
    public String showEdit(Long id) {
        return "";
    }

    /**
     * This method should create a new startup given the arguments it takes.
     * The method should be mapped on path '/startups'.
     * After the startup is created, all startups should be displayed.
     *
     * @param name        The name of the startup
     * @param valuation   The valuation of the startup
     * @param yearFounded The year the startup was founded
     * @param industry    The industry of the startup
     * @param founderId   The id of the founder of the startup
     * @return Redirects to the list of startups
     */
    public String create(String name, Double valuation, Integer yearFounded, Industry industry, Long founderId) {
        return "";
    }

    /**
     * This method should update an existing startup given the arguments it takes.
     * The method should be mapped on path '/startups/[id]'.
     * After the startup is updated, all startups should be displayed.
     *
     * @param id          The id of the startup that is being updated
     * @param name        The name of the startup
     * @param valuation   The valuation of the startup
     * @param yearFounded The year the startup was founded
     * @param industry    The industry of the startup
     * @param founderId   The id of the founder of the startup
     * @return Redirects to the list of startups
     */
    public String update(Long id, String name, Double valuation, Integer yearFounded, Industry industry, Long founderId) {
        return "";
    }

    /**
     * This method should delete the startup that has the appropriate identifier.
     * The method should be mapped on path '/startups/delete/[id]'.
     * After the startup is deleted, all startups should be displayed.
     *
     * @param id The ID of the startup to delete
     * @return Redirects to the list of startups
     */
    public String delete(Long id) {
        return "";
    }

    /**
     * This method should deactivate the startup that has the appropriate identifier.
     * The method should be mapped on path '/startups/deactivate/[id]'.
     * After the selected startups is closed, all startups should be displayed.
     *
     * @param id The ID of the startup to deactivate
     * @return Redirects to the list of startups
     */
    public String deactivate(Long id) {
        return "";
    }
}

