package com.example.revoluttestapp.model.client;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.app.R;

/**
 * Singleton Class for make the http request
 */
public class HttpVolleyClient {
    private static HttpVolleyClient sInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    /**
     * Callback methods
     */
    public interface OnReposeReceiveData {
        void onReceiveData(String response);

        void onErrorResponse(VolleyError error);
    }

    /**
     * Constructors
     *
     * @param context application context.
     */
    private HttpVolleyClient(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * Get or initialize the singleton class.
     *
     * @param context application context.
     * @return HttpVolleyClient.
     */
    public static synchronized HttpVolleyClient getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HttpVolleyClient(context);
        }
        return sInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Make the http request to GET the JSON data from the server.
     *
     * @param onReposeReceiveData callback interface with action to make.
     */
    public void getRatesValuesFromServer(final OnReposeReceiveData onReposeReceiveData) {
        //RequestQueue queue = Volley.newRequestQueue(context);
        String url = mContext.getString(R.string.url_testing);//TODO: testing api.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onReposeReceiveData.onReceiveData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onReposeReceiveData.onErrorResponse(error);
                    }
                });

        //Add the request to the queue.
        getRequestQueue().add(stringRequest);
    }
}
