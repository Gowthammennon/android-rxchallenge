package com.example.androidChallenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private List<Details> detailList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;
    private String TAG = "MainActivity";
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        textView = (TextView) findViewById(R.id.empty_text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        DetailsAdapter detailsAdapter = new DetailsAdapter(detailList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(detailsAdapter);
        downloadData(page);
    }

    private void downloadData(int URL) {
        APIService apiService = ServiceGenerator.createRxService(APIService.class);
        apiService.downloadObservableData(URL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<StarShips>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(StarShips result) {
                        StarShips starShip = result;
                        detailList.addAll(starShip.results);

                        for (Details details : detailList) {
                            if (details.cost_in_credits.equals("unknown")) {
                                details.cost_in_credits = "0";
                            }
                        }

                        Collections.sort(detailList);
                        if (starShip.next != null) {
                            page++;
                            downloadData(page);
                        } else {
                            for (int i = detailList.size(); i > 15; i--) {
                                detailList.remove(i - 1);
                            }
                            recyclerView.getAdapter().notifyDataSetChanged();
                            downloadFilmList(detailList);
                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(detailList.isEmpty() ? View.VISIBLE : View.GONE);
                        }
                    }
                });
    }

    private void downloadFilmList(List<Details> list) {
        APIService apiService = ServiceGenerator.createRxService(APIService.class);
        for (final Details details : list) {
            apiService.downloadObservableFilmData(details.films.get(0))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Observer<Details.Films>() {
                        @Override
                        public void onCompleted() {
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onNext(Details.Films films) {
                            details.filmText = films.title;
                        }
                    });
        }
    }

}
