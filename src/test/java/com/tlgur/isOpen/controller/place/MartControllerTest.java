package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.controller.ControllerTestUtils;
import com.tlgur.isOpen.domain.place.Cafe;
import com.tlgur.isOpen.domain.place.Mart;
import com.tlgur.isOpen.repository.place.CafeRepository;
import com.tlgur.isOpen.repository.place.MartRepository;
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
@WebMvcTest(controllers = MartController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MartController martController;
    @MockBean
    private MartRepository martRepository;
    @MockBean
    private PlaceService placeService;

    @Test
    public void getUsageInformation_Default_ReturnAppropriateFields() throws Exception{
        //given

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/place/usages/mart")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0));

        actions.andDo(
                document(
                        "/place/get/usages/mart",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("usage Information 분류 개수").attributes(example("0 - 변동 시 항목 추가"))
                        )
                )
        );
    }
}