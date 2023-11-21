package com.tlgur.isOpen.controller;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.domain.place.SchoolPlace;
import com.tlgur.isOpen.error.exceptions.NoUsageInAllTypeException;
import com.tlgur.isOpen.repository.PlaceRepository;
import com.tlgur.isOpen.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(controllers = PlaceController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PlaceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlaceController placeController;
    @MockBean
    private PlaceService placeService;
    @MockBean
    private PlaceRepository placeRepository;


    /**
     * input : Campus + PlaceType + selectedUsageInformations(2)
     * expect result :
     */
    @Test
    public void getPlaceCards_FullClassification_Success() throws Exception {
        //given
        Campus campus = Campus.SEOUL;
        PlaceType placeType = PlaceType.SCHOOL_PLACE;
        List<String> usageKeys = Arrays.asList("talkable", "eatable", "hasConsent");
        Map<String, List<String>> usageInformations = new HashMap<>();
        List<List<String>> usageValuesList = Arrays.asList(
                Arrays.asList("SMALL_TALK", "DISCUSSION", "NO"),
                Arrays.asList("WATER_ONLY", "BEVERAGE", "SNACK", "NO"),
                Arrays.asList("TRUE", "FALSE")
        );
        for (int i = 0; i < usageValuesList.size(); i++) {
            usageInformations.put(usageKeys.get(i), usageValuesList.get(i));
        }

        //mocking

        //when
        mockMvc.perform(
                get("/place/cards")
        );

        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getPlaceCards_NoUsageInformation_SearchOnlyWithCampusAndPlaceType() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getPlaceCards_NoPlaceType_SearchOnlyWithCampusAndIgnoreUsageInformation() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input : No Campus, Place
     * expect result :
     */
    @Test
    public void getPlaceCards_ClassificationHasNoPlace_ReturnEmptySliceDTO() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input : No Campus, Place
     * expect result :
     */
    @Test
    public void getPlaceCards_NoCampus_BindException() throws Exception{
        //given

        //mocking

        //when

        //then

    }
}