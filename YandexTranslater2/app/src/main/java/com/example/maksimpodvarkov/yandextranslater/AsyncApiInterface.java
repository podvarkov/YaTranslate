package com.example.maksimpodvarkov.yandextranslater;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by maksim.podvarkov on 27.04.2017.
 */
public interface AsyncApiInterface {
    @GET("getLangs?key=" + DataSource.API_KEY)
    Call<LanguagesContainer> getLanguages(@Query("ui") String ui);
    @GET("detect?key="+DataSource.API_KEY)
    Call<JsonElement> detectLanguages(@Query("text") String text);
}


