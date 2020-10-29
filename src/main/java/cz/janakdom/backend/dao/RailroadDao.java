package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Railroad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RailroadDao extends JpaRepository<Railroad, Integer> {
    Page<Railroad> findAllByIsDeletedFalse(Pageable pageable);
    Optional<Railroad> findByNumberAndName(String number, String name);
}
