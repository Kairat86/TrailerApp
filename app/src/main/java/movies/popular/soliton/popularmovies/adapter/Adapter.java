package movies.popular.soliton.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movies.popular.soliton.popularmovies.R;
import movies.popular.soliton.popularmovies.activity.MovieDetailsActivity;
import movies.popular.soliton.popularmovies.entity.Movie;

import static movies.popular.soliton.popularmovies.util.Constant.MOVIE;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public Adapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    public Adapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.with(context).load(movie.getThumbnailURL()).into(holder.imageView);
        holder.movie = movie;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setList(List<Movie> list) {
        this.movieList = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Movie movie;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.poster);
            imageView.setOnClickListener(view -> {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(MOVIE, movie);
                context.startActivity(intent);
            });
        }
    }
}
