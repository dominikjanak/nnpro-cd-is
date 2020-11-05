package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Railroad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RailroadDao extends JpaRepository<Railroad, Integer> {
    List<Railroad> findAllByIsDeletedFalse();
    Optional<Railroad> findByNumberAndName(String number, String name);
}
