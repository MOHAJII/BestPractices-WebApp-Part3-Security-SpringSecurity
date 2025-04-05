package net.haji.springmvcthymeleafsecured.dao.repositories;

import net.haji.springmvcthymeleafsecured.dao.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNameContainsIgnoreCase(String keywords);

    @Query("select p from Patient p where p.name like :x")
    List<Patient> search(@Param("x") String keyword);

    Page<Patient> findByNameContainsIgnoreCase(String keyword, Pageable pageable);
}
