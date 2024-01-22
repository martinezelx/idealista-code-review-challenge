package com.idealista.application;

import com.idealista.application.exceptions.AdScoreCalculationException;
import com.idealista.domain.Ad;
import com.idealista.domain.AdRepository;
import com.idealista.domain.Constants;
import com.idealista.domain.Picture;
import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.mappers.AdToPublicAdMapper;
import com.idealista.infrastructure.mappers.AdToQualityAdMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdToPublicAdMapper adToPublicAdMapper;

    @Autowired
    private AdToQualityAdMapper adToQualityAdMapper;

    @Override
    public List<PublicAd> findPublicAds() {
        log.info("Finding public ads");
        List<PublicAd> publicAds = adRepository.findRelevantAds().stream()
                .map(adToPublicAdMapper::adToPublicAd)
                .collect(Collectors.toList());
        log.info("Found {} public ads", publicAds.size());
        return publicAds;
    }

    @Override
    public List<QualityAd> findQualityAds() {
        log.info("Finding quality ads");
        List<QualityAd> qualityAds = adRepository.findIrrelevantAds().stream()
                .map(adToQualityAdMapper::adToQualityAd)
                .collect(Collectors.toList());
        log.info("Found {} quality ads", qualityAds.size());
        return qualityAds;
    }

    @Override
    public void calculateScores() {
        log.info("Calculating scores for all ads");
        adRepository
                .findAllAds()
                .forEach(this::calculateScore);
        log.info("Finished calculating scores for all ads");
    }

    private void calculateScore(Ad ad) {
        try {
            log.info("Calculating score for ad with id: {}", ad.getId());
            int score = calculatePicturesScore(ad.getPictures());
            score += calculateDescriptionScore(ad);
            score += calculateCompletenessScore(ad);

            // The score is limited to 0 and 100
            ad.setScore(Math.min(Math.max(score, Constants.ZERO), Constants.ONE_HUNDRED));

            if (ad.getScore() < Constants.FORTY) {
                ad.setIrrelevantSince(new Date());
            } else {
                ad.setIrrelevantSince(null);
            }

            adRepository.save(ad);
            log.info("Successfully calculated and saved score for ad with id: {}", ad.getId());
        } catch (Exception e) {
            log.error("Error calculating score for ad with id: {}", ad.getId(), e);
            throw new AdScoreCalculationException("Error calculating score for ad with id: " + ad.getId());
        }
    }

    private int calculatePicturesScore(List<Picture> pictures) {
        return pictures.stream()
                .mapToInt(picture -> Picture.Quality.HD.equals(picture.getQuality()) ? Constants.TWENTY : Constants.TEN)
                .sum() - (pictures.isEmpty() ? Constants.TEN : 0);
    }

    private int calculateDescriptionScore(Ad ad) {
        return Optional.ofNullable(ad.getDescription())
                .map(description -> {
                    int score = description.isEmpty() ? 0 : Constants.FIVE;
                    List<String> words = Arrays.asList(description.split(" "));
                    score += calculateWordsScore(ad.getTypology(), words);
                    score += calculateKeywordScore(words);
                    return score;
                })
                .orElse(0);
    }

    private int calculateWordsScore(Ad.Typology typology, List<String> words) {
        int score = 0;
        int wordCount = words.size();
        if (Ad.Typology.FLAT.equals(typology)) {
            if (wordCount >= Constants.TWENTY && wordCount <= Constants.FORTY_NINE) {
                score += Constants.TEN;
            } else if (wordCount >= Constants.FIFTY) {
                score += Constants.THIRTY;
            }
        } else if (Ad.Typology.CHALET.equals(typology) && wordCount >= Constants.FIFTY) {
            score += Constants.TWENTY;
        }
        return score;
    }

    private int calculateKeywordScore(List<String> words) {
        return (int) words.stream()
                .filter(Constants.KEYWORDS::contains)
                .count() * Constants.FIVE;
    }

    private int calculateCompletenessScore(Ad ad) {
        return ad.isComplete() ? Constants.FORTY : 0;
    }
}
