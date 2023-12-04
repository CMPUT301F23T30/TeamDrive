package com.example.a301groupproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.ui.home.HomeViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
@RunWith(MockitoJUnitRunner.class)

public class CalculateTotalValueTest{
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public HomeViewModel viewModel= new HomeViewModel();
    @Before
    public void setup(){
        viewModel = new HomeViewModel();
    }

    @Test
    public void addImage_AddsImageToImagesList() {
        viewModel.addImage("testImageUrl");
        assertTrue(viewModel.getImages().getValue().contains("testImageUrl"));
    }

    @Test
    public void addDeleteImage_AddsImageToDeleteImagesList() {
        viewModel.addDeleteImage("testImageUrl");
        assertTrue(viewModel.getDeleteImages().getValue().contains("testImageUrl"));
    }

    @Test
    public void removeDeleteImage_RemovesImageFromDeleteImagesList() {
        viewModel.addDeleteImage("testImageUrl");
        viewModel.removeDeleteImage("testImageUrl");
        assertTrue(!viewModel.getDeleteImages().getValue().contains("testImageUrl"));
    }

    @Test
    public void emptyImages_ClearsImagesList() {
        viewModel.addImage("testImageUrl");
        viewModel.emptyImages();
        assertEquals(0, viewModel.getImages().getValue().size());
    }

    @Test
    public void emptyDeletedImages_ClearsDeletedImagesList() {
        viewModel.addDeleteImage("testImageUrl");
        viewModel.emptyDeletedImages();
        assertEquals(0, viewModel.getDeleteImages().getValue().size());
    }

    @Test
    public void calculateTotalValue_ReturnsCorrectSum_WhenItemsPresent() {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Item1", "Model1", "Make1", "2001-09-11", "100.00", "Serial1", "Description1", "Comment1", new ArrayList<>()));
        itemList.add(new Item("Item2", "Model2", "Make2", "2001-09-11", "200.00", "Serial2", "Description2", "Comment2", new ArrayList<>()));

        viewModel.setItemsValue(itemList);

        assertEquals("Total value should be correct when items are present", 300.00, viewModel.calculateTotalValue(), 0.01);
    }



}
