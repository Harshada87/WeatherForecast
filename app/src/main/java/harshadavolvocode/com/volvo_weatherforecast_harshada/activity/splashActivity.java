package harshadavolvocode.com.volvo_weatherforecast_harshada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import harshadavolvocode.com.volvo_weatherforecast_harshada.R;

/*
 * Created by Harshada Mahulkar
 * */
public class splashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
