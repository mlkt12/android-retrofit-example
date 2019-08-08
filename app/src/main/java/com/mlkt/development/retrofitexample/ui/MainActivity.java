package com.mlkt.development.retrofitexample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mlkt.development.retrofitexample.R;
import com.mlkt.development.retrofitexample.RxManager;
import com.mlkt.development.retrofitexample.api.Client;
import com.mlkt.development.retrofitexample.api.model.Country;
import com.mlkt.development.retrofitexample.ui.adapters.CountriesAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RETROFIT_EXAMPLE";

    private CountriesAdapter countriesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        call();
    }

    private void initUI(){
        RecyclerView countriesRecycler = findViewById(R.id.recycler);
        countriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        countriesAdapter = new CountriesAdapter(MainActivity.this);
        countriesRecycler.setAdapter(countriesAdapter);
    }

    private void call(){
//        getData();
        getDataUseRx();
    }

    private void getData(){
        Client client = new Client();
        client.getCountryData().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, retrofit2.Response<List<Country>> response) {
                countriesAdapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }

        });
    }

    private void getDataUseRx(){
        RxManager.getInstance().observeCountryData().subscribe(new Subscriber<List<Country>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,e.getMessage());
            }

            @Override
            public void onNext(List<Country> countries) {
                countriesAdapter.setData(countries);
            }
        });
    }

}

