package movies.popular.soliton.popularmovies.activity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

import movies.popular.soliton.popularmovies.R;
import movies.popular.soliton.popularmovies.adapter.Adapter;
import movies.popular.soliton.popularmovies.entity.Movie;

import static movies.popular.soliton.popularmovies.util.Constant.API_KEY;
import static movies.popular.soliton.popularmovies.util.Constant.POSTER_PATH;
import static movies.popular.soliton.popularmovies.util.Constant.IMG_URL;
import static movies.popular.soliton.popularmovies.util.Constant.OVERVIEW;
import static movies.popular.soliton.popularmovies.util.Constant.POPULAR;
import static movies.popular.soliton.popularmovies.util.Constant.RELEASE_DATE;
import static movies.popular.soliton.popularmovies.util.Constant.RESULTS;
import static movies.popular.soliton.popularmovies.util.Constant.SPAN_COUNT;
import static movies.popular.soliton.popularmovies.util.Constant.TITLE;
import static movies.popular.soliton.popularmovies.util.Constant.TOP_RATED;
import static movies.popular.soliton.popularmovies.util.Constant.URL;
import static movies.popular.soliton.popularmovies.util.Constant.VOTE_AVERAGE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private int currentSelection;
    private boolean isGettingTopRated;
    private List<Movie> topRatedMovieList;
    private List<Movie> popularMovieList;
    private View prgrsBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new RequestMovieTask().execute(Uri.parse(URL)
                .buildUpon()
                .appendPath(POPULAR)
                .appendQueryParameter(API_KEY, getString(R.string.api_key))
                .build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        List<String> sortTypes = new ArrayList<>();
        sortTypes.add(getString(R.string.popular));
        sortTypes.add(getString(R.string.top_rated));
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortTypes));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentSelection != i) {
                    switch (i) {
                        case 0:
                            mAdapter.setList(popularMovieList);
                            mAdapter.notifyDataSetChanged();
                            break;
                        case 1:
                            if (topRatedMovieList == null) {
                                prgrsBar.setVisibility(View.VISIBLE);
                                isGettingTopRated = true;
                                new RequestMovieTask().execute(Uri.parse(URL)
                                        .buildUpon()
                                        .appendPath(TOP_RATED)
                                        .appendQueryParameter(API_KEY, getString(R.string.api_key))
                                        .build());
                            } else {
                                mAdapter.setList(topRatedMovieList);
                                mAdapter.notifyDataSetChanged();
                            }
                    }
                    currentSelection = i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private class RequestMovieTask extends AsyncTask<Uri, Void, String> {
        private String TAG = RequestMovieTask.class.getSimpleName();

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
            prgrsBar = findViewById(R.id.prgs_bar);
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
                mAdapter = new Adapter(movieList, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                if (isGettingTopRated) {
                    topRatedMovieList = movieList;
                } else {
                    popularMovieList = movieList;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
