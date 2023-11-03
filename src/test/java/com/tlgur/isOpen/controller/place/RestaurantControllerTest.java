package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.domain.enums.FoodType;
import com.tlgur.isOpen.domain.enums.MedicalType;
import com.tlgur.isOpen.domain.place.MedicalPlace;
import com.tlgur.isOpen.domain.place.Restaurant;
import com.tlgur.isOpen.repository.place.RestaurantRepository;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static com.tlgur.isOpen.controller.ControllerTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(controllers = RestaurantController.class)
@MockBean(JpaMetamodelMappingContext.class)
class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RestaurantController restaurantController;
    @MockBean
    private RestaurantRepository restaurantRepository;
    @MockBean
    private PlaceService placeService;


    /**
     * expect result : 세부 분류들 이름 + 총 개수 리턴
     */
    @Test
    public void getUsageInformation_Default_ReturnAppropriateFields() throws Exception {
        //given

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/usages/restaurant")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3));

        FoodType[] foodTypes = FoodType.values();
        for (int i = 0; i < foodTypes.length; i++) {
            actions.andExpect(jsonPath("$.foodType[" + i + "]").value(foodTypes[i].toString()));
        }
        actions.andExpect(jsonPath("$.hasDeliveryService[0]").value("TRUE"));
        actions.andExpect(jsonPath("$.hasDeliveryService[1]").value("FALSE"));
        actions.andExpect(jsonPath("$.hasPickUpService[0]").value("TRUE"));
        actions.andExpect(jsonPath("$.hasPickUpService[1]").value("FALSE"));


        actions.andDo(
                document(
                        "/place/get/usages/restaurant",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("usage Information 분류 개수").attributes(example("3 - 변동 시 항목 추가")),
                                fieldWithPath("foodType").type(JsonFieldType.ARRAY).description("usage Information : foodType").attributes(example(LINK_FOOD_TYPE)),
                                fieldWithPath("hasDeliveryService").type(JsonFieldType.ARRAY).description("usage Information : BOOLEAN").attributes(example(LINK_BOOLEAN_TYPE)),
                                fieldWithPath("hasPickUpService").type(JsonFieldType.ARRAY).description("usage Information : BOOLEAN").attributes(example(LINK_BOOLEAN_TYPE))
                        )
                )
        );
    }
}