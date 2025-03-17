package com.corbinelli.giamberini.examManagement.entityManager;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {
	
	private static EntityManagerFactory emf;

	@Produces
	@RequestScoped
	public EntityManager produceEntityManager() {
		if(emf == null || !emf.isOpen()) {
			 emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
		}
		return emf.createEntityManager();
	}

	public void closeEntityManager(@Disposes EntityManager em) {
		if (em.isOpen()) {
			em.close(); // Closes EntityManager after each request
		}
	}

	@PreDestroy
	public void closeEntityManagerFactory() {
		if (emf.isOpen()) {
			emf.close(); // Closes EntityManagerFactory when the app shuts down
		}
	}
}
