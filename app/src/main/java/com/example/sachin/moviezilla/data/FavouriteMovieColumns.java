package com.example.sachin.moviezilla.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by sam_chordas on 8/17/15.
 */
public interface FavouriteMovieColumns {

   /* public String poster_path;
    public String overview;
    public String original_title;
    public double vote_average;
    public String release_date;
    public String backdrop_path;*/

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID =
            "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String TITLE = "title";
    @DataType(DataType.Type.TEXT) @NotNull public static final String OVERVIEW =
            "overview";
    @DataType(DataType.Type.TEXT) @NotNull public static final String POSTER_PATH =
            "poster_path";
    @DataType(DataType.Type.INTEGER) @NotNull public static final String VOTE_AVERAGE =
            "vote_average";
    @DataType(DataType.Type.TEXT) @NotNull public static final String RELEASE_DATE =
            "release_date";
    @DataType(DataType.Type.TEXT) @NotNull public static final String BACK_DROP =
            "backdrop_path";
    @DataType(DataType.Type.INTEGER) @NotNull public static final String MOVIE_ID =
            "movie_id";
}

