package com.example.furka.justweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textCity,textRF,textFeel;
    EditText editCitySearch;
    ImageView imageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCity = (TextView)findViewById(R.id.textCity);
        textRF = (TextView)findViewById(R.id.textRF);
        textFeel = (TextView)findViewById(R.id.textFeel);
        editCitySearch = (EditText)findViewById(R.id.editCitySearch);
        imageIcon = (ImageView)findViewById(R.id.imageIcon);
    }
    //give param models
    public getData(){
    //param=ApiClient().provideApi("http....")
    //data=param.(Get method call)
    //data.enqueue(new Callback<>(){
    ...
        @Override
        onResponse()
        @Override
        onFailure()
    ...
        ..gerisini aynı mantıkla doldurup yaparsın
    ...})    
    }
}
