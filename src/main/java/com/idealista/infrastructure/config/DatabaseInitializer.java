package com.idealista.infrastructure.config;

import com.idealista.domain.Ad;
import com.idealista.domain.Picture;
import com.idealista.infrastructure.persistence.entities.AdVO;
import com.idealista.infrastructure.persistence.entities.PictureVO;
import com.idealista.infrastructure.persistence.repositories.AdVORepository;
import com.idealista.infrastructure.persistence.repositories.PictureVORepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(AdVORepository adVORepository, PictureVORepository pictureVORepository) {
        return args -> {
            List<PictureVO> pictures = pictureVORepository.saveAll(Arrays.asList(
                    new PictureVO(UUID.randomUUID(), "http://www.idealista.com/pictures/1", Picture.Quality.SD.name(), null),
                    new PictureVO(UUID.randomUUID(), "http://www.idealista.com/pictures/2", Picture.Quality.HD.name(), null),
                    new PictureVO(UUID.randomUUID(), "http://www.idealista.com/pictures/3", Picture.Quality.SD.name(), null),
                    new PictureVO(UUID.randomUUID(), "http://www.idealista.com/pictures/4", Picture.Quality.HD.name(), null),
                    new PictureVO(UUID.randomUUID(), "http://www.idealista.com/pictures/5", Picture.Quality.SD.name(), null),
                    new PictureVO(UUID.randomUUID(), "http://www.idealista.com/pictures/6", Picture.Quality.SD.name(), null),
                    new PictureVO(UUID.randomUUID(), "http://www.idealista.com/pictures/7", Picture.Quality.SD.name(), null),
                    new PictureVO(UUID.randomUUID(), "http://www.idealista.com/pictures/8", Picture.Quality.HD.name(), null)
            ));

            UUID pic1Id = pictures.get(0).getId();
            UUID pic2Id = pictures.get(1).getId();
            UUID pic3Id = pictures.get(2).getId();
            UUID pic4Id = pictures.get(3).getId();
            UUID pic5Id = pictures.get(4).getId();
            UUID pic6Id = pictures.get(5).getId();
            UUID pic7Id = pictures.get(6).getId();
            UUID pic8Id = pictures.get(7).getId();

            adVORepository.saveAll(Arrays.asList(
                    new AdVO(UUID.randomUUID(), Ad.Typology.CHALET.name(), "Este piso es una ganga, compra, compra, COMPRA!!!!!", findPictures(pictures, Collections.emptyList()), 300, null, null, null),
                    new AdVO(UUID.randomUUID(), Ad.Typology.FLAT.name(), "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", findPictures(pictures, List.of(pic4Id)), 300, null, null, null),
                    new AdVO(UUID.randomUUID(), Ad.Typology.CHALET.name(), "", findPictures(pictures, List.of(pic2Id)), 300, null, null, null),
                    new AdVO(UUID.randomUUID(), Ad.Typology.FLAT.name(), "Ático céntrico muy luminoso y recién reformado, parece nuevo", findPictures(pictures, List.of(pic5Id)), 300, null, null, null),
                    new AdVO(UUID.randomUUID(), Ad.Typology.FLAT.name(), "Pisazo,", findPictures(pictures, Arrays.asList(pic3Id, pic8Id)), 300, null, null, null),
                    new AdVO(UUID.randomUUID(), Ad.Typology.GARAGE.name(), "", findPictures(pictures, List.of(pic6Id)), 300, null, null, null),
                    new AdVO(UUID.randomUUID(), Ad.Typology.GARAGE.name(), "Garaje en el centro de Albacete", findPictures(pictures, Collections.emptyList()), 300, null, null, null),
                    new AdVO(UUID.randomUUID(), Ad.Typology.CHALET.name(), "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", findPictures(pictures, Arrays.asList(pic1Id, pic7Id)), 300, null, null, null)
            ));
        };
    }

    private List<PictureVO> findPictures(List<PictureVO> pictures, List<UUID> ids) {
        return pictures.stream()
                .filter(picture -> ids.contains(picture.getId()))
                .collect(Collectors.toList());
    }
}
