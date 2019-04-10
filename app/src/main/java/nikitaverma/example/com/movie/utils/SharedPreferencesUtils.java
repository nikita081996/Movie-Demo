package nikitaverma.example.com.movie.utils;

import android.content.Context;
import android.content.SharedPreferences;

import nikitaverma.example.com.movie.Constants;

/**
 * SharedPreference Utility Class
 */
public class SharedPreferencesUtils {
    private final SharedPreferences sp;

    public SharedPreferencesUtils(Context context) {
        sp = context.getSharedPreferences("", Context.MODE_PRIVATE);
    }

    public String getData() {

        return sp.getString("", "");
    }


    public void setData(String email) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("", email);

        editor.apply();
    }




}
