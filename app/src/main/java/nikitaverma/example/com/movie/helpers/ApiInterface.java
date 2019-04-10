package nikitaverma.example.com.movie.helpers;

import nikitaverma.example.com.movie.Constants;
import nikitaverma.example.com.movie.home.model.movie_success_response.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(Constants.TOP_RATED_END_POINTS)
    @Headers("Content-Type: application/json")
    Call<MovieResponse> topRatedMovie(@Query("api_key") String api_key, @Query("language") String language, @Query("page") String page);

    @GET(Constants.UPCOMING_END_POINTS)
    @Headers("Content-Type: application/json")
    Call<MovieResponse> upcomingMovie(@Query("api_key") String api_key, @Query("language") String language, @Query("page") String page);

    @GET(Constants.NOW_PLAYING_END_POINTS)
    @Headers("Content-Type: application/json")
    Call<MovieResponse> nowPlayingMovie(@Query("api_key") String api_key, @Query("language") String language, @Query("page") String page);

    @GET(Constants.POPULAR_END_POINTS)
    @Headers("Content-Type: application/json")
    Call<MovieResponse> popularMovie(@Query("api_key") String api_key, @Query("language") String language, @Query("page") String page);

}
