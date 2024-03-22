package net.kernevez.pbhibernateproxy;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.kernevez.pbhibernateproxy.TsidUtils.randomTsid;

public class GeneratorTest {
    @Test
    void generate() {
        Logger logger = LoggerFactory.getLogger(PbHibernateProxyApplication.class);
        for (int i = 0; i < 10; i++) {
            logger.info("Id={}", randomTsid().toLong());
        }
    }
}
