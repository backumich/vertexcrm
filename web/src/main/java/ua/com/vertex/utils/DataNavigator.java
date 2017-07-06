package ua.com.vertex.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
@Scope("session")
public class DataNavigator {
    private String currentNamePage = "";
    private int currentNumberPage = 1;
    private int nextPage = 1;
    private int lastPage = 1;
    private int currentRowPerPage = 25;
    private int totalPages = 0;
    private int dataSize = 0;
    private Map<Integer, Integer> countRowPerPage = new TreeMap<Integer, Integer>() {{
        put(3, 3);
        put(9, 9);
        put(25, 25);
        put(50, 50);
        put(100, 100);
    }};

    public DataNavigator() {
    }

    public DataNavigator(String currentNamePage) {
        this.currentNamePage = currentNamePage;
    }

    public DataNavigator(String currentNamePage, int currentNumberPage, int currentRowPerPage, int dataSize) {
        this.currentNamePage = currentNamePage;
        this.currentNumberPage = currentNumberPage;
        this.currentRowPerPage = currentRowPerPage;
        this.dataSize = dataSize;
    }

    public String getCurrentNamePage() {
        return currentNamePage;
    }

    public void setCurrentNamePage(String currentNamePage) {
        this.currentNamePage = currentNamePage;
    }

    public int getCurrentNumberPage() {
        return currentNumberPage;
    }

    public void setCurrentNumberPage(int currentNumberPage) {
        this.currentNumberPage = currentNumberPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getCurrentRowPerPage() {
        return currentRowPerPage;
    }

    public void setCurrentRowPerPage(int currentRowPerPage) {
        this.currentRowPerPage = currentRowPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public Map<Integer, Integer> getCountRowPerPage() {
        return countRowPerPage;
    }

    public void setCountRowPerPage(Map<Integer, Integer> countRowPerPage) {
        this.countRowPerPage = countRowPerPage;
    }
}
