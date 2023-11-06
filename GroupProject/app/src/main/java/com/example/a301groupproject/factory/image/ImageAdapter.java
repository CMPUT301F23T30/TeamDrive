package com.example.a301groupproject.factory.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.a301groupproject.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Image> images;

    public ImageAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.individual_image, parent, false);
        }

        ImageView individualImage = convertView.findViewById(R.id.individual_image);
        ImageView deleteIcon = convertView.findViewById(R.id.delete_image_button);

        Image image = images.get(position);
        individualImage.setImageURI(image.getImageUri());

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                images.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
