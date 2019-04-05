package pagination;

import common.domain.BaseEntity;

import java.util.List;
import java.util.stream.Stream;

public class Page<T extends BaseEntity> {

    private List<T> elements;
    private PageGenerator nextPage;

    public Page(List<T> elements, PageGenerator nextPage){
        this.elements = elements;
        this.nextPage = nextPage;
    }

    public Stream<T> getElements(){
        return elements.stream();
    }
    public PageGenerator getNextPage(){
        return nextPage;
    }
}
