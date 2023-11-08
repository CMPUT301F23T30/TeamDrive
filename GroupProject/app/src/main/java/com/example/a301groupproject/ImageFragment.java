package com.example.a301groupproject;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a301groupproject.factory.image.Image;
import com.example.a301groupproject.factory.image.ImageAdapter;
import com.example.a301groupproject.ui.home.HomeViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Fragment for showing images. Users can add images by taking photos or
 * selecting from gallery. Confirm their selection by clicking the
 * confirm button
 */
public class ImageFragment extends Fragment {
    private ArrayList<Image> images = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_IMAGE_REQUEST = 2;

    /**
     * Default no-arg constructor for ImageFragment
     */
    public ImageFragment() {

    }

    /**
     * Inflates the layout for this fragment, initializes the GridView with image adapter,
     * set 3 button onclick listener: camera, album, and confirm to listen taking picture action,
     * Album selection action and confirm action.
     * Navigate to the previous fragment (add) if click confirm
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Return the view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        GridView gridView = view.findViewById(R.id.images);

        imageAdapter = new ImageAdapter(getContext(), images);
        gridView.setAdapter(imageAdapter);

        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        ArrayList<Uri> uriList = homeViewModel.getImages().getValue();

        if (uriList != null) {
            images.clear();
            for (Uri uri : uriList) {
                images.add(new Image(uri));
            }
        }

        imageAdapter.notifyDataSetChanged();

        Button cameraButton = view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri photoURI = createFileUri();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, TAKE_IMAGE_REQUEST);

            }
        });

        Button albumButton = view.findViewById(R.id.albumButton);
        albumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        Button confirmButton = view.findViewById(R.id.saveButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
                for (Image image : images) {
                    homeViewModel.addImage(image.getImageUri());
                }

                // go back to home page after add confirm
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigateUp();
            }
        });
        return view;
    }


    /**
     * Handles the results getting from the camera and album selection.
     * Update the image date and notify image adapter
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null) {
                Uri imageUri = data.getData();
                images.add(new Image(imageUri));
                imageAdapter.notifyDataSetChanged();
            } else if (requestCode == TAKE_IMAGE_REQUEST) {
                images.add(new Image(imageUri));
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Create a file URI for storing images from the camera
     * The URI is used to store the selected images
     *
     * @return A new Uri object
     */
    private Uri createFileUri() {
        Uri newImageUri = null;

        try {
            String timeId = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "JPEG_" + timeId + "_";
            File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );

            newImageUri = FileProvider.getUriForFile(getContext(),
                    getContext().getApplicationContext().getPackageName() + ".provider",
                    image);

            imageUri = newImageUri;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newImageUri;
    }
}

