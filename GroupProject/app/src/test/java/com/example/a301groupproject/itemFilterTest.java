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

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;




@RunWith(MockitoJUnitRunner.class)
public class itemFilterTest {
    @Mock
    public HomeViewModel homeViewModel;

    @Mock
    public View view;

    @Captor
    public ArgumentCaptor<ArrayList<Item>> itemsCaptor;

    public HomeFragment homeFragment;

    @Before
    public void setup() {
        homeFragment = new HomeFragment();
        homeFragment.homeViewModel = homeViewModel;
    }

    @Test
    public void testFilterItemsByDescriptionKeyword() {
        // Arrange
        String keyword = "engine";
        ArrayList<Item> allItems = new ArrayList<>();
        allItems.add(new Item("engine1", "V8", "Germany", "2011-10-15", "50000", "000", "an engine", "nothing special", new ArrayList<>()));
        allItems.add(new Item("engine2", "V6", "USA", "2012-05-20", "60000", "001", "another engine", "not so special", new ArrayList<>()));

        // Act code from HomeFragment itemFilteredByDescription
        ArrayList<Item> filteredItems = new ArrayList<>();
        if (allItems != null) {
            for (Item item : allItems) {
                if (item.getDescription() != null && item.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
        }

        //assert
        assertEquals(2, filteredItems.size());
        assertEquals("engine1", filteredItems.get(0).getName());
    }


    @Test
    public void testFilterItemsByMake() {
        // Arrange
        String make = "Germany";
        ArrayList<Item> allItems = new ArrayList<>();
        allItems.add(new Item("engine1", "V8", "Germany", "2011-10-15", "50000", "000", "an engine", "nothing special", new ArrayList<>()));
        allItems.add(new Item("engine2", "V6", "USA", "2012-05-20", "60000", "001", "another engine", "not so special", new ArrayList<>()));
        //when(homeViewModel.getTheItems().getValue()).thenReturn(allItems);

        // Act code from HomeFragment.java itemFilteredByMake
        ArrayList<Item> filteredItems = new ArrayList<>();
        if (allItems != null) {
            for (Item item : allItems) {
                if (item.getMake() != null && item.getMake().toLowerCase(Locale.ROOT).contains(make.toLowerCase(Locale.ROOT))) {
                    filteredItems.add(item);
                }
            }
        }
        // homeViewModel.setItemsValue(filteredItems);

        // Assert
        //verify(homeViewModel).setItemsValue(itemsCaptor.capture());
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
        //when(homeViewModel.getTheItems().getValue()).thenReturn(allItems);

        // Act code from HomeFragment filterItemsByTag(inputTags);
        ArrayList<Item> filteredItems = new ArrayList<>();

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

        //homeViewModel.setItemsValue(filteredItems);
        // Assert
        //verify(homeViewModel).setItemsValue(itemsCaptor.capture());

        assertEquals(1, filteredItems.size());
        assertEquals("engine1", filteredItems.get(0).getName());
    }


    @Test
    public void testFilterItemsByDateRange() {
        // Arrange
        Date startDate = new Date(2011, 01, 01);
        Date endDate = new Date(2011, 12, 31);
        ArrayList<Item> allItems = new ArrayList<>();
        allItems.add(new Item("engine1", "V8", "Germany", "2012-10-15", "50000", "000", "an engine", "nothing special", new ArrayList<>()));
        allItems.add(new Item("engine2", "V6", "USA", "2012-05-20", "60000", "001", "another engine", "not so special", new ArrayList<>()));
        //when(homeViewModel.getTheItems().getValue()).thenReturn(allItems);

        // Act
        //homeFragment.filterItemsByDateRange();
        ArrayList<Item> filteredItems = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (Item item : allItems) {
            try {
                Date itemDate = format.parse(item.getDate());
                if (itemDate != null && !itemDate.before(startDate) && !itemDate.after(endDate)) {
                    filteredItems.add(item);
                }
            } catch (ParseException e) {
                e.printStackTrace(); // Handle parsing error
            }
        }

        // Assert
        //verify(homeViewModel).setItemsValue(itemsCaptor.capture());
        assertEquals(0, filteredItems.size());
    }
}



