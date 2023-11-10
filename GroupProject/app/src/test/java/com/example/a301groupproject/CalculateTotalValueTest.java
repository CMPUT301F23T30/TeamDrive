package com.example.a301groupproject;

import static org.junit.Assert.assertEquals;

import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.ui.home.HomeViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CalculateTotalValueTest {
    private HomeViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new HomeViewModel();
    }

    @Test
    public void calculateTotalValue_ReturnsCorrectSum() {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Item1", "Model1", "Make1", "2001-09-11", "100.00", "Serial1", "Description1", "Comment1", new ArrayList<>()));
        itemList.add(new Item("Item2", "Model2", "Make2", "2001-09-11", "200.00", "Serial2", "Description2", "Comment2", new ArrayList<>()));


        viewModel.getItems().setValue(itemList);


        double totalValue = viewModel.calculateTotalValue();


        assertEquals("The calculated total value should be correct", 300.00, totalValue, 0.01);
    }
}
