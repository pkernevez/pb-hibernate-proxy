package net.kernevez.pbhibernateproxy;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.kernevez.pbhibernateproxy.entities.BSCategoryEntity;
import net.kernevez.pbhibernateproxy.entities.BSCategoryRepository;
import org.hibernate.engine.spi.ActionQueue;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection",})
@SpringBootTest
@Transactional
class PbLoadingTest {
    @Autowired
    private BSCategoryRepository bsCategoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void verySimpleCase() {
        // Given
        /*
         * Create a link between 2 entities of same class
         *  root
         *   |-> child1
         */
        BSCategoryEntity root = new BSCategoryEntity(0L).setName("root");
        BSCategoryEntity child1 = bsCategoryRepository.save(new BSCategoryEntity(1L).setName("child1"));
        child1 = bsCategoryRepository.save(child1);
        root = bsCategoryRepository.save(root);
        root.addChild(child1);

        // When
        try (var asserter = LoggerTestUtil.createTestAppender(ActionQueue.class, Level.INFO)) {
            entityManager.flush();
            // Then...
            // we have warning in the logs
            asserter.assertLogEquals(Level.WARN,
                                     "The batch containing 2 statements could not be sorted. This might indicate a circular entity relationship.");
        }
    }

    @Test
    void noErrorDuringSavingWithoutBS() {
        // Given
        /*
         * Create an Oriented Graph
         *  root
         *   |-> child1
         *   |   |-> child1_1
         *   |   |-> child1_2
         *   |-> child2
         *       |-> child2_1
         *       |-> child2_2s
         */
        BSCategoryEntity root = createOrientedGraph();
        bsCategoryRepository.save(root);

        // When
        try (var asserter = LoggerTestUtil.createTestAppender(ActionQueue.class, Level.INFO)) {
            entityManager.flush();
            // Then...
            // we have warning in the logs
            asserter.assertLogEquals(Level.WARN,
                                     "The batch containing 7 statements could not be sorted. This might indicate a circular entity relationship.");
        }
    }

    @SuppressWarnings({"java:S117", "NamingConvention"})
    private static @NotNull BSCategoryEntity createOrientedGraph() {
        BSCategoryEntity root = new BSCategoryEntity(1L).setName("root");
        BSCategoryEntity child1 = new BSCategoryEntity(2L).setName("child1");
        root.addChild(child1);
        BSCategoryEntity child1_1 = new BSCategoryEntity(3L).setName("child1_1");
        child1.addChild(child1_1);
        BSCategoryEntity child1_2 = new BSCategoryEntity(4L).setName("child1_2");
        child1.addChild(child1_2);

        BSCategoryEntity child2 = new BSCategoryEntity(5L).setName("child2");
        root.addChild(child2);
        BSCategoryEntity child2_1 = new BSCategoryEntity(6L).setName("child2_1");
        child1.addChild(child2_1);
        BSCategoryEntity child2_2 = new BSCategoryEntity(7L).setName("child2_2");
        child1.addChild(child2_2);
        return root;
    }
}
