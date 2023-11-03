package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.controller.ControllerTestUtils;
import com.tlgur.isOpen.domain.enums.MedicalType;
import com.tlgur.isOpen.domain.place.MedicalPlace;
import com.tlgur.isOpen.repository.place.MedicalPlaceRepository;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
}