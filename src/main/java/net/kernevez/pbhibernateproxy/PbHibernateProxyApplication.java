package net.kernevez.pbhibernateproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PbHibernateProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PbHibernateProxyApplication.class, args);
	}

}
