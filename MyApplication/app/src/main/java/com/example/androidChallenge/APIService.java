package com.example.androidChallenge;


import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by DELL-PC on 15-10-2016.
 */
public interface APIService {

    @GET("http://swapi.co/api/starships/")
    Observable<StarShips> downloadObservableData (@Query("page") int path);

    @GET
    Observable<Details.Films> downloadObservableFilmData (@Url String url);



}
