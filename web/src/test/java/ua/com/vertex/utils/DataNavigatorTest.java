package ua.com.vertex.utils;

import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class DataNavigatorTest {

    @Test
    public void dataNavigatorDefaultValueTest() {
        DataNavigator dataNavigator = new DataNavigator("Test DataNavigator");
        dataNavigator = DataNavigator.updateDataNavigator(dataNavigator, 999);

        assertEquals("Test DataNavigator", dataNavigator.getCurrentNamePage());
        assertEquals(1, dataNavigator.getCurrentNumberPage());
        assertEquals(1, dataNavigator.getNextPage());
        assertEquals(40, dataNavigator.getLastPage());
        assertEquals(25, dataNavigator.getRowPerPage());
        assertEquals(40, dataNavigator.getTotalPages());
        assertEquals(999, dataNavigator.getDataSize());
        assertEquals(new TreeMap<Integer, Integer>() {{
            put(25, 25);
            put(50, 50);
            put(100, 100);
        }}, dataNavigator.getCountRowPerPage());
    }

    @Test
    public void DataNavigatorZeroDataSizeTest() {
        DataNavigator dataNavigator = new DataNavigator("Test DataNavigator");
        dataNavigator = DataNavigator.updateDataNavigator(dataNavigator, 0);

        assertEquals("Test DataNavigator", dataNavigator.getCurrentNamePage());
        assertEquals(1, dataNavigator.getCurrentNumberPage());
        assertEquals(1, dataNavigator.getNextPage());
        assertEquals(1, dataNavigator.getLastPage());
        assertEquals(25, dataNavigator.getRowPerPage());
        assertEquals(1, dataNavigator.getTotalPages());
        assertEquals(0, dataNavigator.getDataSize());
        assertEquals(new TreeMap<Integer, Integer>() {{
            put(25, 25);
            put(50, 50);
            put(100, 100);
        }}, dataNavigator.getCountRowPerPage());
    }

    @Test
    public void DataNavigatorChangeRowPerPageToHigherWhenCurrentPageIsLastTest() {
        DataNavigator dataNavigator = new DataNavigator("Test DataNavigator");
        dataNavigator.setCurrentNumberPage(20);
        dataNavigator.setNextPage(20);
        dataNavigator.setLastPage(20);
        dataNavigator.setRowPerPage(50);
        dataNavigator = DataNavigator.updateDataNavigator(dataNavigator, 999);

        dataNavigator.setRowPerPage(25);
        dataNavigator = DataNavigator.updateDataNavigator(dataNavigator, 999);

        assertEquals("Test DataNavigator", dataNavigator.getCurrentNamePage());
        assertEquals(1, dataNavigator.getCurrentNumberPage());
        assertEquals(20, dataNavigator.getNextPage());
        assertEquals(40, dataNavigator.getLastPage());
        assertEquals(25, dataNavigator.getRowPerPage());
        assertEquals(40, dataNavigator.getTotalPages());
        assertEquals(999, dataNavigator.getDataSize());
        assertEquals(new TreeMap<Integer, Integer>() {{
            put(25, 25);
            put(50, 50);
            put(100, 100);
        }}, dataNavigator.getCountRowPerPage());
    }
}