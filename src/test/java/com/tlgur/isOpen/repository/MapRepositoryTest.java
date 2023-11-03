package com.tlgur.isOpen.repository;

import com.tlgur.isOpen.ProjectConfiguration;
import com.tlgur.isOpen.domain.MapInformation;
import com.tlgur.isOpen.domain.Place;
import com.tlgur.isOpen.domain.place.PcCafe;
import com.tlgur.isOpen.dto.CoordinateDTO;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import(ProjectConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MapRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private MapRepository mapRepository;

    private final String placeNamePrefix = "testPlace";

    @Test
    public void getCoordinatesByPlaceIds_쿼리의In절검사_Success() throws Exception{
        //given
        Integer placeIdCount = 5;
        List<Long> givenPlaceIDs = readyPlaceIDs(placeIdCount);

        //when
        List<CoordinateDTO> coordinateDTOs = mapRepository.getCoordinatesByPlaceIds(givenPlaceIDs);

        //then
        //크기 검사
        assertThat(coordinateDTOs).hasSize(placeIdCount);

        //placeId 중복 여부 + null 검사
        List<Long> placeIds = coordinateDTOs.stream().map(CoordinateDTO::getPlaceID).collect(Collectors.toList());
        assertThat(placeIds).doesNotHaveDuplicates();
        assertThat(placeIds).doesNotContainNull();

        //placeName 중복 + null + prefix 포함 여부
        List<String> placeNames = coordinateDTOs.stream().map(CoordinateDTO::getPlaceName).collect(Collectors.toList());
        assertThat(placeNames).doesNotContainNull();
        assertThat(placeNames).doesNotHaveDuplicates();
        for (String placeName : placeNames) {
            assertThat(placeName).contains(placeNamePrefix);
        }

        //place longitude & latitude 검사
        //given : same with id + id * 0.1
        List<Double> placeLatitudes = coordinateDTOs.stream().map(CoordinateDTO::getLatitude).collect(Collectors.toList());
        List<Double> placeLongitudes = coordinateDTOs.stream().map(CoordinateDTO::getLongitude).collect(Collectors.toList());
        assertThat(placeLatitudes).doesNotHaveDuplicates();
        assertThat(placeLongitudes).doesNotHaveDuplicates();

        assertThat(placeLatitudes).doesNotContainNull();
        assertThat(placeLongitudes).doesNotContainNull();
    }

    /**
     * @param totalPlaceIdCount
     *
     * coordinate : id + id * 0.1
     */
    private void savePlaceWithCoordinates(Integer totalPlaceIdCount) {
        for (int i = 1; i <= totalPlaceIdCount; i++) {
            Double coordiantes = i + i * 0.1;
            MapInformation mapInformation = new MapInformation(coordiantes, coordiantes);
            Place place = new PcCafe(placeNamePrefix + i, null, null, null,mapInformation,null, null);
            em.persist(mapInformation);
            em.persist(place);
        }
        em.flush();
        em.clear();
    }

    /**
     * @param placeIdCount
     * @return 검색 대상이 될 place들의 ID 리스트
     * 총 개수뒤에서 부터 원하는 개수
     * 20, 10
     * -> 11, 12, .. 20
     */
    private List<Long> readyPlaceIDs(Integer placeIdCount) {
        return em.createQuery("select p.id from Place p", Long.class)
                .setMaxResults(placeIdCount)
                .getResultList();
    }

}