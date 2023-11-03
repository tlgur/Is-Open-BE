package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.domain.Place;
import com.tlgur.isOpen.domain.enums.*;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.dto.SchoolPlaceUsageInformationRequest;
import com.tlgur.isOpen.repository.place.RestaurantRepository;
import com.tlgur.isOpen.repository.place.SchoolPlaceRepository;
import com.tlgur.isOpen.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tlgur.isOpen.controller.ControllerTestUtils.*;
import static java.time.LocalTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
@WebMvcTest(controllers = SchoolPlaceController.class)
@MockBean(JpaMetamodelMappingContext.class)
class SchoolPlaceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SchoolPlaceController schoolPlaceController;
    @MockBean
    private SchoolPlaceRepository schoolPlaceRepository;
    @MockBean
    private PlaceService placeService;

    private final String placeNamePrefix = "testPlaceName";
    private final String placeLoadAddressPrefix = "testPlaceLoadAddress";
    private final String imgOriginalNamePrefix = "testImgOriginal";
    private final String imgOriginalNamePostfix = ".png";
    private final String imgSavedNamePrefix = "testImgSaved";
    private final String imgSavedNamePostfix = ".png";


    /**
     * expect result : 세부 분류들 이름 + 총 개수 리턴
     */
    @Test
    public void getUsageInformation_Default_ReturnAppropriateFields() throws Exception {
        //given

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/usages/school")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(4));

        Talkable[] talkables = Talkable.values();
        for (int i = 0; i < talkables .length; i++) {
            actions.andExpect(jsonPath("$.talkable[" + i + "]").value(talkables[i].toString()));
        }
        Eatable[] eatables = Eatable.values();
        for (int i = 0; i < eatables.length; i++) {
            actions.andExpect(jsonPath("$.eatable[" + i + "]").value(eatables[i].toString()));
        }
        Typeable[] typeables = Typeable.values();
        for (int i = 0; i < typeables .length; i++) {
            actions.andExpect(jsonPath("$.typeable[" + i + "]").value(typeables[i].toString()));
        }
        actions.andExpect(jsonPath("$.hasConsent[0]").value("TRUE"));
        actions.andExpect(jsonPath("$.hasConsent[1]").value("FALSE"));


        actions.andDo(
                document(
                        "/place/get/usages/school",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("usage Information 분류 개수").attributes(example("4 - 변동 시 항목 추가")),
                                fieldWithPath("talkable").type(JsonFieldType.ARRAY).description("usage Information : talkables").attributes(example(LINK_TALKABLE_TYPE)),
                                fieldWithPath("eatable").type(JsonFieldType.ARRAY).description("usage Information : eatables").attributes(example(LINK_EATABLE_TYPE)),
                                fieldWithPath("typeable").type(JsonFieldType.ARRAY).description("usage Information : typeables").attributes(example(LINK_TYPEABLE_TYPE)),
                                fieldWithPath("hasConsent").type(JsonFieldType.ARRAY).description("usage Information : BOOLEAN").attributes(example(LINK_BOOLEAN_TYPE))
                        )
                )
        );
    }

    /**
     * input : Campus, talk, eat without type, consent - default pageable
     * expect result :
     */
    @Test
    public void getPlaceCardsOfSchoolPlace_defaultWithDefaultPaging_Success() throws Exception{
        //given
        Campus campus = Campus.SEOUL;
        Talkable talkable = Talkable.ANY;
        Eatable eatable = Eatable.ANY;
        Typeable typeable = null;
        Boolean hasConsent = null;
        Integer pageSize = 3;
        Slice<PlaceCard> expectedPlaceCardSlice = createExpectedPlaceCardSlice(pageSize);

        //mocking
        given(schoolPlaceRepository.findSchoolPlaceCardByUsageInformations(
                campus,
                new SchoolPlaceUsageInformationRequest(
                        Optional.of(talkable),
                        Optional.of(eatable),
                        Optional.empty(),
                        Optional.empty()
                )
                , expectedPlaceCardSlice.getPageable()
        )).willReturn(
                expectedPlaceCardSlice
        );

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/cards/school")
                        .queryParam("campus", campus.name())
                        .queryParam("talkable", talkable.name())
                        .queryParam("eatable", eatable.name())
                        .queryParam("size", pageSize.toString())
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext").value(true))
                .andExpect(jsonPath("$.size").value(pageSize))
                .andExpect(jsonPath("$.number").value(0));
        for(int i = 0; i < pageSize; i++){
            PlaceCard expectPlaceCard = expectedPlaceCardSlice.getContent().get(i);
            actions
                    .andExpect(jsonPath("$.content[" + i + "].placeID").value(expectPlaceCard.getPlaceID()))
                    .andExpect(jsonPath("$.content[" + i + "].name").value(expectPlaceCard.getName()))
                    .andExpect(jsonPath("$.content[" + i + "].openAt").value(expectPlaceCard.getOpenAt().toString()))
                    .andExpect(jsonPath("$.content[" + i + "].closeAt").value(expectPlaceCard.getCloseAt().toString()))
                    .andExpect(jsonPath("$.content[" + i + "].loadNameAddress").value(expectPlaceCard.getLoadNameAddress()))
                    .andExpect(jsonPath("$.content[" + i + "].imgSavedName").value(expectPlaceCard.getImgSavedName()))
                    .andExpect(jsonPath("$.content[" + i + "].imgOriginalName").value(expectPlaceCard.getImgOriginalName()));
        }

        actions.andDo(
                document(
                        "place/get/cards/school",
                        queryParameters(
                                parameterWithName("campus").attributes(example(LINK_CAMPUS_TYPE)).description("장소 대 분류"),
                                parameterWithName("talkable").optional().attributes(example(LINK_TALKABLE_TYPE)).description("대화 가능 여부"),
                                parameterWithName("eatable").optional().attributes(example(LINK_EATABLE_TYPE)).description("취식 가능 여부"),
                                parameterWithName("typeable").optional().attributes(example(LINK_TYPEABLE_TYPE)).description("타이핑 가능 여부"),
                                parameterWithName("hasConsent").optional().attributes(example(LINK_BOOLEAN_TYPE)).description("콘센트 보유 여부"),
                                parameterWithName("page").optional().attributes(example("0")).description("paging - page 번호"),
                                parameterWithName("size").optional().attributes(example("5")).description("paging - 한 page 크기")
                        ),
                        responseFields(
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("뒤에 남아 있는지").attributes(example(LINK_BOOLEAN_TYPE)),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("paging - 한 page 크기").attributes(example("5")),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("paging - page 번호").attributes(example("0")),
                                fieldWithPath("content[].placeID").type(JsonFieldType.NUMBER).description("place ID(식별자)").attributes(example("1")),
                                fieldWithPath("content[].name").type(JsonFieldType.STRING).description("place 이름").attributes(example("샘플 장소명")),
                                fieldWithPath("content[].openAt").type(JsonFieldType.STRING).description("운영 시작 시간").attributes(example("00:00")),
                                fieldWithPath("content[].closeAt").type(JsonFieldType.STRING).description("운영 마감 시간").attributes(example("23:59")),
                                fieldWithPath("content[].loadNameAddress").type(JsonFieldType.STRING).description("도로명 주소").attributes(example("수원시 ㅇㅇ로 12번길 ㅇㅇ동")),
                                fieldWithPath("content[].imgSavedName").type(JsonFieldType.STRING).description("대표 사진 저장된 이름").attributes(example("original.png")),
                                fieldWithPath("content[].imgOriginalName").type(JsonFieldType.STRING).description("대표 사진 원본 이름").attributes(example("saved.png"))
                        )
                )
        );
    }

    private Slice<PlaceCard> createExpectedPlaceCardSlice(Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        List<PlaceCard> placeCards = new ArrayList<>();
        for(int i = 0; i < pageRequest.getPageSize(); i++){
            placeCards.add(new PlaceCard(Long.valueOf(i), placeNamePrefix + i, now().truncatedTo(ChronoUnit.MINUTES), now().truncatedTo(ChronoUnit.MINUTES),
                            placeLoadAddressPrefix + i,
                            imgOriginalNamePrefix + i + imgOriginalNamePostfix,
                            imgSavedNamePostfix + i + imgSavedNamePostfix
                    )
            );
        }
        return new SliceImpl<>(placeCards, pageRequest, true);
    }

    /**
     * input : no campus
     * expect result : MissingServletRequestParameterException
     */
    @Test
    public void getPlaceCardsOfSchoolPlace_NoCampus_MissingServletRequestParameterException() throws Exception{
        //given
        Campus campus = Campus.SEOUL;
        Talkable talkable = Talkable.ANY;
        Eatable eatable = Eatable.ANY;
        Typeable typeable = null;
        Boolean hasConsent = null;

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/cards/school")
                        .queryParam("talkable", talkable.name())
                        .queryParam("eatable", eatable.name())
        );

        //then
        MvcResult noCampusResult = actions.andExpect(status().isBadRequest())
                .andReturn();
        assertThat(noCampusResult.getResolvedException()).isExactlyInstanceOf(MissingServletRequestParameterException.class);

    }

    /**
     * input : wrong type to talk
     * expect result :MethodArgumentNotValidException
     */
    @Test
    public void getPlaceCardsOfSchoolPlace_InvalidUsageValue_MethodArgumentNotValidException() throws Exception{
        //given
        String talkable = "절대있을수없는값";

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/cards/school")
                        .queryParam("campus", "SEOUL")
                        .queryParam("talkable", talkable)
        );

        //then
        MvcResult noCampusResult = actions.andExpect(status().isBadRequest())
                .andReturn();
        assertThat(noCampusResult.getResolvedException()).isExactlyInstanceOf(MethodArgumentNotValidException.class);

    }
}