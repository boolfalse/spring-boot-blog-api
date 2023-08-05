package am.github.springbootblogapi.validations;

import java.util.Arrays;
import java.util.Objects;

public class PostValidationFilter {
    public static PostFilteredParameters getAll(String page, String per_page, String sort_by, String order_by) {
        int pageNumber;
        int perPageNumber;
        String sortBy;
        String orderBy;
        try {
            pageNumber = Integer.parseInt(page);
            perPageNumber = Integer.parseInt(per_page);
            if (pageNumber < 1 || pageNumber > 1000 || perPageNumber < 1 || perPageNumber > 100) {
                pageNumber = 1;
                perPageNumber = 10;
            }

            String[] fields = {"id", "title", "description"};
            if (Arrays.asList(fields).contains(sort_by)) {
                sortBy = sort_by;
            } else {
                sortBy = "id";
            }

            if (Objects.equals(order_by, "asc") || Objects.equals(order_by, "desc")) {
                orderBy = order_by;
            } else {
                orderBy = "asc";
            }
        } catch (NumberFormatException e) {
            pageNumber = 1;
            perPageNumber = 10;
            sortBy = "id";
            orderBy = "asc";
        }

        return new PostFilteredParameters(pageNumber, perPageNumber, sortBy, orderBy);
    }
}
