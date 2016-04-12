package com.example.sachin.moviezilla.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by sam_chordas on 8/11/15.
 */

@ContentProvider(authority = PlanetProvider.AUTHORITY, database = PlanetDatabase.class)
public final class PlanetProvider {
    public static final String AUTHORITY =
            "com.example.sachin.moviezilla.data.PlanetProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String PLANETS = "planets";
        String FAVOURITE_MOVIES = "favourite_movies";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }
    @TableEndpoint(table = PlanetDatabase.PLANETS) public static class Planets{
        @ContentUri(
                path = Path.PLANETS,
                type = "vnd.android.cursor.dir/planet",
                defaultSort = PlanetColumns.DIST_FROM_SUN + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.PLANETS);

        @InexactContentUri(
                name = "PLANET_ID",
                path = Path.PLANETS + "/#",
                type = "vnd.android.cursor.item/planet",
                whereColumn = PlanetColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.PLANETS, String.valueOf(id));
        }
    }

    @TableEndpoint(table = PlanetDatabase.FAVOURITE_MOVIES) public static class FavouriteMovies{
        @ContentUri(
                path = Path.FAVOURITE_MOVIES,
                type = "vnd.android.cursor.dir/favourite_movies",
                defaultSort = FavouriteMovieColumns.VOTE_AVERAGE + " ASC"
        )
        public static final Uri CONTENT_URI = buildUri(Path.FAVOURITE_MOVIES);

        @InexactContentUri(
                name = "FAVOURITE_MOVIES_ID",
                path = Path.FAVOURITE_MOVIES + "/#",
                type = "vnd.android.cursor.item/favourite_movies",
                whereColumn = FavouriteMovieColumns._ID,
                pathSegment = 1
        )
        public static Uri withId(long id){
            return buildUri(Path.FAVOURITE_MOVIES, String.valueOf(id));
        }
    }
}
