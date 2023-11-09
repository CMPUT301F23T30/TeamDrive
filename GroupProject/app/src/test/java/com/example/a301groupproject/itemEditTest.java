package com.example.a301groupproject;

import static org.junit.Assert.assertEquals;
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
public class itemEditTest {
    public ArrayList<String> tag = new ArrayList<>();
    public Item mockItem = new Item("engine","V8","Germany","2011-10-15","50000","000","an engine","nothing special",tag);
    public HomeViewModel HVM= new HomeViewModel();
    public HomeFragment HF = new HomeFragment();
    @Before
    public void setup(){
        tag.add("steel");
        HVM = mock(HomeViewModel.class);
        HF = mock(HomeFragment.class);
    }

    @Test
    public void testViewing(){
        assertEquals("engine",mockItem.getName());
        assertEquals("V8",mockItem.getModel());
        assertEquals("Germany",mockItem.getMake());
        assertEquals("2011-10-15",mockItem.getDate());
        assertEquals("50000",mockItem.getValue());
        assertEquals("000",mockItem.getSerialNumber());
        assertEquals("an engine",mockItem.getDescription());
        assertEquals("nothing special",mockItem.getComment());
        assertEquals(tag,mockItem.getTags());
    }
    @Test
    public void testEditing(){
        mockItem.setName("New engine");
        mockItem.setComment("an updated one");
        mockItem.setDate("2015-05-10");

        assertEquals("New engine",mockItem.getName());
        assertEquals("V8",mockItem.getModel());
        assertEquals("Germany",mockItem.getMake());
        assertEquals("2015-05-10",mockItem.getDate());
        assertEquals("50000",mockItem.getValue());
        assertEquals("000",mockItem.getSerialNumber());
        assertEquals("an engine",mockItem.getDescription());
        assertEquals("an updated one",mockItem.getComment());
        assertEquals(tag,mockItem.getTags());
    }

    @Test
    public void verifyEditTest(){
        HVM.addItem(mockItem,null);
        HVM.removeItem(mockItem);
        HVM.editItem(mockItem,null);
    }


}
