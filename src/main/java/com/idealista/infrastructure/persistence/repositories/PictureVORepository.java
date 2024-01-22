package com.idealista.infrastructure.persistence.repositories;

import com.idealista.infrastructure.persistence.entities.PictureVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureVORepository extends JpaRepository<PictureVO, Integer> {
}
