package com.example.maksimpodvarkov.yandextranslater;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maksim.podvarkov on 27.04.2017.
 */
public class DataSource {

    static final String API_KEY = "trnsl.1.1.20170421T154615Z.9ec55b32cddbed9a.36f69d8c47131cb96ed8526a0643b229cb604fa7";

    private static DataSource instance;

    private final AsyncApiInterface asyncApi;

    static {
        instance = new DataSource();
    }

    public static AsyncApiInterface async() {
        return instance.asyncApi;
    }

    private DataSource() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(4, TimeUnit.SECONDS)
                .build();


        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LanguagesContainer.class, new LanguagesContainer.UserDeserializer())
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        asyncApi = retrofit.create(AsyncApiInterface.class);
    }

}
