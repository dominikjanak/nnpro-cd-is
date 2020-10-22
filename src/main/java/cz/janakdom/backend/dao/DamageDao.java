package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Damage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageDao extends JpaRepository<Damage, Integer> {

}
