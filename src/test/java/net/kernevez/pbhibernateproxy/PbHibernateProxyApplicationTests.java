package net.kernevez.pbhibernateproxy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import net.kernevez.pbhibernateproxy.entities.InstrumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
@SpringBootTest(classes = PbHibernateProxyApplication.class)
@Transactional
class PbHibernateProxyApplicationTests {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    InstrumentRepository instrumentRepository;
//    @Autowired
//    AmountRepository sut;

	@Test
	void contextLoads() {
	}

}
