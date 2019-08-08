package com.mlkt.development.retrofitexample;

import android.util.Log;

import com.mlkt.development.retrofitexample.api.Client;
import com.mlkt.development.retrofitexample.api.model.Country;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxManager {

    private static RxManager rxManager;

    private static final String LOG_TAG = RxManager.class.getName();

    public final String endpoint = "https://gist.githubusercontent.com/";
    private static Client client;

    public static RxManager getInstance() {
        if (rxManager == null) {
            rxManager = new RxManager();
            client = new Client();
        }
        return rxManager;
    }

    public static void resetInstance() {
        rxManager = null;
    }

    public Observable<List<Country>> observeCountryData() {
        Log.d(LOG_TAG, "-> observeCountryData");
        return Observable
                .create(new Observable.OnSubscribe<List<Country>>() {
                    @Override
                    public void call(Subscriber<? super List<Country>> subscriber) {

                        try {
                            List<Country> response = client.getCountryData().execute().body();

                            if (response != null) {
                                subscriber.onNext(response);
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(new Exception("Load data failed"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
