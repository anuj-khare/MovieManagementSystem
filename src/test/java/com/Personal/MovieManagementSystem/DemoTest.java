package com.Personal.MovieManagementSystem;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DemoTest {
    @Mock
    private List<Integer> listmock;
    @Test
    public void ListTestTrue(){
        assertTrue(2==2);
    }
    @Test
    public void ListTestFalse(){
        assertFalse(21==2);
    }
    @Test
    public void ListAddTest(){
        listmock = mock(ArrayList.class);
        listmock.add(1);
        when(listmock.get(0)).thenReturn(1);
        assertEquals(1,listmock.get(0));
    }
    @Test
    public void verifyMethodCall(){
        listmock = mock(ArrayList.class);
        listmock.add(2);
        verify(listmock,times(1)).add(anyInt());
    }
}
