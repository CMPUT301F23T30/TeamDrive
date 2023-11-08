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
import com.example.a301groupproject.factory.Item;
import com.example.a301groupproject.factory.ItemAdapter;

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

        // 在 HomeFragment 的 onCreateView 方法中添加按钮的监听器
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 执行删除操作
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
        double totalValue = homeViewModel.calculateTotalValue(); // 调用 ViewModel 的方法来计算总价值
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
        homeViewModel.getItems().getValue().removeAll(itemsToRemove); // 从 ViewModel 数据中移除选中的项目
        homeViewModel.getItems().postValue(homeViewModel.getItems().getValue()); // 更新 LiveData
        itemAdapter.notifyDataSetChanged(); // 通知适配器数据已更改
    }

}