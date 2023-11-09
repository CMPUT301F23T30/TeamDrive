package com.example.a301groupproject;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * This is a Fragment that handle the add,edit and delete of an single item record
 */

public class EditItemFragment extends Fragment {

    private FragmentAddItemBinding binding;

    private HomeViewModel homeViewModel;
    private Uri imageUri;
    

    EditText itemTagInput;
    Button add_tag;
    ChipGroup chipGroup;
    ArrayList<String> tagList = new ArrayList<>();
    String input;
    private Object e;

    /**
     * Default constructor for the EditItemFragment.
     */
    public EditItemFragment() {
    }
    /**
     * Inflates the fragment's view and initializes its components.
     *
     * @param inflater           The layout inflater.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState  A saved state bundle.
     * @return                   The inflated view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddItemBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        Bundle receivedBundle = getArguments();

        //Tags function
        add_tag = view.findViewById(R.id.addtagbutton);
        itemTagInput = view.findViewById(R.id.itemTagInput);
        chipGroup = view.findViewById(R.id.chipgroup);

        add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagText = itemTagInput.getText().toString();
                setChips(tagText);
            }
        });

        if (receivedBundle != null) {
            int receivedIntValue = (receivedBundle.getInt("loc"));

            Item i = homeViewModel.getItems().getValue().get(receivedIntValue);

            String date = i.getDate();
            String[] year_month_day = date.split("-");

            binding.itemNameInput.setText(i.getName());
            binding.itemModelInput.setText(i.getModel());
            binding.itemMakeInput.setText(i.getMake());
            binding.inputYear.setText(year_month_day[0]);
            binding.inputMonth.setText(year_month_day[1]);
            binding.inputDay.setText(year_month_day[2]);
            binding.serialNumberInput.setText(i.getSerialNumber());
            binding.estimatedValueInput.setText(i.getValue());
            binding.descriptionInput.setText(i.getDescription());
            binding.commentInput.setText(i.getComment());
            // TODO: only support the viewing of images when checking the detail of an item record
            homeViewModel.emptyImages();
            ArrayList<String> imageUris = i.getImages();
            for (String uri : imageUris) {
                homeViewModel.addImage(Uri.parse(uri));
            }

            chipGroup.removeAllViews();
            tagList.clear();
            ArrayList<String> tags = i.getTags();
            for(String tag:tags){
                setChips(tag);
            }

            binding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeViewModel.removeItem(homeViewModel.getItems().getValue().get(receivedIntValue));
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigateUp();

                }
            });
        }else {
            binding.deleteButton.setVisibility(View.INVISIBLE);
        }

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

                ArrayList<String> imageUris = new ArrayList<>();
                ArrayList<Uri> UriImageUris = homeViewModel.getImages().getValue();

                for (Uri uri : UriImageUris) {
                    imageUris.add(uri.toString());
                }

                Item item = new Item(itemName, itemModel, itemMake, itemDate, estimatedValue, serialNumber, description, comment, tagList);
                if (receivedBundle == null) {
                    homeViewModel.addItem(item, imageUris);
                    homeViewModel.emptyImages();
                } else {
                    int receivedIntValue = (receivedBundle.getInt("loc"));
                    Item i = homeViewModel.getItems().getValue().get(receivedIntValue);
                    item.setId(i.getId());
                    homeViewModel.editItem(item,imageUris);
                    homeViewModel.emptyImages();
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


    /**
     * Sets a chip with the specified text in the chip group for tags.
     *
     * @param e  The text to set in the chip.
     */
    public void setChips(String e) {
        final Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.single_input_chip_layout, null, false);
        chip.setText(e);
//        chip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tagList.add(e);
//            }
//        });
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeView(chip);
                tagList.remove(e);
            }
        });
        tagList.add(e);
        chipGroup.addView(chip);

    }
}
