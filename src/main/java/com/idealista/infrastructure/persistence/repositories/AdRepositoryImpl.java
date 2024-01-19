package com.idealista.infrastructure.persistence.repositories;

import com.idealista.application.exceptions.AdRepositoryAccessException;
import com.idealista.domain.Ad;
import com.idealista.domain.AdRepository;
import com.idealista.infrastructure.persistence.jpa.AdVORepository;
import com.idealista.infrastructure.mappers.AdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AdRepositoryImpl implements AdRepository {

    @Autowired
    private AdVORepository adVORepository;

    @Autowired
    private AdMapper adMapper;

    @Override
    public List<Ad> findAllAds() {
        try {
            return adVORepository.findAll().stream()
                    .map(adMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new AdRepositoryAccessException("Error occurred while fetching all ads", e);
        }
    }

    @Override
    public void save(Ad ad) {
        try {
            adVORepository.save(adMapper.toPersistence(ad));
        } catch (Exception e) {
            throw new AdRepositoryAccessException("Error occurred while saving ad", e);
        }
    }

    @Override
    public List<Ad> findRelevantAds() {
        try {
            return adVORepository.findRelevantAds().stream()
                    .map(adMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new AdRepositoryAccessException("Error occurred while fetching relevant ads", e);
        }
    }

    @Override
    public List<Ad> findIrrelevantAds() {
        try {
            return adVORepository.findIrrelevantAds().stream()
                    .map(adMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new AdRepositoryAccessException("Error occurred while fetching irrelevant ads", e);
        }
    }
}