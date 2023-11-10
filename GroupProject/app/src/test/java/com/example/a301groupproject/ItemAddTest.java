package com.example.a301groupproject;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import com.example.a301groupproject.factory.item.Item;
import com.example.a301groupproject.ui.home.HomeFragment;
import com.example.a301groupproject.ui.home.HomeViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
@RunWith(MockitoJUnitRunner.class)

public class itemAddTest {
    public ArrayList<String> tag = new ArrayList<>();

    public HomeViewModel HVM= new HomeViewModel();
    public HomeFragment HF = new HomeFragment();
    @Before
    public void setup(){
        HVM = mock(HomeViewModel.class);
        HF = mock(HomeFragment.class);
    }

    @Test
    void testAddNameEmpty() {
        public Item mockItem = new Item("","V8","Germany","2011-10-15","50000","000","an engine","nothing special",tag);
        assertFalse(null, mockItem.getName());
    }
    @Test
    void testAddMakeEmpty() {
        public Item mockItem = new Item("Mock","","Germany","2011-10-15","50000","000","an engine","nothing special",tag);
        assertFalse(null, mockItem.getModel());
    }

    @Test
    void testAddModelEmpty() {
        public Item mockItem = new Item("Mock","V8","","2011-10-15","50000","000","an engine","nothing special",tag);
        assertFalse(null, mockItem.getMake());
    }

    @Test
    void testAddDateEmpty() {
        public Item mockItem = new Item("Mock","V8","Germany"," ","50000","000","an engine","nothing special",tag);
        assertFalse(null, mockItem.getDate());
    }

    @Test
    void testAddValueEmpty() {
        public Item mockItem = new Item("Mock","V8","Germany","2011-10-15 ","","000","an engine","nothing special",tag);
        assertFalse(null, mockItem.getValue());
    }

    @Test
    void testAddDesEmpty() {
        public Item mockItem = new Item("Mock","V8","Germany","2011-10-15 ","5000","000","","nothing special",tag);
        assertFalse(null, mockItem.getDescription());
    }

    @Test
    void testAddCommentEmpty() {
        public Item mockItem = new Item("Mock","V8","Germany","2011-10-15 ","5000","000","an engine","",tag);
        assertFalse(null, mockItem.getDescription());
    }


}