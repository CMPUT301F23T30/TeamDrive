package com.example.a301groupproject.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a301groupproject.R;
import com.example.a301groupproject.databinding.FragmentHomeBinding;
import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.factory.item.ItemAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.Consumer;

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

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
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


        return root;

    }
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

                        break;
                    case "By Make":

                        break;
                    case "By Tag":

                        break;
                }
                return true;
            }
        });
        filterMenu.show();
    }

    private Calendar startDate;
    private Calendar endDate;

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
                ArrayList<Item> sortedArray = homeViewModel.getTheItems().getValue();
                dialog.dismiss();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void showDatePickerDialog(boolean isStartDate, Consumer<String> dateConsumer) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
            dateConsumer.accept(formattedDate); // Pass the formatted date string to the consumer
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



}