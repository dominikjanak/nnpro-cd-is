package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.DamageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageTypeDao extends JpaRepository<DamageType, Integer> {

}
