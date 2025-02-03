package com.corbinelli.giamberini.examManagement.entityManager;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
	
	@Produces
    public EntityManager produceEntityManager() {
        return emf.createEntityManager();
    }
}
