package movies.popular.soliton.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import static movies.popular.soliton.popularmovies.util.Constant.IMG_URL;

public class Movie implements Parcelable {

    private String poster_path;
    private String title;
    private String release_date;
    private double vote_average;
    private String overview;

    static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public Movie(String title, String posterPath, String releaseDate, double voteAverage, String overview) {
        this.title = title;
        this.poster_path = posterPath;
        this.release_date = releaseDate;
        this.vote_average = voteAverage;
        this.overview = overview;
    }

    public Movie(Parcel in) {


    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
    }

    public String getThumbnailURL() {
        return IMG_URL + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "poster_path='" + poster_path + '\'' +
                ", title='" + title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average=" + vote_average +
                ", overview='" + overview + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
