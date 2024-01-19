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
    }

    private int calculatePicturesScore(List<Picture> pictures) {
        return pictures.stream()
                .mapToInt(picture -> Quality.HD.equals(picture.getQuality()) ? Constants.TWENTY : Constants.TEN)
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

    private int calculateWordsScore(Typology typology, List<String> words) {
        int score = 0;
        int wordCount = words.size();
        if (Typology.FLAT.equals(typology)) {
            if (wordCount >= Constants.TWENTY && wordCount <= Constants.FORTY_NINE) {
                score += Constants.TEN;
            } else if (wordCount >= Constants.FIFTY) {
                score += Constants.THIRTY;
            }
        } else if (Typology.CHALET.equals(typology) && wordCount >= Constants.FIFTY) {
            score += Constants.TWENTY;
        }
        return score;
    }

    private int calculateKeywordScore(List<String> words) {
        return (int) words.stream()
                .filter(word -> Arrays.asList("luminoso", "nuevo", "céntrico", "reformado", "ático").contains(word))
                .count() * Constants.FIVE;
    }

    private int calculateCompletenessScore(Ad ad) {
        return ad.isComplete() ? Constants.FORTY : 0;
    }

}
