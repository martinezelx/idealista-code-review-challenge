package com.idealista.application;

import com.idealista.infrastructure.rest.dto.PublicAdDTO;
import com.idealista.infrastructure.rest.dto.QualityAdDTO;

import java.util.List;

public interface AdService {

    List<PublicAdDTO> findPublicAds();
    List<QualityAdDTO> findQualityAds();
    void calculateScores();
}
