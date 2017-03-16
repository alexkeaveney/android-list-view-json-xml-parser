package com.example.admin.ca2;

/**
 * Created by admin on 30/11/2016.
 */
import android.support.v4.app.Fragment;

public class MoviesActivity extends SingleFragmentActivity {

    @Override

    //creates a Movie Fragment that holds the view Movie data
    protected Fragment createFragment() {
        return new MovieFragment();
    }


}


