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
import com.example.samayatest.support.VolleyRequest;
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

public class PicturesPresenter extends BasePresenter {

    private PicturesCallback callbackP;
    private ArrayList<PicturesRoom> picturesRoomArrayList;

    public PicturesPresenter(Context context, PicturesCallback callback, Activity activity) {
        super(context, activity);
        callbackP = callback;
    }

    public void getPicturesRx(Context context) {
        disposable = Observable.fromCallable(() -> getPictures(context))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result -> callbackP.onLoading("Cargando..."))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->  {
                    if(result.equals("")){
                        callbackP.onSucess(result);
                    }{
                        callbackP.onError("Error al cargar imagenes");
                    }
                }, throwable -> {
                    callbackP.onError("Error al cargar imagenes");
                });
    }

    private ArrayList<PicturesRoom> getPictures(Context context) throws Exception {
        String url = "https://picsum.photos/v2/list?page=2&limit=20";
        RequestFuture<JSONArray> request = VolleyRequest.getInstance().makeRequest(context, url, Request.Method.GET, new JSONArray());
        JSONArray response = request.get();
        try {
            int longitud = response.length();
            for (int i = 0; i < longitud; i++) {
                PicturesRoom picture = new PicturesRoom();
                JSONObject jsonObject = response.getJSONObject(i);
                picture.setId(jsonObject.getLong("id"));
                picture.setAuthor(jsonObject.getString("author"));
                picture.setWidth(jsonObject.getLong("width"));
                picture.setHeight(jsonObject.getLong("height"));
                picture.setUrl(jsonObject.getString("url"));
                picture.setDownload_url(jsonObject.getString("download_url"));
                //AppDatabase.getInstance(context).picturesDao().insertAll(picture);
                picturesRoomArrayList.add(picture);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return picturesRoomArrayList;
    }
}