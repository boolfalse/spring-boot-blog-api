package am.github.springbootblogapi.validations;

import lombok.Getter;

@Getter
public class PostFilteredParameters {
    private final int pageNumber;
    private final int perPageNumber;
    private final String sortBy;
    private final String orderBy;

    public PostFilteredParameters(int pageNumber, int perPageNumber, String sortBy, String orderBy) {
        this.pageNumber = pageNumber;
        this.perPageNumber = perPageNumber;
        this.sortBy = sortBy;
        this.orderBy = orderBy;
    }
}
