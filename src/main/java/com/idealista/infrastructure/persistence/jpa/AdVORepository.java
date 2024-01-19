package com.idealista.infrastructure.persistence.jpa;

import com.idealista.infrastructure.persistence.entities.AdVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdVORepository extends JpaRepository<AdVO, Integer> {

    @Query("SELECT a FROM AdVO a WHERE a.score >= 40 ORDER BY a.score DESC")
    List<AdVO> findRelevantAds();

    @Query("SELECT a FROM AdVO a WHERE a.score < 40")
    List<AdVO> findIrrelevantAds();
}
