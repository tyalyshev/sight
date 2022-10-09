package com.yalyshev.sight.repo;

import com.yalyshev.sight.entity.Sight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SightRepo extends JpaRepository<Sight, Integer> {

    @Query(value = "select * from sight order by id limit 60", nativeQuery=true)
    List<Sight> getAll();

    Optional<Sight> findById(Integer id);

    void deleteById(Integer id);

}
