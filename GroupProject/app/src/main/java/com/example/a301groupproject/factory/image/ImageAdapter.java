package com.example.a301groupproject.factory.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import com.example.a301groupproject.R;
import com.example.a301groupproject.ui.home.HomeViewModel;

import java.util.ArrayList;

/**
 * ImageAdapter for showing images in GridView component.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Image> images;
    private OnDeleteIconClickListener onDeleteIconClickListener;

    public void setOnDeleteIconClickListener(OnDeleteIconClickListener listener) {
        this.onDeleteIconClickListener = listener;
    }

    public interface OnDeleteIconClickListener {
        void onDeleteClick(int position);
    }

    /**
     * Constructor for the ImageAdapter with a context and images list.
     *
     * @param context Current Context
     * @param images  Image List
     */
    public ImageAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
    }

    /**
     * Override method: get # of images
     *
     * @return # of images.
     */
    @Override
    public int getCount() {
        return images.size();
    }

    /**
     * Get the image in the specific position
     *
     * @param position image index
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    /**
     * Get the index
     *
     * @param position image index
     * @return image index
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * View for displaying the image and delete button at the right corner
     * Set delete button onclick listener to delete corresponding image
     *
     * @param position    image index
     * @param convertView
     * @param parent
     * @return Show the new View composed of image and delete button at the top right corner
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.individual_image, parent, false);
        }

        ImageView individualImage = convertView.findViewById(R.id.individual_image);
        ImageView deleteIcon = convertView.findViewById(R.id.delete_image_button);

        Image image = images.get(position);
        Glide.with(context)
                .load(image.getImageUrl())
                .into(individualImage);

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteIconClickListener != null) {
                    onDeleteIconClickListener.onDeleteClick(position);
                }
            }
        });

        return convertView;
    }
}
