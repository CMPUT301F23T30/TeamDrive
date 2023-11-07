package com.example.a301groupproject.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;

public class HomeFragment extends Fragment implements RvInterface {

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

        itemAdapter = new ItemAdapter(itemList,this);
        recyclerView.setAdapter(itemAdapter);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            itemAdapter.setItems(items);
            itemAdapter.notifyDataSetChanged();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        Log.d("MyTag", "The clicking position is: " + position);
        Bundle bundle = new Bundle();
        bundle.putInt("loc",position);
        NavController navController = NavHostFragment.findNavController(HomeFragment.this);
        navController.navigate(R.id.nav_addItem,bundle);
    }
}