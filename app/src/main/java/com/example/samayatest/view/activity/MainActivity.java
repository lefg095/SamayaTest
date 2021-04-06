package com.example.samayatest.view.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samayatest.R;
import com.example.samayatest.model.data.PicturesRoom;
import com.example.samayatest.presenter.implementation.BasePresenter;
import com.example.samayatest.presenter.implementation.PicturesPresenter;
import com.example.samayatest.presenter.callback.PicturesCallback;
import com.example.samayatest.view.adapter.GalleryAdapter;
import org.json.JSONArray;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements  PicturesCallback{

    private PicturesPresenter mPresenter;
    private GalleryAdapter galleryAdapter;
    RecyclerView gridView = findViewById(R.id.gridView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter.getPicturesRx(this);
    }

    @Override
    protected BasePresenter getPresenter(){
        mPresenter = new PicturesPresenter(this, this, this);
        return mPresenter;
    }

    @Override
    public void onSucess(ArrayList<PicturesRoom> picturesRoomList) {
        initOrUpdateView(picturesRoomList);
    }

    private void initOrUpdateView(ArrayList<PicturesRoom> picturesRoomList) {
        if (galleryAdapter != null) {
            galleryAdapter.updateGalleryAdapter(picturesRoomList);
        } else {
            GridLayoutManager gridManager = new GridLayoutManager(this, 3);
            gridView.setLayoutManager(gridManager);
            //gridView.setOnClickListener(v -> onItemPhotosClicked(picturesRoomList));
            galleryAdapter = new GalleryAdapter(this, picturesRoomList);
            gridView.setAdapter(galleryAdapter);
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