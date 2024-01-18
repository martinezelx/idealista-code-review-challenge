package com.idealista.infrastructure.persistence.repositories;

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
        return adVORepository.findAll().stream()
                .map(adMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Ad ad) {
        adVORepository.save(adMapper.toPersistence(ad));
    }

    @Override
    public List<Ad> findRelevantAds() {
        return adVORepository.findRelevantAds().stream()
                .map(adMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ad> findIrrelevantAds() {
        return adVORepository.findIrrelevantAds().stream()
                .map(adMapper::toDomain)
                .collect(Collectors.toList());
    }
}