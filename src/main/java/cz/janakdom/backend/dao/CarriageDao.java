package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Carriage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarriageDao extends JpaRepository<Carriage, Integer> {
    List<Carriage> findAllByIsDeletedFalse();
    Optional<Carriage> findBySerialNumber(String serialNumber);
}
