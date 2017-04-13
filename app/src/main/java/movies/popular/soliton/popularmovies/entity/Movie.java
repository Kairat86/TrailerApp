package movies.popular.soliton.popularmovies.entity;

import java.net.URL;
import java.util.Calendar;

public class Movie {

    private URL thumbnail;
    private String title;
    private Calendar releaseDate;
    private float voteAverage;
    private String overview;

    public URL getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(URL thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
