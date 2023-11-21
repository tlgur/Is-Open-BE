package com.tlgur.isOpen.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tlgur.isOpen.ProjectConfiguration;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.Eatable;
import com.tlgur.isOpen.domain.enums.Talkable;
import com.tlgur.isOpen.domain.enums.Typeable;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.dto.SchoolPlaceUsageInformationRequest;
import com.tlgur.isOpen.repository.place.SchoolPlaceRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ProjectConfiguration.class)
class SchoolPlaceRepositoryTest {
    @Autowired
    private SchoolPlaceRepository schoolPlaceRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    

    /**
     * input : Campus(서울), TYPE(SILENT_IO), has_consent(false)
     * expect result :
     */
    @Test
    public void findSchoolPlaceCardByUsageInformations_FullClassification_Success() throws Exception{
        //given
        Campus campus = Campus.SEOUL;
        Typeable typeable = Typeable.SILENT_IO;
        Boolean hasConsent = false;
        PageRequest pageRequest = PageRequest.of(0, 2);

        List<PlaceCard> expectPlaceCards = em.createQuery(
                        "select " +
                                "new com.tlgur.isOpen.dto.PlaceCard(" +
                                "    p.id, " +
                                "    p.name, " +
                                "    o.openAt, o.closeAt, " +
                                "    a.loadNameAddress, " +
                                "    i.savedName, i.originalName)" +
                                "from SchoolPlace p " +
                                "left join p.addressInformation a " +
                                "left join p.operatingInformation o " +
                                "left join p.images i " +
                                "where a.campus = :campus " +
                                "and p.typeable = :typeable " +
                                "and p.hasConsent = :hasConsent " +
                                " group by p.id",
                        PlaceCard.class
                )
                .setParameter("campus", campus)
                .setParameter("typeable", typeable)
                .setParameter("hasConsent", hasConsent)
                .setMaxResults(pageRequest.getPageSize() + 1)
                .getResultList();

        //mocking
        
        //when
        Slice<PlaceCard> placeCards = schoolPlaceRepository.findSchoolPlaceCardByUsageInformations(campus,
                new SchoolPlaceUsageInformationRequest(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(typeable),
                        Optional.of(hasConsent)
                ), pageRequest);

        //then
        assertThat(placeCards.getNumberOfElements()).isNotZero();
        assertThat(placeCards.getContent()).doesNotHaveDuplicates();
        assertThat(placeCards.getContent()).doesNotContainNull();
        assertThat(expectPlaceCards).containsAll(placeCards.getContent());
        assertThat(placeCards.hasNext()).isTrue();
        assertThat(placeCards.getContent().size()).isEqualTo(pageRequest.getPageSize())
                .isEqualTo(expectPlaceCards.size() - 1);
        
    }

    /**
     * input : Campus(서울), Talk(NO), Eat(SNACK), TYPE(SILENT_IO), has_consent(false)
     * expect result :
     */
    @Test
    public void findSchoolPlaceCardByUsageInformations_EmptyResult_ReturnEmptySilce() throws Exception{
        //given
        Campus campus = Campus.SEOUL;
        Talkable talkable = Talkable.NO;
        Eatable eatable = Eatable.SNACK;
        Typeable typeable = Typeable.SILENT_IO;
        Boolean hasConsent = false;
        PageRequest pageRequest = PageRequest.of(0, 2);

        List<PlaceCard> expectPlaceCards = em.createQuery(
                        "select " +
                                "new com.tlgur.isOpen.dto.PlaceCard(" +
                                "    p.id, " +
                                "    p.name, " +
                                "    o.openAt, o.closeAt, " +
                                "    a.loadNameAddress, " +
                                "    i.savedName, i.originalName)" +
                                "from SchoolPlace p " +
                                "left join p.addressInformation a " +
                                "left join p.operatingInformation o " +
                                "left join p.images i " +
                                "where a.campus = :campus " +
                                "and p.typeable = :typeable " +
                                "and p.talkable = :talkable " +
                                "and p.eatable = :eatable " +
                                "and p.hasConsent = :hasConsent " +
                                " group by p.id",
                        PlaceCard.class
                )
                .setParameter("campus", campus)
                .setParameter("typeable", typeable)
                .setParameter("talkable", talkable)
                .setParameter("eatable", eatable)
                .setParameter("hasConsent", hasConsent)
                .setMaxResults(pageRequest.getPageSize() + 1)
                .getResultList();

        //mocking

        //when
        Slice<PlaceCard> placeCards = schoolPlaceRepository.findSchoolPlaceCardByUsageInformations(campus,
                new SchoolPlaceUsageInformationRequest(
                        Optional.of(talkable),
                        Optional.of(eatable),
                        Optional.of(typeable),
                        Optional.of(hasConsent)
                ), pageRequest);

        //then
        assertThat(placeCards.getNumberOfElements()).isEqualTo(expectPlaceCards.size())
                .isZero();
        assertThat(placeCards.hasNext()).isFalse();
    }

