package com.example.a301groupproject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.ui.home.HomeFragment;
import com.example.a301groupproject.ui.home.HomeViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
/**
@RunWith(MockitoJUnitRunner.class)
public class itemFilterTest {
    @Mock
    private HomeViewModel homeViewModel;

    @Mock
    private View view;

    @Captor
    private ArgumentCaptor<ArrayList<Item>> itemsCaptor;

    private HomeFragment homeFragment;

    @Before
    public void setup() {
        homeFragment = new HomeFragment();
        homeFragment.homeViewModel = homeViewModel;
    }

    @Test
    public void testFilterItemsByDescriptionkeyword() {
        // Arrange
        String keyword = "engine";
        ArrayList<Item> allItems = new ArrayList<>();
        allItems.add(new Item("engine1", "V8", "Germany", "2011-10-15", "50000", "000", "an engine", "nothing special", new ArrayList<>()));
        allItems.add(new Item("engine2", "V6", "USA", "2012-05-20", "60000", "001", "another engine", "not so special", new ArrayList<>()));
        when(homeViewModel.getTheItems().getValue()).thenReturn(allItems);

        // Act
        homeFragment.filterItemsByDescription(keyword);

        // Assert
        verify(homeViewModel).setItemsValue(itemsCaptor.capture());
        ArrayList<Item> filteredItems = itemsCaptor.getValue();
        assertEquals(1, filteredItems.size());
        assertEquals("engine1", filteredItems.get(0).getName());
    }

    @Test
    public void testFilterItemsByMake() {
        // Arrange
        String make = "Germany";
        ArrayList<Item> allItems = new ArrayList<>();
        allItems.add(new Item("engine1", "V8", "Germany", "2011-10-15", "50000", "000", "an engine", "nothing special", new ArrayList<>()));
        allItems.add(new Item("engine2", "V6", "USA", "2012-05-20", "60000", "001", "another engine", "not so special", new ArrayList<>()));
        when(homeViewModel.getTheItems().getValue()).thenReturn(allItems);

        // Act
        homeFragment.filterItemsByMake(make);

        // Assert
        verify(homeViewModel).setItemsValue(itemsCaptor.capture());
        ArrayList<Item> filteredItems = itemsCaptor.getValue();
        assertEquals(1, filteredItems.size());
        assertEquals("engine1", filteredItems.get(0).getName());
    }

    @Test
    public void testFilterItemsByTag() {
        // Arrange
        String inputTags = "steel, powerful";
        ArrayList<Item> allItems = new ArrayList<>();
        ArrayList<String> tags1 = new ArrayList<>(Arrays.asList("steel", "powerful"));
        ArrayList<String> tags2 = new ArrayList<>(Arrays.asList("aluminum", "fast"));
        allItems.add(new Item("engine1", "V8", "Germany", "2011-10-15", "50000", "000", "an engine", "nothing special", tags1));
        allItems.add(new Item("engine2", "V6", "USA", "2012-05-20", "60000", "001", "another engine", "not so special", tags2));
        when(homeViewModel.getTheItems().getValue()).thenReturn(allItems);

        // Act
        homeFragment.filterItemsByTag(inputTags);

        // Assert
        verify(homeViewModel).setItemsValue(itemsCaptor.capture());
        ArrayList<Item> filteredItems = itemsCaptor.getValue();
        assertEquals(1, filteredItems.size());
        assertEquals("engine1", filteredItems.get(0).getName());
    }

    @Test
    public void testFilterItemsByDateRange() {
        // Arrange
        String startDate = "2011-01-01";
        String endDate = "2011-12-31";
        ArrayList<Item> allItems = new ArrayList<>();
        allItems.add(new Item("engine1", "V8", "Germany", "2011-10-15", "50000", "000", "an engine", "nothing special", new ArrayList<>()));
        allItems.add(new Item("engine2", "V6", "USA", "2012-05-20", "60000", "001", "another engine", "not so special", new ArrayList<>()));
        when(homeViewModel.getTheItems().getValue()).thenReturn(allItems);

        // Act
        homeFragment.filterItemsByDateRange();

        // Assert
        verify(homeViewModel).setItemsValue(itemsCaptor.capture());
        ArrayList<Item> filteredItems = itemsCaptor.getValue();
        assertEquals(1, filteredItems.size());
        assertEquals("engine1", filteredItems.get(0).getName());
    }


}
**/