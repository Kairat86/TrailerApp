package movies.popular.soliton.popularmovies.retrofit;

import movies.popular.soliton.popularmovies.entity.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static movies.popular.soliton.popularmovies.util.Constant.API_KEY;
import static movies.popular.soliton.popularmovies.util.Constant.POPULAR;
import static movies.popular.soliton.popularmovies.util.Constant.TOP_RATED;

public interface TheMovieDBAPI {

    @GET(POPULAR)
    Call<Result> getPopularMovies(@Query(API_KEY) String api_key);

    @GET(TOP_RATED)
    Call<Result> getTopRatedMovies(@Query(API_KEY) String api_key);
}
