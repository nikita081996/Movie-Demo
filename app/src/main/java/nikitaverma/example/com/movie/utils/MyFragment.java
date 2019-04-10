package nikitaverma.example.com.movie.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MyFragment  {

    public static void getFragment(FragmentManager fm, Fragment fragment, int resource, String  tag) {

        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentByTag ( tag ) == null) {
            ft.add(resource, fragment);
            ft.addToBackStack(tag);
            ft.replace(resource, fragment);
            ft.commit();
        } else {
            ft.show(fm.findFragmentByTag(tag));
            ft.commit();
        }

    }
}
