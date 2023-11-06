package com.example.a301groupproject;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a301groupproject.databinding.FragmentAddItemBinding;
import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.ui.home.HomeViewModel;

import java.util.ArrayList;

public class EditItemFragment extends Fragment {

    private FragmentAddItemBinding binding;

    private HomeViewModel homeViewModel;


    public EditItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddItemBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        Bundle receivedBundle = getArguments();

        if (receivedBundle != null) {
            int receivedIntValue = (receivedBundle.getInt("loc"));

            Item i = homeViewModel.getItems().getValue().get(receivedIntValue);

            binding.itemNameInput.setText(i.getName());
            binding.itemModelInput.setText(i.getModel());
            binding.itemMakeInput.setText(i.getMake());
            binding.itemDateInput.setText(i.getDate());
            binding.estimatedValueInput.setText(i.getValue());

            binding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeViewModel.removeItem(homeViewModel.getItems().getValue().get(receivedIntValue));
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigateUp();

                }
            });
            /*binding.confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String itemName = binding.itemNameInput.getText().toString();
                    String itemModel = binding.itemModelInput.getText().toString();
                    String itemMake = binding.itemMakeInput.getText().toString();
                    String itemDate = binding.itemDateInput.getText().toString();
                    String estimatedValue = binding.estimatedValueInput.getText().toString();

                    Item item = new Item(itemName, itemModel, itemMake, itemDate, estimatedValue);
                    item.setId(i.getId());
                    homeViewModel.editItem(item);

                }
            });*/
        }
        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Need to contain all the attributes (comment, desc...)
                String itemName = binding.itemNameInput.getText().toString();
                String itemModel = binding.itemModelInput.getText().toString();
                String itemMake = binding.itemMakeInput.getText().toString();
                String itemDate = binding.itemDateInput.getText().toString();
                String estimatedValue = binding.estimatedValueInput.getText().toString();

                ArrayList<String> imageUris = new ArrayList<>();
                ArrayList<Uri> value = homeViewModel.getImages().getValue();

                for (Uri uri : value) {
                    imageUris.add(uri.toString());
                }

                Item item = new Item(itemName, itemModel, itemMake, itemDate, estimatedValue);
                if(receivedBundle == null) {
                    homeViewModel.addItem(item);
                }
                else {
                    int receivedIntValue = (receivedBundle.getInt("loc"));
                    Item i = homeViewModel.getItems().getValue().get(receivedIntValue);
                    item.setId(i.getId());
                    homeViewModel.editItem(item);
                }

                // go back to home page after add confirm
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigateUp();
            }
        });

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_images);
            }
        });
        return view;
    }
}
