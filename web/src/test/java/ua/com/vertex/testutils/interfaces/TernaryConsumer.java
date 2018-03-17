package ua.com.vertex.testutils.interfaces;

@FunctionalInterface
public interface TernaryConsumer<T, U, V> {
    void accept(T t, U u, V v);
}
