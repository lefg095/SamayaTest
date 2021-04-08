package com.example.samayatest.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samayatest.R;
import com.example.samayatest.model.data.PicturesRoom;
import com.example.samayatest.presenter.callback.GalleryCallback;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private ArrayList<PicturesRoom> picturesRoomArrayList;
    private GalleryCallback mCallback;
    public GalleryAdapter(GalleryCallback callback, ArrayList<PicturesRoom> picturesRoomArrayList) {
        mCallback = callback;
        this.picturesRoomArrayList = picturesRoomArrayList;
    }
    @NonNull
    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        PicturesRoom pictures = picturesRoomArrayList.get(position);
        holder.textView.setText(pictures.getAuthor());
    }

    @Override
    public int getItemCount()  {
        return picturesRoomArrayList.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        TextView textView = (TextView ) itemView.findViewById(R.id.titlePicture);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewPhoto);

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);

        }

    }

    public void updateGalleryAdapter(ArrayList<PicturesRoom> picturesRoomArrayList) {
        this.picturesRoomArrayList = picturesRoomArrayList;
        notifyDataSetChanged();
    }
}
