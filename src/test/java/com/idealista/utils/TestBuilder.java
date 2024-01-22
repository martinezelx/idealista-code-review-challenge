package com.idealista.utils;

import com.idealista.domain.Ad;
import com.idealista.domain.Picture;
import com.idealista.infrastructure.rest.dto.PublicAdDTO;
import com.idealista.infrastructure.rest.dto.QualityAdDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestBuilder {

    public static Ad buildAd() {
        Ad ad = new Ad();
        ad.setId(UUID.randomUUID());
        ad.setTypology(Ad.Typology.FLAT);
        ad.setDescription("This is a test ad");
        ad.setPictures(List.of(buildPicture()));
        ad.setHouseSize(100);
        ad.setGardenSize(50);
        ad.setScore(50);
        ad.setIrrelevantSince(null);
        return ad;
    }

    public static PublicAdDTO buildPublicAd() {
        PublicAdDTO publicAdDTO = new PublicAdDTO();
        publicAdDTO.setId(UUID.randomUUID());
        publicAdDTO.setTypology("FLAT");
        publicAdDTO.setDescription("This is a test ad");
        publicAdDTO.setPictureUrls(List.of("http://example.com/picture1.jpg"));
        publicAdDTO.setHouseSize(100);
        publicAdDTO.setGardenSize(50);
        return publicAdDTO;
    }

    public static QualityAdDTO buildQualityAd() {
        QualityAdDTO qualityAdDTO = new QualityAdDTO();
        qualityAdDTO.setId(UUID.randomUUID());
        qualityAdDTO.setTypology("FLAT");
        qualityAdDTO.setDescription("This is a test ad");
        qualityAdDTO.setPictureUrls(List.of("http://example.com/picture1.jpg"));
        qualityAdDTO.setHouseSize(100);
        qualityAdDTO.setGardenSize(50);
        qualityAdDTO.setScore(50);
        qualityAdDTO.setIrrelevantSince(new Date());
        return qualityAdDTO;
    }

    public static Ad buildFlatAd() {
        Ad ad = new Ad();
        ad.setId(UUID.randomUUID());
        ad.setTypology(Ad.Typology.FLAT);
        ad.setDescription("This is a test flat ad");
        ad.setPictures(List.of(buildPicture()));
        ad.setHouseSize(100);
        ad.setGardenSize(50);
        ad.setScore(50);
        ad.setIrrelevantSince(null);
        return ad;
    }

    public static Ad buildChaletAd() {
        Ad ad = new Ad();
        ad.setId(UUID.randomUUID());
        ad.setTypology(Ad.Typology.CHALET);
        ad.setDescription("This is a test chalet ad");
        ad.setPictures(List.of(buildPicture()));
        ad.setHouseSize(200);
        ad.setGardenSize(100);
        ad.setScore(60);
        ad.setIrrelevantSince(null);
        return ad;
    }

    public static Ad buildGarageAd() {
        Ad ad = new Ad();
        ad.setId(UUID.randomUUID());
        ad.setTypology(Ad.Typology.GARAGE);
        ad.setDescription("This is a test garage ad");
        ad.setPictures(List.of(buildPicture()));
        ad.setHouseSize(50);
        ad.setGardenSize(null);
        ad.setScore(40);
        ad.setIrrelevantSince(null);
        return ad;
    }

    private static Picture buildPicture() {
        Picture picture = new Picture();
        picture.setId(UUID.randomUUID());
        picture.setUrl("http://example.com/picture1.jpg");
        picture.setQuality(Picture.Quality.HD);
        return picture;
    }
}