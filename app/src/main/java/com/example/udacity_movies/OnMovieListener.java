package com.example.udacity_movies;

import java.util.ArrayList;

public interface OnMovieListener {
    void Popularmovies(ArrayList<movie> moviesa,ArrayList<movie> movi);
    void reviews(ArrayList<movie> moviesa);
    void onCarsError(String error);
}
