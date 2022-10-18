package ru.isador.ais.microservices.client.data;

import java.util.Collection;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import io.quarkus.narayana.jta.QuarkusTransaction;

@ApplicationScoped
public class ClientRepository {

    private EntityManager entityManager;

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Client> findByLogin(String login) {
        return entityManager.createQuery("select c from Client c where c.login = :login", Client.class)
                .setParameter("login", login)
                .getResultStream().findAny();
    }

    public Collection<Client> list() {
        return entityManager.createQuery("select c from Client c", Client.class)
                .getResultList();
    }

    @Transactional
    public Client save(Client client) {
        entityManager.persist(client);
        return client;
    }

    @Transactional
    public void save(Collection<Client> clients) {
        clients.forEach(entityManager::persist);
    }

    @Transactional
    public Client update(Client client) {
        return entityManager.merge(client);
    }

    public void removeByLogin(String login) {
        QuarkusTransaction.begin();
        if (1 == entityManager.createQuery("delete from Client c where c.login = :login")
                .setParameter("login", login)
                .executeUpdate()) {
            QuarkusTransaction.commit();
        } else {
            QuarkusTransaction.rollback();
        }
    }
}
