package com.idealista.infrastructure.persistence.repositories;

import com.idealista.domain.Ad;
import com.idealista.infrastructure.mappers.AdMapper;
import com.idealista.infrastructure.persistence.entities.AdVO;
import com.idealista.infrastructure.persistence.jpa.AdVORepository;
import com.idealista.utils.TestBuilder;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AdRepositoryImplIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdVORepository adVORepository;

    private final AdMapper adMapper = Mappers.getMapper(AdMapper.class);

    @Test
    public void findAllAdsTest() {
        Ad ad = TestBuilder.buildFlatAd();
        entityManager.persist(adMapper.toPersistence(ad));
        entityManager.flush();

        List<AdVO> result = adVORepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(adMapper.toPersistence(ad));
    }

    @Test
    public void saveTest() {
        Ad ad = TestBuilder.buildChaletAd();
        adVORepository.save(adMapper.toPersistence(ad));

        AdVO savedAd = adVORepository.findById(ad.getId()).orElse(null);
        assertThat(savedAd).isNotNull();
        assertThat(savedAd).usingRecursiveComparison().isEqualTo(adMapper.toPersistence(ad));
    }

    @Test
    public void findRelevantAdsTest() {
        Ad ad = TestBuilder.buildGarageAd();;
        ad.setScore(50);
        entityManager.persist(adMapper.toPersistence(ad));
        entityManager.flush();

        List<AdVO> result = adVORepository.findRelevantAds();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(adMapper.toPersistence(ad));
    }

    @Test
    public void findIrrelevantAdsTest() {
        Ad ad = TestBuilder.buildFlatAd();
        ad.setScore(30);
        entityManager.persist(adMapper.toPersistence(ad));
        entityManager.flush();

        List<AdVO> result = adVORepository.findIrrelevantAds();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(adMapper.toPersistence(ad));
    }
}