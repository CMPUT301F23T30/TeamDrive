package com.example.a301groupproject.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a301groupproject.MainActivity;
import com.example.a301groupproject.R;
import com.example.a301groupproject.databinding.FragmentHomeBinding;
import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.factory.item.ItemAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * HomeFragment is the main UI controller that users interact with for listing, deleting, and managing items.
 * It hosts a RecyclerView to display the list of items and integrates with HomeViewModel for data handling.
 * This fragment allows users to perform actions such as delete and view item details, with changes observed in real-time.
 */
public class HomeFragment extends Fragment implements RvInterface {

    private FragmentHomeBinding binding;

    private ArrayList<Item> itemList;

    private RecyclerView recyclerView;

    private ItemAdapter itemAdapter;

    private HomeViewModel homeViewModel;


    /**
     * Called to have the fragment instantiate its user interface view. This method inflates the layout
     * for the fragment's view and initializes RecyclerView with an adapter.
     *
     * @param inflater           LayoutInflater object to inflate views in the fragment
     * @param container          Parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being reconstructed from a previous saved state
     * @return The root View for the fragment's UI, or null
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemAdapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(itemAdapter);

        Spinner spinner = binding.SpinnerSort;
        spinner.setSelection(0);

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
                spinner.setSelection(0);
            }
        });
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            itemAdapter.setItems(items);
            itemAdapter.notifyDataSetChanged();
            updateTotalValue();
        });

        binding.addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.tagEditText.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(),"please input the tag to add ",Toast.LENGTH_SHORT).show();
                }
                else {
                    String newTag = binding.tagEditText.getText().toString();
                    addTagToItems(newTag);
                    binding.tagEditText.setText("");
                    spinner.setSelection(0);
                    Toast.makeText(v.getContext(),"tag added",Toast.LENGTH_SHORT).show();
                }
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(),selectedValue,Toast.LENGTH_SHORT).show();
                sortItem(selectedValue);
                itemAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }

    /**
     * Called when the view hierarchy associated with the fragment is being removed. This ensures the binding
     * is cleaned up to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Updates the total value displayed in the TextView by calculating the total value of items
     * and formatting it as a currency string.
     */
    private void updateTotalValue() {
        double totalValue = homeViewModel.calculateTotalValue();
        String formattedTotal = String.format(Locale.getDefault(), "Total Value: $%.2f", totalValue);

        binding.totalValueTextView.setText(formattedTotal);
    }

    /**
     * Deletes the selected items from the adapter and the database by checking the items that are marked
     * and using the HomeViewModel to remove them from the backend.
     */
    private void deleteSelectedItems() {
        ArrayList<Item> itemsToRemove = new ArrayList<>();
        for (Item item : homeViewModel.getItems().getValue()) {
            if (item.isChecked()) {
                itemsToRemove.add(item);
            }
        }
        for (Item item : itemsToRemove) {
            homeViewModel.removeItem(item);
        }

    }

    /**
     * Handles item click events by logging the clicked position and navigating to the item addition
     * screen with the item's position passed as an argument for potential editing.
     *
     * @param position The position of the clicked item in the RecyclerView.
     */
    @Override
    public void onItemClick(int position) {
        Log.d("MyTag", "The clicking position is: " + position);
        Bundle bundle = new Bundle();
        bundle.putInt("loc", position);
        NavController navController = NavHostFragment.findNavController(HomeFragment.this);
        navController.navigate(R.id.nav_addItem, bundle);
    }
    /**
     * Adds a new tag to the selected items.
     *
     * This method iterates through the items that currently checked
     * and adds the new tag to those.
     *
     * @param newTag The new tag to be added to the selected items.
     */
    public void addTagToItems(String newTag){
        ArrayList<Item> itemsToAddTag = new ArrayList<>();
        for (Item item : homeViewModel.getTheItems().getValue()) {
            if (item.isChecked()) {
                itemsToAddTag.add(item);
            }
        }
        if (itemsToAddTag.isEmpty()){
            Toast.makeText(this.getContext(),"please select items",Toast.LENGTH_SHORT).show();
        }
        else {
            for (Item item : itemsToAddTag) {
                ArrayList<String> newTags = item.getTags();
                newTags.add(newTag);
                homeViewModel.addTagToItem(item, newTags);
            }
        }

    }
    /**
     * Sorts the list of items based on the specified sorting criteria.
     *
     * This method takes a sorting criteria string and uses it to determine the sorting order for the list of items.
     * The supported sorting criteria include "date↑" (ascending date), "date↓" (descending date),
     * "description↑" (ascending description), "description↓" (descending description),
     * "make↑" (ascending make), "make↓" (descending make),
     * "value↑" (ascending value), "value↓" (descending value),
     * and "tag" (descending number of tags).
     *
     * @param sorter The sorting criteria string that determines the order in which the items should be sorted.
     *               Supported values: "date↑", "date↓", "description↑", "description↓", "make↑", "make↓",
     *               "value↑", "value↓", "tag".
     */
    public void sortItem(String sorter){

        ArrayList<Item> sortedArray = homeViewModel.getTheItems().getValue();
        Collections.sort(sortedArray, new Comparator<Item>(){

            @Override
            public int compare(Item o1, Item o2) {
                if (sorter.equalsIgnoreCase("date↑"))
                    return o1.getDate().compareToIgnoreCase(o2.getDate());
                else if (sorter.equalsIgnoreCase("date↓")) {
                    return o2.getDate().compareToIgnoreCase(o1.getDate());
                }

                else if (sorter.equalsIgnoreCase("description↑"))
                    return o1.getDescription().compareToIgnoreCase(o2.getDescription());
                else if (sorter.equalsIgnoreCase("description↓")) {
                    return o2.getDescription().compareToIgnoreCase(o1.getDescription());
                }

                else if (sorter.equalsIgnoreCase("make↑"))
                    return o1.getMake().compareToIgnoreCase(o2.getMake());
                else if (sorter.equalsIgnoreCase("make↓")) {
                    return o2.getMake().compareToIgnoreCase(o1.getMake());
                }

                else if (sorter.equalsIgnoreCase("value↑"))
                    return Integer.compare(Integer.parseInt(o1.getValue()), Integer.parseInt(o2.getValue()));
                else if (sorter.equalsIgnoreCase("value↓")) {
                    return Integer.compare(Integer.parseInt(o2.getValue()), Integer.parseInt(o1.getValue()));
                }

                else if (sorter.equalsIgnoreCase("tag"))
                    return Integer.compare(o2.getTagsSize(),o1.getTagsSize());

                return 0;
            }
        });
        homeViewModel.setItemsValue(sortedArray);
    }


}