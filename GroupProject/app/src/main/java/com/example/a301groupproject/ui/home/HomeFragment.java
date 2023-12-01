package com.example.a301groupproject.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterMenu(v);
            }
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
     * Displays a filter menu with options for filtering items.
     *
     * @param v The view that triggers this menu.
     */
    private void showFilterMenu(View v) {
        PopupMenu filterMenu = new PopupMenu(getContext(), v);
        filterMenu.getMenu().add("By Date Range");
        filterMenu.getMenu().add("By Description Keyword");
        filterMenu.getMenu().add("By Make");
        filterMenu.getMenu().add("By Tag");

        filterMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "By Date Range":
                        showDateRangePicker(v);
                        break;
                    case "By Description Keyword":
                        showTextInputDialog("By Description Keyword", this::filterItemsByDescription);
                        break;
                    case "By Make":
                        showTextInputDialog("By Make", this::filterItemsByMake);
                        break;
                    case "By Tag":
                        showTextInputDialog("By Tag", this::filterItemsByTag);
                        break;

                }
                return true;
            }
            /**
             * Filters items by their description.
             *
             * @param keyword The keyword to filter the description.
             */
            private void filterItemsByDescription(String keyword) {
                ArrayList<Item> allItems = homeViewModel.getTheItems().getValue();
                ArrayList<Item> filteredItems = new ArrayList<>();
                if (allItems != null) {
                    for (Item item : allItems) {
                        if (item.getDescription() != null && item.getDescription().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) {
                            filteredItems.add(item);
                        }
                    }
                }
                homeViewModel.setItemsValue(filteredItems);
            }

            /**
             * Filters items by their make.
             *
             * @param make The make to filter the items.
             */
            private void filterItemsByMake(String make) {
                ArrayList<Item> allItems = homeViewModel.getTheItems().getValue();
                ArrayList<Item> filteredItems = new ArrayList<>();
                if (allItems != null) {
                    for (Item item : allItems) {
                        if (item.getMake() != null && item.getMake().toLowerCase(Locale.ROOT).contains(make.toLowerCase(Locale.ROOT))) {
                            filteredItems.add(item);
                        }
                    }
                }
                homeViewModel.setItemsValue(filteredItems);
            }

            /**
             * Filters items by tags.
             *
             * @param inputTags The tags to use for filtering.
             */
            private void filterItemsByTag(String inputTags) {
                ArrayList<Item> allItems = homeViewModel.getTheItems().getValue();
                ArrayList<Item> filteredItems = new ArrayList<>();

                // Split the inputTags string into individual tags and prepare for comparison
                Set<String> lowerCaseTagsToFilter = Arrays.stream(inputTags.split(","))
                        .map(tag -> tag.trim().toLowerCase(Locale.ROOT))
                        .collect(Collectors.toSet());

                if (allItems != null) {
                    for (Item item : allItems) {
                        if (item.getTags() != null) {
                            Set<String> lowerCaseItemTags = item.getTags().stream()
                                    .map(String::toLowerCase)
                                    .collect(Collectors.toSet());

                            // Check if the item's tags contain all the tags to filter
                            if (lowerCaseItemTags.containsAll(lowerCaseTagsToFilter)) {
                                filteredItems.add(item);
                            }
                        }
                    }
                }

                homeViewModel.setItemsValue(filteredItems);
            }



        });
        filterMenu.show();

    }

    private Calendar startDate;
    private Calendar endDate;

    /**
     * Shows a date range picker dialog for filtering items by date.
     *
     * @param anchorView The view to anchor the date picker dialog to.
     */
    private void showDateRangePicker(View anchorView) {
        startDate = null;
        endDate = null;
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View popupView = inflater.inflate(R.layout.date_range_picker, null);

        // Set up the TextViews for start and end dates
        TextView startDateText = popupView.findViewById(R.id.start_date_text);
        TextView endDateText = popupView.findViewById(R.id.end_date_text);

        startDateText.setOnClickListener(v -> showDatePickerDialog(true, date -> {
            startDateText.setText(date);
            startDate = Calendar.getInstance();
            try {
                startDate.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }));

        endDateText.setOnClickListener(v -> showDatePickerDialog(false, date->{
            endDateText.setText(date);
            endDate = Calendar.getInstance();
            try {
                endDate.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }));


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(popupView);
        dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
            if (startDate == null || endDate == null) {
                Toast.makeText(getContext(), "Please select both start and end dates", Toast.LENGTH_SHORT).show();
            } else if (endDate.before(startDate)) {
                Toast.makeText(getContext(), "End date must be after start date", Toast.LENGTH_SHORT).show();
            } else {

                ArrayList<Item> allItems = homeViewModel.getTheItems().getValue();
                ArrayList<Item> filteredItems = new ArrayList<>();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                for (Item item : allItems) {
                    try {
                        Date itemDate = format.parse(item.getDate());
                        if (itemDate != null && !itemDate.before(startDate.getTime()) && !itemDate.after(endDate.getTime())) {
                            filteredItems.add(item);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace(); // Handle parsing error
                    }
                }
                homeViewModel.setItemsValue(filteredItems);

                dialog.dismiss();
            }

        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    /**
     * Shows a DatePickerDialog for selecting a date.
     *
     * @param isStartDate Indicates whether the date is for the start or end of the range.
     * @param dateConsumer The consumer to handle the selected date.
     */
    private void showDatePickerDialog(boolean isStartDate, Consumer<String> dateConsumer) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
            dateConsumer.accept(formattedDate);
        };

        new DatePickerDialog(getContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
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

    /**
     * Shows a text input dialog for filtering items.
     *
     * @param title         The title of the dialog.
     * @param inputHandler  The handler to process the input text.
     */
    private void showTextInputDialog(String title, Consumer<String> inputHandler) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(title);

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        if ("By Tag".equals(title)) {
            input.setHint("Enter tags separated by commas");
        }
        dialogBuilder.setView(input);


        dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
            String inputText = input.getText().toString().trim();
            if (inputText.equals("")){
                Toast.makeText(getContext(), "Please enter something....", Toast.LENGTH_SHORT).show();
            }
            else{
                inputHandler.accept(inputText);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        dialogBuilder.show();

    }


}