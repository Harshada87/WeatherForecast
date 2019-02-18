package harshadavolvocode.com.volvo_weatherforecast_harshada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import harshadavolvocode.com.volvo_weatherforecast_harshada.Model.ConsolidatedWeather;
import harshadavolvocode.com.volvo_weatherforecast_harshada.Model.Weather;
import harshadavolvocode.com.volvo_weatherforecast_harshada.R;
import harshadavolvocode.com.volvo_weatherforecast_harshada.adapter.WeatherAdapter;

/*
 * Created by Harshada Mahulkar
 * */
public class DetailActivity extends AppCompatActivity {
    List<ConsolidatedWeather> consolidatedWeatherList = new ArrayList<>();
    RecyclerView recyclerView;
    private String entryPoint = "https://www.metaweather.com";

    private String urlDetail = "/api/location/";
    Intent intent;
    private String woeid;
    private Gson gson;
    private WeatherAdapter adapter;
    ArrayList<String> list = new ArrayList<>();
    List<Weather> weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    public void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        recyclerView = findViewById(R.id.rv);
        intent = getIntent();
        if (intent != null) {
            woeid = (getIntent().getStringExtra("woeid"));
            String title = getIntent().getStringExtra("LocationTitle");
            Log.i("intent", woeid + "==" + title);

        } else Toast.makeText(DetailActivity.this, "Intent is null", Toast.LENGTH_SHORT).show();
        requestData(urlDetail + woeid + "/");
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

    private void requestData(String s) {
        StringRequest request = new StringRequest(entryPoint + s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("DEtailResponse", response);
                        gson = new Gson();
                        Weather weathers = gson.fromJson(response, Weather.class);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("consolidated_weather");
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                // ConsolidatedWeather consolidatedWeather = new ConsolidatedWeather();

                                JSONObject obj = jsonArray.getJSONObject(i);
                                String abb = obj.getString("weather_state_abbr");
                                String name = obj.getString("weather_state_name");
                                String wind_direction_compass = obj.getString("wind_direction_compass");
                                String applicable_date = obj.getString("applicable_date");
                                Double min_temp = obj.getDouble("min_temp");
                                Double max_temp = obj.getDouble("max_temp");
                                Double the_temp = obj.getDouble("the_temp");
                                Double wind_speed = obj.getDouble("wind_speed");
                                Double wind_direction = obj.getDouble("wind_direction");
                                Double air_pressure = obj.getDouble("air_pressure");
                                Double humidity = obj.getDouble("humidity");
                                Double visibility = obj.getDouble("visibility");
                                Double predictability = obj.getDouble("predictability");


                                ConsolidatedWeather consolidatedWeather = new ConsolidatedWeather();
                                consolidatedWeather.setWeatherStateAbbr(obj.getString("weather_state_abbr"));
                                consolidatedWeather.setWeatherStateName(name);
                                consolidatedWeather.setWindDirectionCompass(wind_direction_compass);
                                consolidatedWeather.setApplicableDate(applicable_date);
                                consolidatedWeather.setMinTemp(min_temp);
                                consolidatedWeather.setMaxTemp(max_temp);
                                consolidatedWeather.setTheTemp(the_temp);
                                consolidatedWeather.setWindSpeed(wind_speed);
                                consolidatedWeather.setWindDirection(wind_direction);
                                consolidatedWeather.setAirPressure(air_pressure);
                                consolidatedWeather.setHumidity(humidity);
                                consolidatedWeather.setVisibility(visibility);
                                consolidatedWeather.setPredictability(Double.valueOf(predictability));


                                consolidatedWeatherList.add(consolidatedWeather);

                                adapter = new WeatherAdapter(DetailActivity.this, consolidatedWeatherList);
                                recyclerView.setAdapter(adapter);
                                weatherList = new ArrayList<>();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(DetailActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

}
