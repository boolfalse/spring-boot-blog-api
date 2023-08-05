package am.github.springbootblogapi.validations;

public class PostFilteredParameters {
    private int pageNumber;
    private int perPageNumber;
    private String sortBy;
    private String orderBy;

    public PostFilteredParameters(int pageNumber, int perPageNumber, String sortBy, String orderBy) {
        this.pageNumber = pageNumber;
        this.perPageNumber = perPageNumber;
        this.sortBy = sortBy;
        this.orderBy = orderBy;
    }

    // Getters for the filtered parameters
    public int getPageNumber() {
        return pageNumber;
    }
    public int getPerPageNumber() {
        return perPageNumber;
    }
    public String getSortBy() {
        return sortBy;
    }
    public String getOrderBy() {
        return orderBy;
    }
}
