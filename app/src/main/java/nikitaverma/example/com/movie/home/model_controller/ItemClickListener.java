package nikitaverma.example.com.movie.home.model_controller;

import android.view.View;

/**
 * onClickListener for Adapter class
 */
public interface ItemClickListener {

    void onClick(View view, int position);
    void onClick(String apiName);

}
