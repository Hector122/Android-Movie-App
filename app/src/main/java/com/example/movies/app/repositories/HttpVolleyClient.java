package com.example.movies.app.repositories;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movie.app.R;
import com.example.movies.app.models.Movie;
import com.example.movies.app.models.MoviesHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Singleton class for make the http request with the volley library.
 */
public class HttpVolleyClient {
  private static final String API_KEY = "c9040ca82260ace0ea4a99bbcb665308";
  private static final String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";

  private static HttpVolleyClient instance;
  private Context context;
  private RequestQueue requestQueue;

  /**
   * Constructors
   *
   * @param context application context.
   */
  private HttpVolleyClient(Context context) {
    this.context = context;
    requestQueue = getRequestQueue();
  }

  /**
   * Get or initialize the singleton class.
   *
   * @param context application context.
   * @return HttpVolleyClient.
   */
  public static synchronized HttpVolleyClient getInstance(Context context) {
    if (instance == null) {
      instance = new HttpVolleyClient(context);
    }
    return instance;
  }

  /**
   * Get request queue is this create isn;t create a new one.
   *
   * @return RequestQueue
   */
  private RequestQueue getRequestQueue() {
    if (requestQueue == null) {
      // getApplicationContext() is key, it keeps you from leaking the
      // Activity or BroadcastReceiver if someone passes one in.
      requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    return requestQueue;
  }

  /**
   * Make the http request to GET the JSON movies data  from the server.
   */
  public MutableLiveData<List<Movie>> getNowPlayingMoviesFromServer() {
    String url = URL + API_KEY;
    final MutableLiveData<List<Movie>> listMutableLiveData = new MediatorLiveData<>();

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                Log.i("Response: ", response);

                Gson gson = new GsonBuilder().create();
                MoviesHolder movies = gson.fromJson(response, MoviesHolder.class);
                listMutableLiveData.postValue(movies.getMovies());
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(error.getMessage())
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                          }
                        });

                builder.create().show();
              }
            });

    //Add the request to the queue.
    getRequestQueue().add(stringRequest);

    return listMutableLiveData;
  }
}
