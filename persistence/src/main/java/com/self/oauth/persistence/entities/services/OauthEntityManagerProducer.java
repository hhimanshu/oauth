package com.self.oauth.persistence.entities.services;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class OauthEntityManagerProducer {
	@PersistenceContext(unitName = "oauth")
	private EntityManager entityManager;

	@Produces
	@OauthEntityManager
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
