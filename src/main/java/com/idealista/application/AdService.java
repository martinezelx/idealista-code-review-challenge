package com.idealista.application;

import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;

import java.util.List;

public interface AdService {

    List<PublicAd> findPublicAds();
    List<QualityAd> findQualityAds();
    void calculateScores();
}