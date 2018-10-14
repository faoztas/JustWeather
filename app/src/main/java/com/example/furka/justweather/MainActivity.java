package com.example.furka.justweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.furka.justweather.Models.CurrentWeather.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textCity,textRF,textFeel;
    EditText editCitySearch;
    ImageView imageIcon;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCity = (TextView)findViewById(R.id.textCity);
        textRF = (TextView)findViewById(R.id.textRF);
        textFeel = (TextView)findViewById(R.id.textFeel);
        editCitySearch = (EditText)findViewById(R.id.editCitySearch);
        imageIcon = (ImageView)findViewById(R.id.imageIcon);

        apiInterface = ApiClient.getMyRetrofit(ApiClient.mainUrl).create(ApiInterface.class);

        Call<List<CurrentWeather>> call;
        //call=apiInterface.getWeather("trabzon","metric","tr",ApiClient.apiKey);
        call=apiInterface.getWeather();

        call.enqueue(new Callback<List<CurrentWeather>>() {
            @Override
            public void onResponse(Call<List<CurrentWeather>> call, Response<List<CurrentWeather>> response) {
                List<CurrentWeather> nowWeatherList = new ArrayList<>();
                nowWeatherList=response.body();

                for (int i=0;i<nowWeatherList.size();i++){
                    //textCity.setText(nowWeatherList.get(i).getMain().getTemp().toString());
                    Log.i("Hava Durumu", String.valueOf(nowWeatherList.get(i).getMain().getTemp()));
                }
            }

            @Override
            public void onFailure(Call<List<CurrentWeather>> call, Throwable t) {
                Log.i("Hata",t.toString());

            }
        });

    }
}
