package harshadavolvocode.com.volvo_weatherforecast_harshada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import harshadavolvocode.com.volvo_weatherforecast_harshada.Model.Location;
import harshadavolvocode.com.volvo_weatherforecast_harshada.R;
/*
 * Created by Harshada Mahulkar
 * */
//o	Gothenburg
//o	Stockholm
//o	Mountain View
//o	London
//o	New York
//o	Berlin
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    CardView cv1, cv2, cv3, cv4, cv5,cv6;
    TextView tv_gothen, tv_stockholm, tv_maountainview, tv_london, tv_newyork, tv_berlin ;
    String entryPoint = "https://www.metaweather.com";
    String urlLocation = "/api/location/search/?query=";
    private Gson gson;
    Location location;
    private View Scroll;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cv1 = findViewById(R.id.card1);

        cv2 = findViewById(R.id.card2);
        cv3 = findViewById(R.id.card3);
        cv4 = findViewById(R.id.card4);
        cv5 = findViewById(R.id.card5);
        cv6 = findViewById(R.id.card6);
        tv_gothen = findViewById(R.id.card1_tv1);
        tv_gothen.setText(R.string.gothenburg);
        tv_stockholm = findViewById(R.id.card2_tv1);
        tv_stockholm.setText(R.string.Stockholm);
        tv_maountainview = findViewById(R.id.card3_tv1);
        tv_maountainview.setText(R.string.Mountain_View);
        tv_london = findViewById(R.id.card4_tv1);
        tv_london.setText(R.string.London);
        tv_newyork = findViewById(R.id.card5_tv1);
        tv_newyork.setText(R.string.New_York);
        tv_berlin = findViewById(R.id.card6_tv1);
        tv_berlin.setText(R.string.Berlin);
        Scroll = findViewById(R.id.sv);
        Scroll.setVisibility(View.VISIBLE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        cv1.setOnClickListener(this);
        cv2.setOnClickListener(this);
        cv3.setOnClickListener(this);
        cv4.setOnClickListener(this);
        cv5.setOnClickListener(this);
        cv6.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.card1:
                requestLocation(tv_gothen.getText().toString());
                break;
            case R.id.card2:
                requestLocation(tv_stockholm.getText().toString());
                break;
            case R.id.card3:
                requestLocation(tv_maountainview.getText().toString());
                break;
            case R.id.card4:
                requestLocation(tv_london.getText().toString());
                break;
            case R.id.card5:
                requestLocation(tv_newyork.getText().toString());
                break;
            case R.id.card6:
                requestLocation(tv_berlin.getText().toString());
                break;


        }
    }

    private void requestLocation(String s) {
        StringRequest request = new StringRequest(entryPoint+urlLocation+s,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                       List<Location> locations =  Arrays.asList(gson.fromJson(response, Location[].class));
                        for(Location location: locations )
                        {
                            String woeid = String.valueOf(location.getWoeid().intValue());
                            String title = location.getTitle().toString().trim();
                            Log.i("Locatioin", location.getWoeid().intValue()+ ":" +location.getTitle());
                            openFragmant(woeid, title);
                        }
                        }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void openFragmant(String s, String t) {
       Intent i = new Intent(this, DetailActivity.class);
       i.putExtra("woeid",s);
       i.putExtra("LocationTitle", t);
       startActivity(i);

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    }

