package com.example.samayatest.presenter.implementation;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.samayatest.model.data.PicturesRoom;
import com.example.samayatest.model.database.AppDatabase;
import com.example.samayatest.presenter.callback.PicturesCallback;
import com.example.samayatest.view.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PicturesPresenter extends BasePresenter{

    private PicturesCallback callbackP;

    public PicturesPresenter(Context context, PicturesCallback callback, Activity activity){
        super(context, activity);
        callbackP = callback;
    }

    public void getPicturesRx(MainActivity mainActivity, Context context){
        disposable = Observable.fromCallable(() -> getPictures(context, callbackP))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result -> callbackP.onLoading("Cargando..."))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    callbackP.onSucess(AppDatabase.getInstance(context).picturesDao().getAll());
                }, throwable -> {
                    callbackP.onError("Error al cargar imagenes");
                });
    }

    private JSONArray getPictures(Context context, PicturesCallback callbackP) throws Exception {
        String url = "https://picsum.photos/v2/list?page=2&limit=100";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        /*JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, JSONArray jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                PicturesRoom picture = new PicturesRoom();
                                JSONObject jsonObject = response.getJSONObject(i);
                                picture.setId(jsonObject.getLong("id"));
                                picture.setAuthor(jsonObject.getString("author"));
                                picture.setWidth(jsonObject.getLong("width"));
                                picture.setHeight(jsonObject.getLong("height"));
                                picture.setUrl(jsonObject.getString("url"));
                                picture.setDownload_url(jsonObject.getString("download_url"));
                                AppDatabase.getInstance(context).picturesDao().insertAll(picture);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                });*/
        // Initialize a new JsonArrayRequest instance


        AtomicReference<JSONArray> result = new AtomicReference<>(new JSONArray());
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url + "getLocations", null, response -> {
                    result.set(response);
                    //Here I have correct value of result (JSONArray)
                    System.out.println(result.get());
                    callbackP.onSuccessArray(response);
                },
                        error -> {
                            Toast.makeText(context, "Cannot update users' location :" + error, Toast.LENGTH_LONG).show();
                        });
        requestQueue.add(request);
        return null;
    }
}