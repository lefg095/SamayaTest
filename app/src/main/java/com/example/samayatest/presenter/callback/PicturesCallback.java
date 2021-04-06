package com.example.samayatest.presenter.callback;

import com.example.samayatest.model.data.PicturesRoom;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public interface PicturesCallback {
        void onSucess(List<PicturesRoom> picturesRoomList);
        void onLoading(String message);
        void onError(String error);
        void onSuccessArray(JSONArray array);
}
