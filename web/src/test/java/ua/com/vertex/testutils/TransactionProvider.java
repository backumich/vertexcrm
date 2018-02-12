package ua.com.vertex.testutils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.testutils.interfaces.TernaryConsumer;

import java.util.function.BiConsumer;

@Repository
@Profile("test")
public class TransactionProvider<T, U, V> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void withPropagationRequiresNewAndException(BiConsumer<T, U> consumer, T t, U u) {
        consumer.accept(t, u);
        throw new RuntimeException(String.format("Exception in test for BiConsumer with parameters: %s %s", t, u));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void withPropagationRequiresNewAndException(TernaryConsumer<T, U, V> consumer, T t, U u, V v) {
        consumer.accept(t, u, v);
        throw new RuntimeException(
                String.format("Exception in test for TernaryConsumer with parameters: %s %s %s", t, u, v));
    }
}
