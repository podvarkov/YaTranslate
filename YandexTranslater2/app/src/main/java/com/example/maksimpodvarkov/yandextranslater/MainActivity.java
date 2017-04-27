package com.example.maksimpodvarkov.yandextranslater;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
LanguagesContainer l;
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.editText);
        textView=(TextView)findViewById(R.id.textView);
        //textView.setText("Downloading");
        DataSource.async().getLanguages("ru").enqueue(new Callback<LanguagesContainer>() {
            @Override
            public void onResponse(Call<LanguagesContainer> call, Response<LanguagesContainer> response) {
                //textView.setText(response.body().toString());
                l=response.body();
         //       getLang();
            }

            @Override
            public void onFailure(Call<LanguagesContainer> call, Throwable t) {
                textView.setText(t.getMessage());


            }
        });
        Button b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLang();
            }
        });

    }

        void getLang(){
        DataSource.async().detectLanguages(Uri.encode(editText.getText().toString())).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("TAG",response.body().getAsJsonObject().get("lang").toString());
                String key=response.body().getAsJsonObject().get("lang").getAsString();
                textView.setText(l.getLanguage(key));

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("TAG",t.getMessage());
            }
        });
    }
}


