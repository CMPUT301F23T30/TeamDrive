package com.example.a301groupproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a301groupproject.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ArrayList<Item> itemList;

    private RecyclerView recyclerView;

    private ItemAdapter itemAdapter;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter = new ItemAdapter(itemList);
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void updateTotalValue() {
        double totalValue = homeViewModel.calculateTotalValue();
        String formattedTotal = String.format(Locale.getDefault(), "Total Value: $%.2f", totalValue);
        binding.totalValueTextView.setText(formattedTotal); // 更新 TextView 显示

    }

    private void deleteSelectedItems() {
        ArrayList<Item> itemsToRemove = new ArrayList<>();
        for (Item item : homeViewModel.getItems().getValue()) {
            if (item.isChecked()) {
                itemsToRemove.add(item);
            }
        }
        homeViewModel.getItems().getValue().removeAll(itemsToRemove);
        homeViewModel.getItems().postValue(homeViewModel.getItems().getValue());
        itemAdapter.notifyDataSetChanged();
    }

}