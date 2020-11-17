package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Report;
import cz.janakdom.backend.model.enums.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportDao extends JpaRepository<Report, Integer> {
    Optional<Report> findByHash(String hash);
    List<Report> findAllByTypeOrderByIdDesc(ReportType type);
}
