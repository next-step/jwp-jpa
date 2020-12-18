package jpa.domain.bridgeObject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineUseBridgeRepository extends JpaRepository<LineUseBridge, Long> {
    Optional<LineUseBridge> findByName(String name);
}
