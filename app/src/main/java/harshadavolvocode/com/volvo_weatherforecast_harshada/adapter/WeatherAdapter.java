package harshadavolvocode.com.volvo_weatherforecast_harshada.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import harshadavolvocode.com.volvo_weatherforecast_harshada.Model.ConsolidatedWeather;
import harshadavolvocode.com.volvo_weatherforecast_harshada.Model.Weather;
import harshadavolvocode.com.volvo_weatherforecast_harshada.R;
import harshadavolvocode.com.volvo_weatherforecast_harshada.activity.DetailActivity;
/*
 * Created by Harshada Mahulkar
 * */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
private Context context;
private List<Weather> weatherList = new ArrayList<>();
private List<ConsolidatedWeather> consolidatedWeatherList = new ArrayList<>();
private LayoutInflater inflater = null;

    private LruCache<Integer,Bitmap> imageCache;
    private RequestQueue queue;
    ImageLoader imageLoader;

    public WeatherAdapter(Context context, List<Weather> weatherList) {
        this.context  = context;
        this.weatherList = weatherList;
        inflater = LayoutInflater.from(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);

        queue = Volley.newRequestQueue(context);

    }

    public WeatherAdapter(DetailActivity context, List<ConsolidatedWeather> consolidatedWeatherList) {
        this.context  = context;
        this.consolidatedWeatherList = consolidatedWeatherList;
        inflater = LayoutInflater.from(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);

        queue = Volley.newRequestQueue(context);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ImageTitleTextView, textViewdate,textViewtemp,textViewwinddirection;
        public  NetworkImageView VolleyImageView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ImageTitleTextView = (TextView)itemView.findViewById(R.id.ImageNameTextView);
            VolleyImageView = (NetworkImageView)itemView.findViewById(R.id.VolleyImageView);
            textViewdate = itemView.findViewById(R.id.textViewdate);
            textViewtemp = itemView.findViewById(R.id.textViewtemp);
            textViewwinddirection = itemView.findViewById(R.id.textViewwinddirection);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
    @Override
    public int getItemCount() {
        return consolidatedWeatherList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( WeatherAdapter.ViewHolder holder, int position) {
        Weather weatherpos = consolidatedWeatherList.get(position);
        DecimalFormat formater = new DecimalFormat("#.##");
        holder.ImageTitleTextView.setText( consolidatedWeatherList.get(position).getWeatherStateName());
        holder.textViewdate.setText("Date: "+consolidatedWeatherList.get(position).getApplicableDate());
        holder.textViewtemp.setText("Temparature : "+formater.format(consolidatedWeatherList.get(position).getTheTemp())+"\u2103");
        holder.textViewwinddirection.setText("Wind Dierction :" +formater.format(consolidatedWeatherList.get(position).getWindDirection()));
        String url = "https://www.metaweather.com/static/img/weather/png/64/";
        String url2 = url+consolidatedWeatherList.get(position).getWeatherStateAbbr()+".png";
       // holder.VolleyImageView.setImageUrl(url2,imageLoader);
        Log.i("Image url", url2);
        //Picasso.get().load(url2).into(holder.VolleyImageView);
        Picasso.get()
                .load(url2)
                .placeholder(R.mipmap.ic_launcher_round)
                .resize(200, 200)
                .centerInside()
                .into(holder.imageView);

    }




}
