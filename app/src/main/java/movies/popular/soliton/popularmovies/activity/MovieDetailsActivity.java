package movies.popular.soliton.popularmovies.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import movies.popular.soliton.popularmovies.R;
import movies.popular.soliton.popularmovies.entity.Movie;

import static movies.popular.soliton.popularmovies.util.Constant.MOVIE;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE);
        setContentView(R.layout.overview);
        ImageView imageView = (ImageView) findViewById(R.id.img);
        Picasso.with(this).load(movie.getThumbnailURL()).into(imageView);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(movie.getTitle());
        TextView releaseDate = (TextView) findViewById(R.id.release_date_val);
        releaseDate.setText(movie.getReleaseDate());
        TextView averageVote = (TextView) findViewById(R.id.average_vote_val);
        averageVote.setText(String.format(Locale.getDefault(), "%10.2f", movie.getVoteAverage()));
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        overview.setText(movie.getOverview());
    }
}
