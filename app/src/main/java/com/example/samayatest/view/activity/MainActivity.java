package com.example.samayatest.view.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samayatest.R;
import com.example.samayatest.model.data.PicturesRoom;
import com.example.samayatest.presenter.callback.GalleryCallback;
import com.example.samayatest.presenter.implementation.BasePresenter;
import com.example.samayatest.presenter.implementation.PicturesPresenter;
import com.example.samayatest.presenter.callback.PicturesCallback;
import com.example.samayatest.view.adapter.GalleryAdapter;
import com.example.samayatest.view.utilities.SweetDialogs;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements PicturesCallback, GalleryCallback {

    private PicturesPresenter mPresenter;
    private GalleryAdapter galleryAdapter;
    RecyclerView gridView;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView= findViewById(R.id.gridView);
        mPresenter.getPicturesRx(context);
    }

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new PicturesPresenter(context, this, this);
        return mPresenter;
    }

   private void initOrUpdateView(ArrayList<PicturesRoom> picturesRoomList) {
        if (galleryAdapter != null) {
            galleryAdapter.updateGalleryAdapter(picturesRoomList);
        } else {
            GridLayoutManager gridManager = new GridLayoutManager(getApplicationContext(), 3);
            gridView.setLayoutManager(gridManager);
            //gridView.setOnClickListener(v -> onItemPhotosClicked(picturesRoomList));
            galleryAdapter = new GalleryAdapter(this, picturesRoomList);
            gridView.setAdapter(galleryAdapter);
        }
    }

    @Override
    public void onSucess(ArrayList<PicturesRoom> picturesRoomList) {
        initOrUpdateView(picturesRoomList);
    }

    @Override
    public void onLoading(String message) {
        SweetDialogs.sweetLoading(this, message);
    }

    @Override
    public void onError(String error) {
        SweetDialogs.sweetError(this, error);
    }
}