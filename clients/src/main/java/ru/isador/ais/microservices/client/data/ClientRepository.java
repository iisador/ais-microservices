package ru.isador.ais.microservices.client.data;

import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByLogin(String login);

    void deleteByLogin(String login);
}
