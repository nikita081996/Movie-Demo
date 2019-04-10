package nikitaverma.example.com.movie.home.view_controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import nikitaverma.example.com.movie.Constants;
import nikitaverma.example.com.movie.R;
import nikitaverma.example.com.movie.helpers.ApiClient;
import nikitaverma.example.com.movie.helpers.ApiInterface;
import nikitaverma.example.com.movie.helpers.MakeCalls;
import nikitaverma.example.com.movie.home.model.error_response.ErrorResponse;
import nikitaverma.example.com.movie.home.model.movie_success_response.MovieResponse;
import nikitaverma.example.com.movie.home.model_controller.ItemClickListener;
import nikitaverma.example.com.movie.home.model_controller.MovieAdapter;
import nikitaverma.example.com.movie.utils.LoaderUtils;
import retrofit2.Call;

public class HomeFragment extends Fragment implements MakeCalls.CallListener, ItemClickListener, View.OnClickListener {

    private static MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerViewTopRated;
    private RecyclerView mRecyclerViewUpcoming;
    private RecyclerView mRecyclerViewNowPlaying;
    private RecyclerView mRecyclerViewPopular;
    private TextView mTopRatedTv;
    private TextView mTopRatedViewAllTv;
    private TextView mUpcomingTv;
    private TextView mUpcomingViewAllTv;
    private TextView mNowPlayingTv;
    private TextView mNowPlayingViewAllTv;
    private TextView mPopularTv;
    private TextView mPopularViewAllTv;
    private Context mContext;
    private ItemClickListener itemClickListener;

    public HomeFragment() {
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bindAllView(view);
        registerAllListener();
        return view;
    }

    void bindAllView(View view) {
        mRecyclerViewTopRated = view.findViewById(R.id.recycler_view_top_rated);
        mRecyclerViewUpcoming = view.findViewById(R.id.recycler_view_upcoming);
        mRecyclerViewNowPlaying = view.findViewById(R.id.recycler_view_now_playing);
        mRecyclerViewPopular = view.findViewById(R.id.recycler_view_popular);

        mTopRatedTv = view.findViewById(R.id.top_rated_tv);
        mTopRatedViewAllTv = view.findViewById(R.id.top_rated_view_all_tv);
        mUpcomingTv = view.findViewById(R.id.upcoming_tv);
        mUpcomingViewAllTv = view.findViewById(R.id.upcoming_view_all_tv);
        mNowPlayingTv = view.findViewById(R.id.now_playing_tv);
        mNowPlayingViewAllTv = view.findViewById(R.id.now_playing_view_all_tv);
        mPopularTv = view.findViewById(R.id.popular_tv);
        mPopularViewAllTv = view.findViewById(R.id.popular_view_all_tv);
    }

    void registerAllListener() {
        mTopRatedViewAllTv.setOnClickListener(this);
        mUpcomingViewAllTv.setOnClickListener(this);
        mNowPlayingViewAllTv.setOnClickListener(this);
        mPopularViewAllTv.setOnClickListener(this);
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
        callMovieApi(Constants.UPCOMING_API);
        callMovieApi(Constants.NOW_PLAYING_API);
        callMovieApi(Constants.POPULAR_API);
    }

    void callMovieApi(String api_name) {
        LoaderUtils.showLoader((Activity) mContext);
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class);
        Call<MovieResponse> call = null;

        switch (api_name) {
            case Constants.TOP_RATED_API:
                call = apiInterface.topRatedMovie(Constants.API_KEY, Constants.LANGUAGE, "1");
                break;

            case Constants.UPCOMING_API:
                call = apiInterface.upcomingMovie(Constants.API_KEY, Constants.LANGUAGE, "1");
                break;

            case Constants.NOW_PLAYING_API:
                call = apiInterface.nowPlayingMovie(Constants.API_KEY, Constants.LANGUAGE, "1");
                break;

            case Constants.POPULAR_API:
                call = apiInterface.popularMovie(Constants.API_KEY, Constants.LANGUAGE, "1");
                break;
            default:
        }
        if (call != null)
            MakeCalls.commonCall(call, this, api_name);


    }

    @Override
    public void onCallSuccess(@NonNull Object result, String apiName) {
        LoaderUtils.hideLoader((Activity) mContext);
        if (apiName == Constants.TOP_RATED_API) {
            mTopRatedTv.setVisibility(View.VISIBLE);
            mTopRatedViewAllTv.setVisibility(View.VISIBLE);
            MovieResponse response = (MovieResponse) result;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewTopRated.setLayoutManager(mLayoutManager);
            mRecyclerViewTopRated.setItemAnimator(new DefaultItemAnimator());
            mMovieAdapter = new MovieAdapter(response.getResults(), mContext, R.layout.movie_data, this);
            mRecyclerViewTopRated.setAdapter(HomeFragment.mMovieAdapter);

        } else if (apiName == Constants.UPCOMING_API) {
            mUpcomingTv.setVisibility(View.VISIBLE);
            mUpcomingViewAllTv.setVisibility(View.VISIBLE);
            MovieResponse response = (MovieResponse) result;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewUpcoming.setLayoutManager(mLayoutManager);
            mRecyclerViewUpcoming.setItemAnimator(new DefaultItemAnimator());
            mMovieAdapter = new MovieAdapter(response.getResults(), mContext, R.layout.movie_data, this);
            mRecyclerViewUpcoming.setAdapter(HomeFragment.mMovieAdapter);

        } else if (apiName == Constants.NOW_PLAYING_API) {
            mNowPlayingTv.setVisibility(View.VISIBLE);
            mNowPlayingViewAllTv.setVisibility(View.VISIBLE);
            MovieResponse response = (MovieResponse) result;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewNowPlaying.setLayoutManager(mLayoutManager);
            mRecyclerViewNowPlaying.setItemAnimator(new DefaultItemAnimator());
            mMovieAdapter = new MovieAdapter(response.getResults(), mContext, R.layout.movie_data, this);
            mRecyclerViewNowPlaying.setAdapter(HomeFragment.mMovieAdapter);

        } else if (apiName == Constants.POPULAR_API) {
            mPopularTv.setVisibility(View.VISIBLE);
            mPopularViewAllTv.setVisibility(View.VISIBLE);
            MovieResponse response = (MovieResponse) result;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewPopular.setLayoutManager(mLayoutManager);
            mRecyclerViewPopular.setItemAnimator(new DefaultItemAnimator());
            mMovieAdapter = new MovieAdapter(response.getResults(), mContext, R.layout.movie_data, this);
            mRecyclerViewPopular.setAdapter(HomeFragment.mMovieAdapter);

        }

    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {
        LoaderUtils.hideLoader((Activity) mContext);
        ErrorResponse errorResponse = (ErrorResponse) result;
        //  Toast.makeText(mContext,errorResponse.getStatus_message(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(String apiName) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_rated_view_all_tv:
                itemClickListener.onClick(Constants.TOP_RATED_API);
                break;
            case R.id.upcoming_view_all_tv:
                itemClickListener.onClick(Constants.UPCOMING_API);
                break;
            case R.id.now_playing_view_all_tv:
                itemClickListener.onClick(Constants.NOW_PLAYING_API);
                break;
            case R.id.popular_view_all_tv:
                itemClickListener.onClick(Constants.POPULAR_API);
                break;
            default:

        }
    }
}
