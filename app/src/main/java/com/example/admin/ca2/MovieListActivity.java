package com.example.admin.ca2;

/**
 * Created by admin on 13/03/2017.
 */

import android.support.v4.app.Fragment;

public class MovieListActivity extends SingleFragmentActivity {

    @Override
    //creates a movie list fragment to hold the movie items
    protected Fragment createFragment() {
        return new MovieListFragment();
    }

}
