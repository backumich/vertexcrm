package ua.com.vertex.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
@Scope("session")
public class DataNavigator {
    private static final Logger LOGGER = LogManager.getLogger(DataNavigator.class);
    private String currentNamePage = "";
    private int currentNumberPage = 1;
    private int nextPage = 1;
    private int lastPage = 1;
    private int rowPerPage = 25;
    private int totalPages = 0;
    private int dataSize = 0;
    private Map<Integer, Integer> countRowPerPage = new TreeMap<Integer, Integer>() {{
        put(25, 25);
        put(50, 50);
        put(100, 100);
    }};

    public DataNavigator() {
    }

    public DataNavigator(String currentNamePage) {
        this.currentNamePage = currentNamePage;
    }

    public DataNavigator(String currentNamePage, int currentNumberPage, int rowPerPage, int dataSize) {
        this.currentNamePage = currentNamePage;
        this.currentNumberPage = currentNumberPage;
        this.rowPerPage = rowPerPage;
        this.dataSize = dataSize;
    }

    public void updateDataNavigator(int dataSize) {
        LOGGER.debug("Update dataNavigator");
        int totalPages = (int) Math.ceil((double) dataSize / this.rowPerPage);

        this.dataSize = dataSize;
        if (totalPages == 0) {
            this.currentNumberPage = 1;
            this.nextPage = 1;
            this.lastPage = 1;
            this.totalPages = 1;
        } else if (totalPages != this.totalPages) {
            this.totalPages = totalPages;
            this.currentNumberPage = 1;
            this.lastPage = totalPages;
        } else {
            this.totalPages = totalPages;
            this.currentNumberPage = this.nextPage;
            this.lastPage = totalPages;
        }
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

    public int getRowPerPage() {
        return rowPerPage;
    }

    public void setRowPerPage(int rowPerPage) {
        this.rowPerPage = rowPerPage;
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
