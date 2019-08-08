package com.mlkt.development.retrofitexample.api;

import com.mlkt.development.retrofitexample.api.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("keeguon/2310008/raw/bdc2ce1c1e3f28f9cab5b4393c7549f38361be4e/countries.json")
    Call<List<Country>> getCountryData();

}
