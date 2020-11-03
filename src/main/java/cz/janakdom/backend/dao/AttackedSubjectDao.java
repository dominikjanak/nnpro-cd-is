package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.AttackedSubject;
import cz.janakdom.backend.model.database.FireBrigadeUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttackedSubjectDao extends JpaRepository<AttackedSubject, Integer> {
    Page<AttackedSubject> findAllByIsDeletedFalse(Pageable pageable);
    Optional<AttackedSubject> findByName(String name);
}
