package com.example.a301groupproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a301groupproject.databinding.FragmentHomeBinding;
import com.example.a301groupproject.factory.Item;
import com.example.a301groupproject.factory.ItemAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ArrayList<Item> itemList;

    private RecyclerView recyclerView;

    private ItemAdapter itemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeData();

        recyclerView = binding.recycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

        return root;
    }

    private void initializeData() {
        itemList = new ArrayList<>();
        itemList.add(new Item("Item 1", "Model 1", "Make 1", "Date 1", "Price 1"));
        itemList.add(new Item("Item 2", "Model 2", "Make 2", "Date 2", "Price 2"));
        itemList.add(new Item("Item 3", "Model 3", "Make 3", "Date 3", "Price 3"));
        itemList.add(new Item("Item 4", "Model 4", "Make 4", "Date 4", "Price 4"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}