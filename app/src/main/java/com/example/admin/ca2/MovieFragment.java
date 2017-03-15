package com.example.admin.ca2; /**
 * Created by admin on 13/03/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieFragment extends Fragment {
    private Movie mMovie;
    private TextView mTitle;
    private ImageView mImage;
    private TextView mCategory;
    private TextView mDescription;
    private TextView mDirector;
    private TextView mDate;
    private TextView mActors;
    public static final String EXTRA_MOVIE_ID = "com.example.admin.ca2.movie_id";
    private int movie_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movie_id = getActivity().getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
        Log.d("ID: ", this.movie_id + "");
        mMovie = MovieModel.get(getActivity()).getMovie(this.movie_id);
        Log.d("Movie Fragment", mMovie.toString());
        Log.d("Movie Fragment", "id: " + this.movie_id);
//        Log.d("Title: ", mMovie.getTitle());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_fragment, parent, false);


        Log.d("MovieTitle ", mMovie.getTitle());
        Log.d("MovieID ", mMovie.getId() + "");
        Log.d("Photo link", mMovie.getPhotoLink());
        Log.d("Release Date", mMovie.getReleaseDate().toString());
//        Log.d("Actors", mMovie.getActors().toString());
        Log.d("Director", mMovie.getDirector());
        Log.d("Desc", mMovie.getDescription());
        Log.d("Photo", mMovie.getPhoto().toString());

        //sets movie title
        mTitle = (TextView) v.findViewById(R.id.movieTitle);
        mTitle.setText("Title: " + mMovie.getTitle());

        //sets image view
        mImage = (ImageView) v.findViewById(R.id.imageView);
        mImage.setImageBitmap(mMovie.getPhoto());

        //sets category
        mCategory = (TextView) v.findViewById(R.id.movieCategory);
        mCategory.setText("Category: " + mMovie.getCategory());

        //sets description
        mDescription = (TextView) v.findViewById(R.id.movieDescription);
        mDescription.setText("Description: " + mMovie.getDescription());

        //sets date
        mDate = (TextView) v.findViewById(R.id.movieDate);
        mDate.setText("Release Date: " + mMovie.getReleaseDate().toString());

        //sets director
        mDirector = (TextView) v.findViewById(R.id.movieDirector);
        mDirector.setText("Director: " + mMovie.getDirector());

        //setActors
        mActors = (TextView) v.findViewById(R.id.movieActors);
        String actorsNames = "";
        String comma = ", ";
        for (int i=0; i < mMovie.getActors().length; i++) {
            if (i == mMovie.getActors().length-1) {
                comma = "";
            }
            actorsNames = actorsNames + mMovie.getActors()[i] + comma;
        }

        mActors.setText("Actors: " + actorsNames);

        //set view screen
        return v;
    }
}

