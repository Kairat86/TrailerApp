package movies.popular.soliton.popularmovies.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import movies.popular.soliton.popularmovies.R;

import static movies.popular.soliton.popularmovies.util.Constant.POPULAR;
import static movies.popular.soliton.popularmovies.util.Constant.SPAN_COUNT;
import static movies.popular.soliton.popularmovies.util.Constant.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new RequestMovieTask().execute(URL + POPULAR);
    }

    private class RequestMovieTask extends AsyncTask<String, Void, Void> {
        private String TAG = RequestMovieTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                InputStream in = connection.getInputStream();
                Scanner scanner = new Scanner(in).useDelimiter("//A");
                Log.i(TAG, scanner.next());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
