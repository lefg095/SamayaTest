package com.example.samayatest.view.activity;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.samayatest.R;
import com.example.samayatest.model.data.PicturesRoom;
import com.example.samayatest.presenter.implementation.BasePresenter;
import com.example.samayatest.presenter.implementation.PicturesPresenter;
import com.example.samayatest.presenter.callback.PicturesCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends BaseActivity implements  PicturesCallback{

    private PicturesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter.getPicturesRx(this, this);
    }

    @Override
    protected BasePresenter getPresenter(){
        mPresenter = new PicturesPresenter(this, this, this);
        return mPresenter;
    }

    @Override
    public void onSucess(List<PicturesRoom> picturesRoomList) {

    }

    @Override
    public void onLoading(String message) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccessArray(JSONArray array) {

    }
}