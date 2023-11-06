package com.example.a301groupproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a301groupproject.databinding.FragmentAddItemBinding;
import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.ui.home.HomeViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // not done with the limits
            public void onClick(View v) {
                String itemName = binding.itemNameInput.getText().toString();
                String itemModel = binding.itemModelInput.getText().toString();
                String itemMake = binding.itemMakeInput.getText().toString();
                String estimatedValue = binding.estimatedValueInput.getText().toString();
                String serialNumber = binding.serialNumberInput.getText().toString();
                String description = binding.descriptionInput.getText().toString();
                String comment = binding.commentInput.getText().toString();
                String year = binding.inputYear.getText().toString();
                String month = binding.inputMonth.getText().toString();
                String day = binding.inputDay.getText().toString();
                String itemDate = year + "-" + month + '-' + day;

                //notification of all required properties
                if (itemName.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the item name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (itemModel.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the item model", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (itemMake.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the item make", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (year.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter yy", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (month.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter mm", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (day.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter dd", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (estimatedValue.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the item estimated value", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (comment.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the comment", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (description.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the item description", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    SimpleDateFormat dateInForm = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateFormatted = dateInForm.parse(itemDate);
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Please enter date in yyyy-mm-dd format", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
                }

                Item item = new Item(itemName, itemModel, itemMake, itemDate, estimatedValue, serialNumber, description,comment);
                homeViewModel.addItem(item);

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
