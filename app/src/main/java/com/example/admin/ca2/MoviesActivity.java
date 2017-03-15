package com.example.admin.ca2;

/**
 * Created by admin on 30/11/2016.
 */
import android.support.v4.app.Fragment;

public class MoviesActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MovieFragment();
    }


}


