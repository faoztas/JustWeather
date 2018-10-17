package com.example.furka.justweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.furka.justweather.Models.CurrentWeather.CurrentWeather;
import com.example.furka.justweather.Models.UVindex;
import com.example.furka.justweather.Models.WeatherForecast.WeatherForecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textCity,textRF,textFeel,textDeg,textDesc,textClo,textHum,textWind;
    EditText editCitySearch;
    ImageView imageIcon,imageWind;
    Button button;

    String city,icon;
    int deg=0;

    ApiInterface apiInterface;

    Call<CurrentWeather> call;
    Call<UVindex> UVcall;
    Call<WeatherForecast> Fcall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCity = (TextView)findViewById(R.id.textCity);
        textDeg = (TextView)findViewById(R.id.textDeg);
        textRF = (TextView)findViewById(R.id.textRF);
        textFeel = (TextView)findViewById(R.id.textFeel);
        editCitySearch = (EditText)findViewById(R.id.editCitySearch);
        imageIcon = (ImageView)findViewById(R.id.imageIcon);
        button = (Button)findViewById(R.id.getDataButton);
        textDesc = findViewById(R.id.textDesc);
        textClo = findViewById(R.id.textClo);
        textHum = findViewById(R.id.textHum);
        textWind = findViewById(R.id.textWind);
        imageWind = findViewById(R.id.imageWind);

        apiInterface = ApiClient.getMyRetrofit(ApiClient.mainUrl).create(ApiInterface.class);
    }

    public static double myUVIndex = 0.0;

    public double getUV(double lat, double lon){
        UVcall=apiInterface.getUVIndex(lon,lat,ApiClient.apiKey);

        UVcall.enqueue(new Callback<UVindex>() {
            @Override
            public void onResponse(Call<UVindex> call, Response<UVindex> response) {
                List<UVindex> UVList = new ArrayList<>();
                UVList.add((UVindex) response.body());

                try {
                    for (int i = 0; i < UVList.size(); i++) {
                        double d = UVList.get(i).getValue();
                        myUVIndex = d;
                        Log.i("UV" , String.valueOf(d));
                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Hata: "+e,Toast.LENGTH_LONG);

                }

            }

            @Override
            public void onFailure(Call<UVindex> call, Throwable t) {
                Log.i("Hata : ", String.valueOf(t));
            }
        });
        return myUVIndex;
    }

    public double getWindmph(double S){
        double wind = S;
        final double mph = 2.23693629;

        return wind*mph;
    }

    public double getDewPoint(double temp,double hum) {
        final double a = 17.27;
        final double b = 237.7;
        double T=temp;
        double H=hum;

        double f = ((a * T) / (b + T)) + Math.log(H);
        return (b * f) / (a - f);
    }

    public double fTOc(Double T){
        Double c=T;
        if (c==32) {
            return 0.0;
        }
        else {
            return ((c - 32) * 5) / 9;
        }
    }
    public double cTOf(Double T){
        Double c=T;
        return (c * 9/5 + 32);
    }

    public Double setRealFeel(Double windSpeed, Integer pressure, Double temp, Double uvIndex, Double hum){
        Double realFeel;
        Double T=cTOf(temp);
        Double W=getWindmph(windSpeed);
        Double UV=uvIndex;
        Double H=hum;
        Double Wa,P2=0.0,WSP2,WSP1,SI2,Da,H2;
        Double D=getDewPoint(T,H);
        Integer A=pressure;

        if (W<4) {
            Wa = W / 2 + 2;
        }
        else if (W<56) {
            Wa = W;
        }
	    else {
            Wa = 56.0;
        }

        WSP2=(80-T)*(0.566+0.25*Math.sqrt(Wa)-0.0166*Wa)*((Math.sqrt(A/10))/10);
        WSP1=Math.sqrt(W)*((Math.sqrt(A/10))/10);

        SI2 = UV;

        if (D >= (55+Math.sqrt(W))) {
            Da = D;
        }
	    else {
            Da = 55 + Math.sqrt(W);
        }

        H2=Math.pow((Da-55-Math.sqrt(W)),2/30);


        if (T>= 65) {
            realFeel = 80 - WSP2 + SI2 + H2 - P2;
        }
	    else{
            realFeel=T-WSP1+SI2+H2-P2;
        }
        return fTOc(realFeel);
    }

    public void setWeatherNow(){
        city = String.valueOf(editCitySearch.getText());
        textCity.setText(city);
        call=apiInterface.getWeather(String.valueOf(city),"metric","tr",ApiClient.apiKey);

        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                List<CurrentWeather> nowWeatherList = new ArrayList<>();
                nowWeatherList.add((CurrentWeather) response.body());

                try {
                    for (int i = 0; i < nowWeatherList.size(); i++) {
                        double temp = nowWeatherList.get(i).getMain().getTemp();
                        int pressure = nowWeatherList.get(i).getMain().getPressure();
                        double wind = nowWeatherList.get(i).getWind().getSpeed();
                        int humidity = nowWeatherList.get(i).getMain().getHumidity();
                        double lat = nowWeatherList.get(i).getCoord().getLat();
                        double lon = nowWeatherList.get(i).getCoord().getLon();
                        double realFeel = setRealFeel(wind,pressure,temp,getUV(lat,lon),(double)humidity);
                        Log.i("RealFeel: ", String.valueOf(realFeel));



                        textDeg.setText((int)Math.ceil(temp) + "°C");
                        textDesc.setText("Durum: "+nowWeatherList.get(i).getWeather().get(i).getDescription());
                        textClo.setText("%" + nowWeatherList.get(i).getClouds().getAll());
                        textHum.setText("Nem: %" + nowWeatherList.get(i).getMain().getHumidity());
                        textWind.setText("Rüzgar: " + String.valueOf(nowWeatherList.get(i).getWind().getSpeed()) + " m/sn");
                        textFeel.setText((int)Math.ceil(realFeel) + "°C");

                        icon = (nowWeatherList.get(i).getWeather().get(i).getIcon());
                        Picasso.get().load(ApiClient.iconUrl + icon + ".png").resize(100, 100).into(imageIcon);

                        if (nowWeatherList.get(i).getWind().getDeg()==null){
                            imageWind.setImageResource(R.drawable.images);
                        }
                        else{
                            deg = nowWeatherList.get(i).getWind().getDeg();
                            imageWind.setImageResource(R.drawable.imgwind);
                            imageWind.setRotation(deg);
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Hata: "+e,Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                Log.i("Hata",t.toString());

            }
        });
    }

    public void setForecast(){


    }

    public void getData(View view) {
        setWeatherNow();
    }
}
