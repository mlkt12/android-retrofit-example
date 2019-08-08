package com.mlkt.development.retrofitexample.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mlkt.development.retrofitexample.RxManager;
import com.mlkt.development.retrofitexample.api.model.Country;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Client implements Service {

    private okhttp3.OkHttpClient client;
    private Service service;

    public Client(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new okhttp3.OkHttpClient()
                .newBuilder()
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final okhttp3.Request request = chain.request();
                        try {
                            return chain.proceed(request.newBuilder().build());
                        } catch (Exception e) {
                            throw new IOException(e);
                        }
                    }
                })
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(RxManager.getInstance().endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(Service.class);
    }

    @Override
    public Call<List<Country>> getCountryData() {
        return service.getCountryData();
    }

}
