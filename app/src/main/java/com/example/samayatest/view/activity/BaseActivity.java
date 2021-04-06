package com.example.samayatest.view.activity;

import android.os.Bundle;

import com.example.samayatest.presenter.implementation.BasePresenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    BasePresenter mPresenter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
    }


    protected abstract BasePresenter getPresenter();

    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDispose();
        }
    }

}
