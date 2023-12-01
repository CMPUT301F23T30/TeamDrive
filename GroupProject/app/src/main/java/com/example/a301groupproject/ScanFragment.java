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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a301groupproject.databinding.FragmentScanBinding;
import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScanFragment extends Fragment {

    private FragmentScanBinding binding;

    private HomeViewModel homeViewModel;

    private Uri imageUri;

    private FirebaseFirestore db;

    private FirebaseUser user;

    private static final int TAKE_IMAGE_REQUEST = 2;

    /**
     * Default constructor for the ScanFragment.
     */
    public ScanFragment() {

    }

    /**
     * Inflates the layout for the scan fragment
     * set 2 button onclick listener: camera and back to home page button
     * Implement taking pictures function to scan the serial number in the barcode to
     * get the detailed information of an item
     * Navigate to the previous fragment (add) if click back
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Return the view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.scanBarCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imageFile = null;
                imageUri = null;
                try {
                    String timeId = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    String imageFileName = "JPEG_" + timeId + "_";
                    File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    imageFile = File.createTempFile(
                            imageFileName,
                            ".jpg",
                            storageDir
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (imageFile != null) {
                    imageUri = FileProvider.getUriForFile(getContext(),
                            getContext().getApplicationContext().getPackageName() + ".provider",
                            imageFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, TAKE_IMAGE_REQUEST);
                }
            }
        });

        binding.backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to home page after add confirm
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigateUp();
            }
        });

        return view;
    }

    /**
     * Handles the results getting from the camera and get serial number
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TAKE_IMAGE_REQUEST) {
            try {
                if (imageUri != null) {
                    InputImage inputImage = InputImage.fromFilePath(getContext(), imageUri);
                    BarcodeScanner client = BarcodeScanning.getClient();
                    client.process(inputImage)
                            .addOnSuccessListener(barcodes -> {
                                String serialNumber = null;
                                if (!barcodes.isEmpty()) {
                                    serialNumber = barcodes.get(0).getRawValue();
                                } else {
                                    Toast.makeText(getContext(), "Please take a picture containing a barcode", Toast.LENGTH_SHORT).show();
                                }

                                if (serialNumber != null) {
                                    compareBarcodeToDB(serialNumber);
                                } else {
                                    Toast.makeText(getContext(), "Please take the picture again, the serial number is missing", Toast.LENGTH_SHORT).show();
                                }

                            });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void compareBarcodeToDB(String serialNumber) {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            db.collection("users").document(uid).collection("items")
                    .whereEqualTo("serialNumber", serialNumber)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                            if (!documentSnapshots.isEmpty()) {
                                DocumentSnapshot document = documentSnapshots.get(0);
                                Item item = document.toObject(Item.class);

                                if (item != null) {
                                    String description = item.getDescription();

                                    if (description != null) {
                                        binding.descriptionDetailsTextView.setText(description);
                                    }
                                }

                            }
                        }
                    });
        }
    }
}
