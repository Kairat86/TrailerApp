package movies.popular.soliton.popularmovies.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import movies.popular.soliton.popularmovies.BuildConfig;
import movies.popular.soliton.popularmovies.R;
import movies.popular.soliton.popularmovies.adapter.Adapter;
import movies.popular.soliton.popularmovies.entity.Movie;
import movies.popular.soliton.popularmovies.util.RequestMovieTask;

import static movies.popular.soliton.popularmovies.util.Constant.API_KEY;
import static movies.popular.soliton.popularmovies.util.Constant.POPULAR;
import static movies.popular.soliton.popularmovies.util.Constant.SPAN_COUNT_SIX;
import static movies.popular.soliton.popularmovies.util.Constant.SPAN_COUNT_THREE;
import static movies.popular.soliton.popularmovies.util.Constant.TOP_RATED;
import static movies.popular.soliton.popularmovies.util.Constant.URL;

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
        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, SPAN_COUNT_THREE);
        } else {
            layoutManager = new GridLayoutManager(this, SPAN_COUNT_SIX);
        }

        mRecyclerView.setLayoutManager(layoutManager);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(this, R.string.you_need_internet_connection, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        prgrsBar = findViewById(R.id.prgs_bar);
        mAdapter = new Adapter(this);
        new RequestMovieTask(prgrsBar, this, isGettingTopRated, mAdapter).execute(Uri.parse(URL)
                .buildUpon()
                .appendPath(POPULAR)
                .appendQueryParameter(API_KEY, BuildConfig.API_KEY)
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
                                new RequestMovieTask(prgrsBar, MainActivity.this, isGettingTopRated, mAdapter).execute(Uri.parse(URL)
                                        .buildUpon()
                                        .appendPath(TOP_RATED)
                                        .appendQueryParameter(API_KEY, BuildConfig.API_KEY)
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

    public void setTopRatedMovieList(List<Movie> topRatedMovieList) {
        this.topRatedMovieList = topRatedMovieList;
    }

    public void setPopularMovieList(List<Movie> popularMovieList) {
        this.popularMovieList = popularMovieList;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
