package movies.popular.soliton.popularmovies.activity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import movies.popular.soliton.popularmovies.R;

import static movies.popular.soliton.popularmovies.util.Constant.API_KEY;
import static movies.popular.soliton.popularmovies.util.Constant.POPULAR;
import static movies.popular.soliton.popularmovies.util.Constant.RESULTS;
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
        new RequestMovieTask().execute(Uri.parse(URL)
                .buildUpon()
                .appendPath(POPULAR)
                .appendQueryParameter(API_KEY, getString(R.string.api_key))
                .build());
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
                Log.i(TAG, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
