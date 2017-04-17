package movies.popular.soliton.popularmovies.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import movies.popular.soliton.popularmovies.activity.MainActivity;
import movies.popular.soliton.popularmovies.adapter.Adapter;
import movies.popular.soliton.popularmovies.entity.Movie;

import static movies.popular.soliton.popularmovies.util.Constant.IMG_URL;
import static movies.popular.soliton.popularmovies.util.Constant.OVERVIEW;
import static movies.popular.soliton.popularmovies.util.Constant.POSTER_PATH;
import static movies.popular.soliton.popularmovies.util.Constant.RELEASE_DATE;
import static movies.popular.soliton.popularmovies.util.Constant.RESULTS;
import static movies.popular.soliton.popularmovies.util.Constant.TITLE;
import static movies.popular.soliton.popularmovies.util.Constant.VOTE_AVERAGE;

public class RequestMovieTask extends AsyncTask<Uri, Void, String> {
    private String TAG = RequestMovieTask.class.getSimpleName();
    private View prgrsBar;
    private MainActivity activity;
    private boolean isGettingTopRated;
    private Adapter mAdapter;


    public RequestMovieTask(View prgrsBar, MainActivity activity, boolean isGettingTopRated, Adapter adapter) {
        this.prgrsBar = prgrsBar;
        this.activity = activity;
        this.isGettingTopRated = isGettingTopRated;
        this.mAdapter = adapter;
    }

    @Override
    protected String doInBackground(Uri... params) {
        String json = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(params[0].toString()).openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in).useDelimiter("//A");
            json = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String s) {
        prgrsBar.setVisibility(View.GONE);
        try {
            List<Movie> movieList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                movieList.add(new Movie(object.getString(TITLE), IMG_URL + object.getString(POSTER_PATH),
                        object.getString(RELEASE_DATE), object.getDouble(VOTE_AVERAGE), object.getString(OVERVIEW)));
            }
            Log.i(TAG, "is movieList empty=>" + movieList.isEmpty());
            if (isGettingTopRated) {
                activity.setTopRatedMovieList(movieList);
            } else {
                activity.setPopularMovieList(movieList);
            }
            mAdapter.setList(movieList);
            activity.getRecyclerView().setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setGettingTopRated(boolean gettingTopRated) {
        this.isGettingTopRated = gettingTopRated;
    }
}