    /**
     * input : Campus(서울)
     * expect result :
     */
    @Test
    public void findSchoolPlaceCardByUsageInformations_AllEmptyClassification_Success() throws Exception{
        //given
        Campus campus = Campus.SEOUL;
        PageRequest pageRequest = PageRequest.of(0, 2);

        List<PlaceCard> expectPlaceCards = em.createQuery(
                        "select " +
                                "new com.tlgur.isOpen.dto.PlaceCard(" +
                                "    p.id, " +
                                "    p.name, " +
                                "    o.openAt, o.closeAt, " +
                                "    a.loadNameAddress, " +
                                "    i.savedName, i.originalName)" +
                                "from SchoolPlace p " +
                                "left join p.addressInformation a " +
                                "left join p.operatingInformation o " +
                                "left join p.images i " +
                                "where a.campus = :campus " +
                                " group by p.id",
                        PlaceCard.class
                )
                .setParameter("campus", campus)
                .setMaxResults(pageRequest.getPageSize() + 1)
                .getResultList();

        //mocking

        //when
        Slice<PlaceCard> placeCards = schoolPlaceRepository.findSchoolPlaceCardByUsageInformations(campus,
                new SchoolPlaceUsageInformationRequest(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                ), pageRequest);

        //then
        assertThat(placeCards.getNumberOfElements()).isNotZero();
        assertThat(placeCards.getContent()).doesNotHaveDuplicates();
        assertThat(placeCards.getContent()).doesNotContainNull();

        assertThat(expectPlaceCards).containsAll(placeCards.getContent());
        assertThat(placeCards.hasNext()).isTrue();
        assertThat(placeCards.getContent().size()).isEqualTo(pageRequest.getPageSize())
                .isEqualTo(expectPlaceCards.size() - 1);
    }

    /**
     * input : Campus(수원) - delete 127번 image
     * expect result :
     */
    @Test
    public void findSchoolPlaceCardByUsageInformations_NoImage_imageNameIsNull() throws Exception{
        //given
        Campus campus = Campus.SUWON;
        PageRequest pageRequest = PageRequest.of(0, 20);

        List<PlaceCard> expectPlaceCards = em.createQuery(
                        "select " +
                                "new com.tlgur.isOpen.dto.PlaceCard(" +
                                "    p.id, " +
                                "    p.name, " +
                                "    o.openAt, o.closeAt, " +
                                "    a.loadNameAddress, " +
                                "    i.savedName, i.originalName)" +
                                "from SchoolPlace p " +
                                "left join p.addressInformation a " +
                                "left join p.operatingInformation o " +
                                "left join p.images i " +
                                "where a.campus = :campus " +
                                " group by p.id",
                        PlaceCard.class
                )
                .setParameter("campus", campus)
                .setMaxResults(pageRequest.getPageSize() + 1)
                .getResultList();

        //mocking

        //when
        Slice<PlaceCard> placeCards = schoolPlaceRepository.findSchoolPlaceCardByUsageInformations(campus,
                new SchoolPlaceUsageInformationRequest(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                ), pageRequest);

        //then
        assertThat(placeCards.getNumberOfElements()).isNotZero();
        assertThat(placeCards.getContent()).doesNotHaveDuplicates();
        assertThat(placeCards.getContent()).doesNotContainNull();

        assertThat(placeCards.hasNext()).isFalse();
        assertThat(placeCards.getContent().size())
                .isEqualTo(expectPlaceCards.size());

        assertThat(placeCards.getContent().stream().map(PlaceCard::getImgOriginalName).collect(Collectors.toList()))
                .containsNull();
        assertThat(placeCards.getContent().stream().map(PlaceCard::getImgSavedName).collect(Collectors.toList()))
                .containsNull();
    }
}