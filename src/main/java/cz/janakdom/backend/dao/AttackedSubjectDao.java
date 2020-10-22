package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.AttackedSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttackedSubjectDao extends JpaRepository<AttackedSubject, Integer> {

}
