package nikitaverma.example.com.movie.home.view_controller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nikitaverma.example.com.movie.Constants;
import nikitaverma.example.com.movie.R;
import nikitaverma.example.com.movie.helpers.ApiClient;
import nikitaverma.example.com.movie.helpers.ApiInterface;
import nikitaverma.example.com.movie.helpers.MakeCalls;
import nikitaverma.example.com.movie.home.model.movie_success_response.MovieResponse;
import nikitaverma.example.com.movie.home.model_controller.ItemClickListener;
import nikitaverma.example.com.movie.home.model_controller.MovieAdapter;
import nikitaverma.example.com.movie.utils.LoaderUtils;
import retrofit2.Call;

public class TopRatedFragment extends Fragment implements MakeCalls.CallListener, ItemClickListener {
    private static MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerViewAllTopRated;
    private Context mContext;

    public TopRatedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);
        bindAllView(view);
        registerAllListener();
        return view;
    }

    void bindAllView(View view) {
        mRecyclerViewAllTopRated = view.findViewById(R.id.recycler_view_all_top_rated);

    }

    void registerAllListener() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callMovieApi(Constants.TOP_RATED_API);
    }

    void callMovieApi(String api_name) {
        LoaderUtils.showLoader((Activity) mContext);
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class);
        Call<MovieResponse> call = apiInterface.topRatedMovie(Constants.API_KEY, Constants.LANGUAGE, "1");

        MakeCalls.commonCall(call, this, api_name);


    }

    @Override
    public void onCallSuccess(@NonNull Object result, String apiName) {
        LoaderUtils.hideLoader((Activity) mContext);
        if (apiName == Constants.TOP_RATED_API) {
            MovieResponse response = (MovieResponse) result;
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
            mRecyclerViewAllTopRated.setLayoutManager(mLayoutManager);
            mRecyclerViewAllTopRated.setItemAnimator(new DefaultItemAnimator());
            mMovieAdapter = new MovieAdapter(response.getResults(), mContext, R.layout.movie_data, this);
            mRecyclerViewAllTopRated.setAdapter(mMovieAdapter);

        }
    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {

    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onClick(String apiName) {

    }
}
