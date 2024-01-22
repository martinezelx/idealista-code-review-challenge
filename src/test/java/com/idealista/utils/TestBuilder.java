package com.idealista.utils;

import com.idealista.domain.Ad;
import com.idealista.domain.Picture;
import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;

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

    public static PublicAd buildPublicAd() {
        PublicAd publicAd = new PublicAd();
        publicAd.setId(UUID.randomUUID());
        publicAd.setTypology("FLAT");
        publicAd.setDescription("This is a test ad");
        publicAd.setPictureUrls(List.of("http://example.com/picture1.jpg"));
        publicAd.setHouseSize(100);
        publicAd.setGardenSize(50);
        return publicAd;
    }

    public static QualityAd buildQualityAd() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setId(UUID.randomUUID());
        qualityAd.setTypology("FLAT");
        qualityAd.setDescription("This is a test ad");
        qualityAd.setPictureUrls(List.of("http://example.com/picture1.jpg"));
        qualityAd.setHouseSize(100);
        qualityAd.setGardenSize(50);
        qualityAd.setScore(50);
        qualityAd.setIrrelevantSince(new Date());
        return qualityAd;
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