package com.idealista.application;

import com.idealista.domain.Ad;
import com.idealista.domain.AdRepository;
import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.mappers.AdToPublicAdMapper;
import com.idealista.infrastructure.mappers.AdToQualityAdMapper;
import com.idealista.utils.TestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private AdToPublicAdMapper adToPublicAdMapper;

    @Mock
    private AdToQualityAdMapper adToQualityAdMapper;

    @InjectMocks
    private AdServiceImpl adService;

    @Test
    public void findPublicAdsTest() {
        PublicAd publicAd = TestBuilder.buildPublicAd();
        Ad ad = TestBuilder.buildAd();
        when(adRepository.findRelevantAds()).thenReturn(Collections.singletonList(ad));
        when(adToPublicAdMapper.adToPublicAd(ad)).thenReturn(publicAd);

        List<PublicAd> result = adService.findPublicAds();

        assertEquals(1, result.size());
        assertEquals(publicAd, result.get(0));
        verify(adRepository).findRelevantAds();
    }

    @Test
    public void findQualityAdsTest() {
        QualityAd qualityAd = TestBuilder.buildQualityAd();
        Ad ad = TestBuilder.buildAd();
        when(adRepository.findIrrelevantAds()).thenReturn(Collections.singletonList(ad));
        when(adToQualityAdMapper.adToQualityAd(ad)).thenReturn(qualityAd);

        List<QualityAd> result = adService.findQualityAds();

        assertEquals(1, result.size());
        assertEquals(qualityAd, result.get(0));
        verify(adRepository).findIrrelevantAds();
    }

    @Test
    public void calculateScoresTest() {
        when(adRepository.findAllAds()).thenReturn(Arrays.asList(TestBuilder.buildAd(), TestBuilder.buildAd()));
        adService.calculateScores();
        verify(adRepository).findAllAds();
        verify(adRepository, times(2)).save(any());
    }
}