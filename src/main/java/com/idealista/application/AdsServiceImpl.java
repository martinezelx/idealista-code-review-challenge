package com.idealista.application;

import com.idealista.domain.*;
import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.mappers.AdToPublicAdMapper;
import com.idealista.infrastructure.mappers.AdToQualityAdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdToPublicAdMapper adToPublicAdMapper;

    @Autowired
    private AdToQualityAdMapper adToQualityAdMapper;

    @Override
    public List<PublicAd> findPublicAds() {
        return adRepository.findRelevantAds().stream()
                .map(adToPublicAdMapper::adToPublicAd)
                .collect(Collectors.toList());
    }

    @Override
    public List<QualityAd> findQualityAds() {
        return adRepository.findIrrelevantAds().stream()
                .map(adToQualityAdMapper::adToQualityAd)
                .collect(Collectors.toList());
    }

    @Override
    public void calculateScores() {
        adRepository
                .findAllAds()
                .forEach(this::calculateScore);
    }

    private void calculateScore(Ad ad) {
        int score = Constants.ZERO;

        //Calcular puntuación por fotos
        if (ad.getPictures().isEmpty()) {
            score -= Constants.TEN; //Si no hay fotos restamos 10 puntos
        } else {
            for (Picture picture : ad.getPictures()) {
                if (Quality.HD.equals(picture.getQuality())) {
                    score += Constants.TWENTY; //Cada foto en alta definición aporta 20 puntos
                } else {
                    score += Constants.TEN; //Cada foto normal aporta 10 puntos
                }
            }
        }

        //Calcular puntuación por descripción
        Optional<String> optDesc = Optional.ofNullable(ad.getDescription());

        if (optDesc.isPresent()) {
            String description = optDesc.get();

            if (!description.isEmpty()) {
                score += Constants.FIVE;
            }

            List<String> wds = Arrays.asList(description.split(" ")); //número de palabras
            if (Typology.FLAT.equals(ad.getTypology())) {
                if (wds.size() >= Constants.TWENTY && wds.size() <= Constants.FORTY_NINE) {
                    score += Constants.TEN;
                }

                if (wds.size() >= Constants.FIFTY) {
                    score += Constants.THIRTY;
                }
            }

            if (Typology.CHALET.equals(ad.getTypology())) {
                if (wds.size() >= Constants.FIFTY) {
                    score += Constants.TWENTY;
                }
            }

            if (wds.contains("luminoso")) score += Constants.FIVE;
            if (wds.contains("nuevo")) score += Constants.FIVE;
            if (wds.contains("céntrico")) score += Constants.FIVE;
            if (wds.contains("reformado")) score += Constants.FIVE;
            if (wds.contains("ático")) score += Constants.FIVE;
        }

        //Calcular puntuación por completitud
        if (ad.isComplete()) {
            score = Constants.FORTY;
        }

        ad.setScore(score);

        if (ad.getScore() < Constants.ZERO) {
            ad.setScore(Constants.ZERO);
        }

        if (ad.getScore() > Constants.ONE_HUNDRED) {
            ad.setScore(Constants.ONE_HUNDRED);
        }

        if (ad.getScore() < Constants.FORTY) {
            ad.setIrrelevantSince(new Date());
        } else {
            ad.setIrrelevantSince(null);
        }

        adRepository.save(ad);
    }


}
