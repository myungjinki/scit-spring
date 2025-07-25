package net.dsa.ex4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dsa.ex4.entity.ChickenEntity;

@Repository
public interface ChickenRepository extends JpaRepository<ChickenEntity, Integer> {

}
