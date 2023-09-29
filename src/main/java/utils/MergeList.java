package utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MergeList {
    private List list;
    private Set set;

    public MergeList() {
        this.list = new ArrayList<>();
        this.set = new LinkedHashSet<>();
    }

    public List mergeListNoDuplicates(List A, List K) {
//            Add to set to remove duplicates
        set = new LinkedHashSet<String>(A);
        set.addAll(K);
//            convert to list
        list = new ArrayList<>(set);
        System.out.println("New Genres" + list);
        return list;
    }
}
