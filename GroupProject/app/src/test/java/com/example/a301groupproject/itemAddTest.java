package com.example.a301groupproject;

import static org.junit.Assert.assertFalse;
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
    public void testAddNameEmpty() {
        Item mockItem = new Item("","V8","Germany","2011-10-15","50000","000","an engine","nothing special",tag);
        assertFalse(mockItem.getName()==null);
    }
    @Test
    public void testAddMakeEmpty() {
        Item mockItem = new Item("Mock","","Germany","2011-10-15","50000","000","an engine","nothing special",tag);
        assertFalse(mockItem.getModel()==null);
    }

    @Test
    public void testAddModelEmpty() {
        Item mockItem = new Item("Mock","V8","","2011-10-15","50000","000","an engine","nothing special",tag);
        assertFalse( mockItem.getMake()==null);
    }

    @Test
    public void testAddDateEmpty() {
        Item mockItem = new Item("Mock","V8","Germany"," ","50000","000","an engine","nothing special",tag);
        assertFalse(mockItem.getDate()==null);
    }

    @Test
    public void testAddValueEmpty() {
        Item mockItem = new Item("Mock","V8","Germany","2011-10-15 ","","000","an engine","nothing special",tag);
        assertFalse(mockItem.getValue()==null);
    }

    @Test
    public void testAddDesEmpty() {
        Item mockItem = new Item("Mock","V8","Germany","2011-10-15 ","5000","000","","nothing special",tag);
        assertFalse(mockItem.getDescription()==null);
    }

    @Test
    public void testAddCommentEmpty() {
        Item mockItem = new Item("Mock","V8","Germany","2011-10-15 ","5000","000","an engine","",tag);
        assertFalse(mockItem.getDescription()==null);
    }


}
