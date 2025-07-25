package net.dsa.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dsa.ex.entity.PerfumeEntity;


@Repository
public interface PerfumeRepository extends JpaRepository<PerfumeEntity,Integer> {
    int countByGender(String gender);
}
