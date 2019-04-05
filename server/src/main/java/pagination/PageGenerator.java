package pagination;

import common.domain.BaseEntity;

public class PageGenerator{
    private final static int DEFAULT_PAGE_SIZE = 2;
    private int currentPage;
    private int pageSize;

    public PageGenerator(int currentPage, int pageSize){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
    public PageGenerator(){
        currentPage = 0;
        pageSize = DEFAULT_PAGE_SIZE;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public int getPageSize(){
        return pageSize;
    }
}