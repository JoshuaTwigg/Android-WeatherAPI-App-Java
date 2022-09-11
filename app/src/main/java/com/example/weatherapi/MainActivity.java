package com.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public Button button;
    public EditText input;
    public ImageView imageView;
    public TextView city;
    public TextView temp;
    public TextView description;
    public TextView wind;
    public TextView humidity;
    public ImageView iconImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        imageView = findViewById(R.id.imageView);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        description= findViewById(R.id.description);
        wind = findViewById(R.id.wind);
        humidity= findViewById(R.id.humidity);
        button= findViewById(R.id.button);
        iconImage = findViewById(R.id.iconImg);

        //Picasso.get()
                //.load("https://picsum.photos/200/300")
                //.into(imageView);    // this is test code for picasso works 100%
                // make image view it belongs to remove margin top 50 to see it on screen
        // i forced it off screen for test purposes



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = input.getText().toString();
                System.out.println(cityName);

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                //String url = "https://api.openweathermap.org/data/2.5/weather?q=%20cape%20town%20&units%20=%20metric%20&appid=169256dd7405ffa9d7394c68847e621d";
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units%20=%20metric%20&appid=169256dd7405ffa9d7394c68847e621d";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response.toString());

                        try {
                            System.out.println(response.getString("name"));
                            JSONObject obj = response.getJSONObject("main"); // gets object inside object api
                            //System.out.println(response.get("wind"));
                            JSONObject windObj = response.getJSONObject("wind");
                            //System.out.println(obj.getString("temp"));
                            //System.out.println(obj.getString("humidity"));

                            JSONArray arr = response.getJSONArray("weather"); // get array inside json object which contains json object it has the name of "weather:
                            System.out.println(arr);
                            JSONObject j = arr.getJSONObject(0);
                            //System.out.println(j.get("description"));
                            //System.out.println(j.get("icon"));

                            String name = response.getString("name");
                            //String windApi = windObj.getString("wind");
                            String tempe = obj.getString("temp");
                            String humid = obj.getString("humidity");
                            String desc = j.getString("description");
                            String winder = windObj.getString("speed");
                            String icon = j.getString("icon");
                            float temper = obj.getInt("temp");
                            float edit = temper - 273;
                            String num =  Float.toString(edit);


                            temp.setText(num);
                            city.setText("weather in" + " " + name);
                            description.setText(desc);
                            wind.setText("Wind:" + " " + winder);
                            humidity.setText("Humidity: "+humid);
                            //System.out.println(humid);
                            //imageView.
                            String link = "http://openweathermap.org/img/w/" + icon + ".png";
                            // wind getting wind from object gives error no value for wind
                            Picasso.get()
                                    .load(link)
                                    .into(iconImage);
                            //icon.src = "http://openweathermap.org/img/wn/" + iconSymbol + ".png"

                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("error of some sort idk");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(request);
            }
        });
            }



        }



