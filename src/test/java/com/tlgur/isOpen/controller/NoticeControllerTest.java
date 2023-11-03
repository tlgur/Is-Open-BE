package com.tlgur.isOpen.controller;


import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.error.exceptions.NoMatchNoticeIDException;
import com.tlgur.isOpen.repository.NoticeRepository;
import com.tlgur.isOpen.dto.NoticePrev;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tlgur.isOpen.controller.ControllerTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(controllers = NoticeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class NoticeControllerTest {
    @Autowired
    private MockMvc mokcMvc;
    @Autowired
    private NoticeController noticeController;
    @MockBean
    private NoticeRepository noticeRepository;

    private final String testTitlePrefix = "testNoticeTitle";
    private final String testContentPrefix = "testNoticeTitle";
    private final Integer testContentIterationCount = 30;

    @Test
    public void getRecentNotices_savedNotices_ReturnEmptyList() throws Exception{
        //given
        Integer noticeCount = 3;
        List<NoticePrev> noticePrevs = readyNoticePrevs(noticeCount);

        //mocking
        given(noticeRepository.getNoticePrevsRecent()).willReturn(noticePrevs);

        //when
        ResultActions actions = mokcMvc.perform(
                get("/notice/recent"
                ));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.noticePrevsCount").value(noticeCount));
        for (int i = 1; i < noticeCount; i++) {
            actions.andExpect(jsonPath("$.noticePrevs[" + (i - 1) + "].noticeId").value(i));
            actions.andExpect(jsonPath("$.noticePrevs[" + (i - 1) + "].title").value(testTitlePrefix + i));
        }
        actions.andDo(
                document(
                        "/notice/get/lists",
                        responseFields(
                                fieldWithPath("noticePrevsCount").type(JsonFieldType.NUMBER).description("가져온 공지 개수(최대 5, 최소 0)").attributes(example("5")),
                                fieldWithPath("noticePrevs[].noticeId").type(JsonFieldType.NUMBER).description("세부 조회시 사용될 공지 ID").attributes(example("123")),
                                fieldWithPath("noticePrevs[].title").type(JsonFieldType.STRING).description("공지 제목").attributes(example("공지제목입니다"))
                        )
                )
        );
    }

    @Test
    public void getNotice_default_Success() throws Exception{
        //given
        Long noticeID = 0L;
        Notice notice = new Notice(testTitlePrefix, testContentPrefix);

        //mocking
        given(noticeRepository.findById(noticeID)).willReturn(Optional.of(notice));

        //when
        ResultActions actions = mokcMvc.perform(
                get("/notice/{noticeID}", noticeID)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testTitlePrefix))
                .andExpect(jsonPath("$.content").value(testContentPrefix))
                .andDo(
                        document(
                                "/notice/get/detail",
                                pathParameters(
                                        parameterWithName("noticeID").description("공지 ID").attributes(example(noticeID.toString()))
                                ),
                                responseFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("공지 제목").attributes(example("공지제목입니다")),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("공지 내용물").attributes(example("공지 내용입니다"))
                                )
                        )
                );
    }

    @Test
    public void getNotice_NoMatchNoticeID_BadNoticeIDException() throws Exception{
        //given
        Long noticeID = -123L;

        //mocking
        given(noticeRepository.findById(noticeID)).willReturn(Optional.empty());

        //when
        MvcResult badIDResult = mokcMvc.perform(
                        get("/notice/{noticeID}", noticeID)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(badIDResult.getResolvedException()).isExactlyInstanceOf(NoMatchNoticeIDException.class);

    }


    /**
     * @param noticeCnt 희망 개수
     * @return Id : 1 ~ noticeCnt
     * @return title : concat(prefix, id)
     */
    private List<NoticePrev> readyNoticePrevs(Integer noticeCnt) {
        List<NoticePrev> noticePrevs = new ArrayList<>();
        for (int i = 1; i <= noticeCnt; i++) {
            noticePrevs.add(new NoticePrev(Long.valueOf(i), testTitlePrefix + i));
        }
        return noticePrevs;
    }
}