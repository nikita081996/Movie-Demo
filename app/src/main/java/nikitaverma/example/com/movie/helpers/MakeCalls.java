package nikitaverma.example.com.movie.helpers;

import android.support.annotation.NonNull;

import nikitaverma.example.com.movie.utils.LoggerUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class to handle all response of all calls make to the server
 * Created by Nikita Verma on 21/02/2019
 */
public class MakeCalls {

    private static CallListener mListener = null;

    /**
     * Common call for all activities to handle common response
     */
    public static void commonCall(Call call, @NonNull CallListener callListerner, final String apiName) {
        mListener = callListerner;

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                LoggerUtils.d("response", response.body() + " ");
                if ((response.body() != null) && response.isSuccessful()) {
                    if (mListener != null) {
                        mListener.onCallSuccess(response.body(), apiName);
                    }
                } else {
                    //    assert response.body() != null;
                    mListener.onCallFailure(response.errorBody(), apiName);


                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                LoggerUtils.d("error", t.getMessage() + " ");
                mListener.onCallFailure(t.getMessage(), apiName);
            }
        });
    }

    /**
     * Listerner to listion success or failure network calls
     */
    public interface CallListener {
        void onCallSuccess(@NonNull Object result, final String apiName);

        void onCallFailure(@NonNull Object result, final String apiName);
    }

}
