package com.example.a301groupproject;


import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.ui.home.HomeFragment;
import com.example.a301groupproject.ui.home.HomeViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

@RunWith(MockitoJUnitRunner.class)
public class itemSortTest {
    public HomeFragment homeFragment;

    @Mock
    public HomeViewModel mockHomeViewModel;
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockHomeViewModel = Mockito.mock(HomeViewModel.class);

        // Initialize the MutableLiveData
        MutableLiveData<ArrayList<Item>> itemsLiveData = new MutableLiveData<>();
        itemsLiveData.setValue(new ArrayList<>());

        // Make sure getTheItems() returns the initialized MutableLiveData
        when(mockHomeViewModel.getTheItems()).thenReturn(itemsLiveData);
        homeFragment = new HomeFragment();
        homeFragment.setHomeViewModel(mockHomeViewModel);
    }

    @Test
    public void testSortItem_ByDateAscending() {
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "make1", "2022-01-01", "100", "000", "desc1", "comment1", new ArrayList<>()),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", new ArrayList<>())
        ));
        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);
        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("date↑");

        assertEquals("2021-01-01", items.get(0).getDate());
        assertEquals("2022-01-01", items.get(1).getDate());
    }

    @Test
    public void testSortItem_ByDateDescending() {
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "make1", "2022-01-01", "100", "000", "desc1", "comment1", new ArrayList<>()),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", new ArrayList<>())
        ));
        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);
        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("date↓");

        assertEquals("2022-01-01", items.get(0).getDate());
        assertEquals("2021-01-01", items.get(1).getDate());

    }
    @Test
    public void testSortItem_ByDescriptionAscending() {
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "make1", "2022-01-01", "100", "000", "desc1", "comment1", new ArrayList<>()),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", new ArrayList<>())
        ));

        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);

        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("description↑");

        assertEquals("desc1", items.get(0).getDescription());
        assertEquals("desc2", items.get(1).getDescription());
    }

    @Test
    public void testSortItem_ByDescriptionDescending() {
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "make1", "2022-01-01", "100", "000", "desc1", "comment1", new ArrayList<>()),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", new ArrayList<>())
        ));

        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);

        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("description↓");

        assertEquals("desc2", items.get(0).getDescription());
        assertEquals("desc1", items.get(1).getDescription());
    }

    @Test
    public void testSortItem_ByMakeAscending() {
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "make1", "2022-01-01", "100", "000", "desc1", "comment1", new ArrayList<>()),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", new ArrayList<>())
        ));
        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);
        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("make↑");

        assertEquals("make1", items.get(0).getMake());
        assertEquals("make2", items.get(1).getMake());
    }

    @Test
    public void testSortItem_ByMakeDescending() {
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "zzzz", "2022-01-01", "100", "000", "desc1", "comment1", new ArrayList<>()),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", new ArrayList<>())
        ));
        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);
        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("make↑");

        assertEquals("make2", items.get(0).getMake());
        assertEquals("zzzz", items.get(1).getMake());
    }

    @Test
    public void testSortItem_ByValueAscending() {
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "make1", "2022-01-01", "100", "000", "desc1", "comment1", new ArrayList<>()),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", new ArrayList<>())
        ));
        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);
        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("value↑");

        assertEquals("100", items.get(0).getValue());
        assertEquals("200", items.get(1).getValue());
    }

    @Test
    public void testSortItem_ByValueDescending() {
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "make1", "2022-01-01", "100", "000", "desc1", "comment1", new ArrayList<>()),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", new ArrayList<>())
        ));
        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);
        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("value↓");

        assertEquals("200", items.get(0).getValue());
        assertEquals("100", items.get(1).getValue());
    }

    @Test
    public void testSortItem_ByTag() {
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("abc1");
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("abc2");

        ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("item1", "model1", "make1", "2022-01-01", "100", "000", "desc1", "comment1",list1),
                new Item("item2", "model2", "make2", "2021-01-01", "200", "001", "desc2", "comment2", list2)
        ));
        MutableLiveData<ArrayList<Item>> liveDataItems = new MutableLiveData<>(items);
        when(mockHomeViewModel.getTheItems()).thenReturn(liveDataItems);

        homeFragment.sortItem("tag");

        assertEquals(list1, items.get(0).getTags());
        assertEquals(list2, items.get(1).getTags());
    }



}
