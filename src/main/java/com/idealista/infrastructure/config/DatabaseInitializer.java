package com.idealista.infrastructure.config;

import com.idealista.infrastructure.persistence.entities.AdVO;
import com.idealista.infrastructure.persistence.entities.PictureVO;
import com.idealista.infrastructure.persistence.jpa.AdVORepository;
import com.idealista.infrastructure.persistence.jpa.PictureVORepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(AdVORepository adVORepository, PictureVORepository pictureVORepository) {
        return args -> {
            List<PictureVO> pictures = pictureVORepository.saveAll(Arrays.asList(
                    new PictureVO(1, "http://www.idealista.com/pictures/1", "SD", null),
                    new PictureVO(2, "http://www.idealista.com/pictures/2", "HD", null),
                    new PictureVO(3, "http://www.idealista.com/pictures/3", "SD", null),
                    new PictureVO(4, "http://www.idealista.com/pictures/4", "HD", null),
                    new PictureVO(5, "http://www.idealista.com/pictures/5", "SD", null),
                    new PictureVO(6, "http://www.idealista.com/pictures/6", "SD", null),
                    new PictureVO(7, "http://www.idealista.com/pictures/7", "SD", null),
                    new PictureVO(8, "http://www.idealista.com/pictures/8", "HD", null)
            ));

            adVORepository.saveAll(Arrays.asList(
                    new AdVO(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", findPictures(pictures, Collections.emptyList()), 300, null, null, null),
                    new AdVO(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", findPictures(pictures, List.of(4)), 300, null, null, null),
                    new AdVO(3, "CHALET", "", findPictures(pictures, List.of(2)), 300, null, null, null),
                    new AdVO(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", findPictures(pictures, List.of(5)), 300, null, null, null),
                    new AdVO(5, "FLAT", "Pisazo,", findPictures(pictures, Arrays.asList(3, 8)), 300, null, null, null),
                    new AdVO(6, "GARAGE", "", findPictures(pictures, List.of(6)), 300, null, null, null),
                    new AdVO(7, "GARAGE", "Garaje en el centro de Albacete", findPictures(pictures, Collections.emptyList()), 300, null, null, null),
                    new AdVO(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", findPictures(pictures, Arrays.asList(1, 7)), 300, null, null, null)
            ));
        };
    }

    private List<PictureVO> findPictures(List<PictureVO> pictures, List<Integer> ids) {
        return pictures.stream()
                .filter(picture -> ids.contains(picture.getId()))
                .collect(Collectors.toList());
    }
}
