package movies.popular.soliton.popularmovies.entity;

import java.util.List;

public class Result {

    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Result{" +
                "results=" + results +
                '}';
    }
}
