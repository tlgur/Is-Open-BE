package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.domain.place.Cafe;
import com.tlgur.isOpen.domain.place.PcCafe;
import com.tlgur.isOpen.repository.place.PcCafeRepository;
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

import static com.tlgur.isOpen.controller.ControllerTestUtils.example;
import static com.tlgur.isOpen.controller.ControllerTestUtils.getInternalFieldNameAndValuesByClassType;
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
@WebMvcTest(controllers = PcCafeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PcCafeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PcCafeController pcCafeController;
    @MockBean
    private PcCafeRepository pcCafeRepository;
    @MockBean
    private PlaceService placeService;


    /**
     * input : 여러 세부 분류가 있는 PlaceType
     * expect result : 세부 분류들 이름 + 총 개수 리턴
     */
    @Test
    public void getUsageInformation_Default_ReturnAppropriateFields() throws Exception {
        //given
        Map<String, List<String>> expectUsageInfosContainer = getInternalFieldNameAndValuesByClassType(PcCafe.class);

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/usages/pc")
        );

        //then
        List<String> usageKeys = expectUsageInfosContainer.keySet().stream().toList();
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0));
        actions.andDo(
                document(
                        "/place/get/usages/pc",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("usage Information 분류 개수").attributes(example("0 - 변동 시 항목 추가"))
                        )
                )
        );
    }
}