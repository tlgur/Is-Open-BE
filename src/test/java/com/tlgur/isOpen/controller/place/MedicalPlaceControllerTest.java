package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.controller.ControllerTestUtils;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.MedicalType;
import com.tlgur.isOpen.domain.place.MedicalPlace;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.repository.place.MedicalPlaceRepository;
import com.tlgur.isOpen.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.tlgur.isOpen.controller.ControllerTestUtils.*;
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
@WebMvcTest(controllers = MedicalPlaceController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MedicalPlaceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MedicalPlaceController medicalPlaceController;
    @MockBean
    private MedicalPlaceRepository medicalPlaceRepository;
    @MockBean
    private PlaceService placeService;

    /**
     * expect result : 세부 분류들 이름 + 총 개수 리턴
     */
    @Test
    public void getUsageInformation_Default_ReturnAppropriateFields() throws Exception {
        //given
        Map<String, List<String>> expectUsageInfosContainer = getInternalFieldNameAndValuesByClassType(MedicalPlace.class);

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/usages/medical")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1));

        MedicalType[] medicalTypes = MedicalType.values();
        for (int i = 0; i < medicalTypes.length; i++) {
            actions.andExpect(jsonPath("$.medicalType[" + i + "]").value(medicalTypes[i].toString()));
        }

        actions.andDo(
                document(
                        "/place/get/usages/medical",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("usage Information 분류 개수").attributes(example("1 - 변동 시 항목 추가")),
                                fieldWithPath("medicalType").type(JsonFieldType.ARRAY).description("usage Information : MedicalType").attributes(example(LINK_MEDICAL_PlACE_TYPE))
                        )
                )
        );
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getPlaceCardsOfMart() throws Exception{
        //given
        Campus campus = Campus.SEOUL;
        PageRequest pageRequest = PageRequest.of(0, 5);
        MedicalType medicalType = MedicalType.약국;
        Slice<PlaceCard> placeCards = readyPlaceCards(pageRequest.getPageSize(), pageRequest);

        //mocking
        given(medicalPlaceRepository.findMedicalPlaceCardsByUsageInformations(campus, Optional.of(medicalType), pageRequest)).willReturn(placeCards);

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/cards/medical")
                        .queryParam("campus", campus.toString())
                        .queryParam("medicalType", medicalType.name())
                        .queryParam("size", String.valueOf(pageRequest.getPageSize()))
                        .queryParam("page", String.valueOf(pageRequest.getPageNumber()))
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext").value(true))
                .andExpect(jsonPath("$.size").value(pageRequest.getPageSize()))
                .andExpect(jsonPath("$.number").value(pageRequest.getPageNumber()));
        for(int i = 0; i < pageRequest.getPageSize(); i++) {
            PlaceCard placeCard = placeCards.getContent().get(i);
            actions
                    .andExpect(jsonPath("$.content["+ i + "].placeID").value(placeCard.getPlaceID()))
                    .andExpect(jsonPath("$.content["+ i + "].name").value(placeCard.getName()))
                    .andExpect(jsonPath("$.content["+ i + "].openAt").value(placeCard.getOpenAt().truncatedTo(ChronoUnit.MINUTES).toString()))
                    .andExpect(jsonPath("$.content["+ i + "].closeAt").value(placeCard.getCloseAt().truncatedTo(ChronoUnit.MINUTES).toString()))
                    .andExpect(jsonPath("$.content["+ i + "].loadNameAddress").value(placeCard.getLoadNameAddress()))
                    .andExpect(jsonPath("$.content["+ i + "].imgSavedName").value(placeCard.getImgSavedName()))
                    .andExpect(jsonPath("$.content["+ i + "].imgOriginalName").value(placeCard.getImgOriginalName()));
        }

        List<FieldDescriptor> pageableResponseFields = new ArrayList<>();
        PlaceCard examplePlaceCard = placeCards.getContent().get(0);
        pageableResponseFields.add(fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("인피니티 스크롤 - 다음 페이지 존재 여부").attributes(example("true or false")));
        pageableResponseFields.add(fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 크기").attributes(example(String.valueOf(pageRequest.getPageSize()))));
        pageableResponseFields.add(fieldWithPath("number").type(JsonFieldType.NUMBER).description("현재 페이지 번호").attributes(example(String.valueOf(pageRequest.getPageNumber()))));
        pageableResponseFields.add(fieldWithPath("content[].placeID").type(JsonFieldType.NUMBER).description("장소 아이디").attributes(example(examplePlaceCard.getPlaceID().toString())));
        pageableResponseFields.add(fieldWithPath("content[].name").type(JsonFieldType.STRING).description("장소 이름").attributes(example(examplePlaceCard.getName())));
        pageableResponseFields.add(fieldWithPath("content[].openAt").type(JsonFieldType.STRING).description("오픈시간").attributes(example(examplePlaceCard.getOpenAt().truncatedTo(ChronoUnit.MINUTES).toString())));
        pageableResponseFields.add(fieldWithPath("content[].closeAt").type(JsonFieldType.STRING).description("마감시간").attributes(example(examplePlaceCard.getCloseAt().truncatedTo(ChronoUnit.MINUTES).toString())));
        pageableResponseFields.add(fieldWithPath("content[].loadNameAddress").type(JsonFieldType.STRING).description("도로명 주소").attributes(example(examplePlaceCard.getLoadNameAddress())));
        pageableResponseFields.add(fieldWithPath("content[].imgSavedName").type(JsonFieldType.STRING).description("대표 이미지 - 저장명").attributes(example(examplePlaceCard.getImgSavedName())));
        pageableResponseFields.add(fieldWithPath("content[].imgOriginalName").type(JsonFieldType.STRING).description("대표 이미지 - 원본명").attributes(example(examplePlaceCard.getImgOriginalName())));

        actions.andDo(
                document("place/get/cards/medical",
                        queryParameters(
                                parameterWithName("campus").description("분류 - 캠퍼스").attributes(example(LINK_CAMPUS_TYPE)),
                                parameterWithName("medicalType").description("분류 - 병원 종류").attributes(example(LINK_MEDICAL_PlACE_TYPE)),
                                parameterWithName("size").optional().description("페이지 정보 - 한 페이지 크기").attributes(example("Default : 20")),
                                parameterWithName("page").optional().description("페이지 정보 - 요청 페이지 번호(시작 0)").attributes(example("Default : 0")),
                                parameterWithName("sort").optional().description("페이지 정보 - 정렬").attributes(example(ControllerTestUtils.LINK_SORT_PLACE))
                        ),
                        responseFields(
                                pageableResponseFields
                        )
                )
        );

    }
}