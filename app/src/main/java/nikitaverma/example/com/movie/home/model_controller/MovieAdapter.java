package nikitaverma.example.com.movie.home.model_controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nikitaverma.example.com.movie.Constants;
import nikitaverma.example.com.movie.R;
import nikitaverma.example.com.movie.home.model.movie_success_response.Results;
import nikitaverma.example.com.movie.home.model_controller.ItemClickListener;
import nikitaverma.example.com.movie.utils.ToastUtils;

/**
 * Adapter class for Restaurant Details of mainActivity
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.RestaurantViewHolder> {

    private final List<Results> mResultList;
    private final Context mContext;
    private int mResource;
    ItemClickListener itemClickListener;

    public MovieAdapter(List<Results> resturantList, Context context, int resource,ItemClickListener itemClickListener ) {
        this.mResultList = resturantList;
        this.mContext = context;
        this.mResource = resource;
        this.itemClickListener = itemClickListener;
    }

    /**
     * Inflate xml file to java
     *
     * @param parent
     * @param viewType
     * @return type of view holder
     */
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mResource, parent, false);

        return new RestaurantViewHolder(itemView,itemClickListener );
    }

    /**
     * Bind each data to view
     *
     * @param holder
     * @param position of holder
     */
    @Override
    public void onBindViewHolder(@NonNull final RestaurantViewHolder holder, int position) {
        final Float rating = Float.parseFloat(mResultList.get(position).getVote_average())/2;

        holder.ratingBarTv.setText(rating+"");
        holder.movieTitleTv.setText(""+ mResultList.get(position).getTitle());
        holder.ratingBar.setRating(rating);

        if (!(mResultList.get(position).getBackdrop_path()).isEmpty()) {
            Picasso.with(mContext).load(Constants.IMAGE_BASE_URL + mResultList.get(position).getBackdrop_path())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .noFade().resize(200, 200)
                    .into(holder.movieImage, new Callback() {
                        @Override
                        public void onError() {
                            //  holder.imageView.setImageDrawable(ic_launcher_foreground);
                            holder.progressBar.setVisibility(View.GONE);
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            holder.progressBar.setVisibility(View.GONE);
                        }

                    });
        } else {
            holder.movieImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher_foreground));
            holder.progressBar.setVisibility(View.GONE);
        }

        /*holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ToastUtils.showLongToast(mContext, rating+"");
                *//*Intent intent = new Intent(mContext, RestaurantDetailsActivity.class);
                intent.putExtra(ADAPTER_POSITION, position);
                intent.putExtra(LIST_OF_RESTAURANT_DETAILS, (Serializable) mRestaurantList);*//*
                //mContext.startActivity(intent);
            }
        });*/
    }
    //  }

    /**
     * @return size of listView
     */
    @Override
    public int getItemCount() {
        if (mResultList != null)
            return mResultList.size();
        else
            return 0;
    }

    /*@Override
    public void onClick(View view, int position) {
        ToastUtils.showLongToast(mContext,"toasting");
    }*/


    /**
     * holder to each object in view
     */
    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView movieTitleTv;
        private TextView ratingBarTv;
        private RatingBar ratingBar;
        private ImageView movieImage;
        private ProgressBar progressBar;
        private ItemClickListener clickListener;

        RestaurantViewHolder(View view, ItemClickListener clickListener) {
            super(view);
            movieTitleTv = view.findViewById(R.id.movie_title);
            ratingBar = view.findViewById(R.id.rating);
            movieImage = view.findViewById(R.id.movie_image);
            progressBar = view.findViewById(R.id.progressbar);
            ratingBarTv = view.findViewById(R.id.rating_tv);

            this.clickListener = clickListener;
            view.setOnClickListener(this);
        }

        /*void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }*/

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        //    clickListener.onClick(view, getPosition());
        }
    }

}