package mk.ukim.finki.wp.kol2025g2.web;

import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;

public class SkiSlopeController {

    /**
     * This method should use the "list.html" template to display all ski slopes.
     * The method should be mapped on paths '/' and '/ski-slopes'.
     * The arguments that this method takes are optional and can be 'null'.
     * The filtered ski slopes that are the result of the call
     * findPage method from the SkiSlopeService should be displayed.
     * If you want to return a paginated result, you should also pass the page number and the page size as arguments.
     *
     * @param name       Filters ski slopes whose names contain the specified text.
     * @param length     Filters ski slopes longer than the specified length in meters.
     * @param difficulty Filters ski slopes matching the specified difficulty level.
     * @param skiResort  Filters ski slopes belonging to the specified ski center.
     * @param pageNum    The page number
     * @param pageSize   The number of items per page
     * @return The view "list.html".
     */
    public String listAll(String name, Integer length, SlopeDifficulty difficulty, Long skiResort, Integer pageNum, Integer pageSize) {
        return "";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/ski-slopes/add'.
     *
     * @return The view "form.html".
     */
    public String showAdd() {
        return "";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the ski slope that is updated.
     * The method should be mapped on path '/ski-slopes/edit/[id]'.
     *
     * @return The view "form.html".
     */
    public String showEdit(Long id) {
        return "";
    }

    /**
     * This method should create a ski slope given the arguments it takes.
     * The method should be mapped on path '/ski-slopes'.
     * After the ski slope is created, all ski slopes should be displayed.
     *
     * @return The view "list.html".
     */
    public String create(String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
        return "";
    }

    /**
     * This method should update a ski slope given the arguments it takes.
     * The method should be mapped on path '/ski-slopes/[id]'.
     * After the ski slope is updated, all ski slopes should be displayed.
     *
     * @return The view "list.html".
     */
    public String update(Long id, String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
        return "";
    }

    /**
     * This method should delete the ski slope that has the appropriate identifier.
     * The method should be mapped on path '/ski-slopes/delete/[id]'.
     * After the ski slope is deleted, all ski slopes should be displayed.
     *
     * @return The view "list.html".
     */
    public String delete(Long id) {
        return "";
    }

    /**
     * This method should close a ski slope.
     * The method should be mapped on path '/ski-slopes/close/[id]'.
     * After the selected ski slope is closed, all ski slopes should be displayed.
     *
     * @param id The ID of the ski slope to close.
     * @return The view "list.html".
     */
    public String close(Long id) {
        return "";
    }

}