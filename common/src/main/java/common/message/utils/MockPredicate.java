package common.message.utils;

import java.io.Serializable;
import java.util.function.Predicate;

@FunctionalInterface
public interface MockPredicate<T extends Serializable> extends Predicate, Serializable {

}
