package common.message.utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Caster {
    private Caster(){}
    public static <TSource,TTarget> TTarget cast(TSource toCast){
        return (TTarget) toCast;
    }
    public static <T> Set<T> fromStream(Stream<T> stream){
        return stream.collect(Collectors.toSet());
    }
    public static<T> Stream<T> fromSet(Set<T> set){
        return set.stream();
    }
}
