package com.example.a301groupproject;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import android.view.View;

import com.example.a301groupproject.ScanFragment;

public class itemScanTest {

    @Test
    public void testDefaultConstructor() {
        ScanFragment fragment = new ScanFragment();
        assertNotNull(fragment);
    }

    @Test
    public void testTakeImageRequestValue() {
        ScanFragment fragment = new ScanFragment();
        assertEquals(2, fragment.getTakeImageRequest());
    }


}