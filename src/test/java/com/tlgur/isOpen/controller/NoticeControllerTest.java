package com.tlgur.isOpen.controller;


import com.tlgur.isOpen.TestUtils;
import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.error.exceptions.NoMatchNoticeIDException;
import com.tlgur.isOpen.repository.NoticeRepository;
import com.tlgur.isOpen.dto.NoticePrev;
import com.tlgur.isOpen.service.NoticeService;
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
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tlgur.isOpen.controller.ControllerTestUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(controllers = NoticeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class NoticeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoticeController noticeController;
    @MockBean
    private NoticeService noticeService;
    @MockBean
    private NoticeRepository noticeRepository;

    private final String testTitlePrefix = "testNoticeTitle";
    private final String testContentPrefix = "testNoticeContent";
    private final Integer testContentIterationCount = 30;

    /**
     * input : title & content
     * expect result : id & title
     */
    @Test
    public void createNotice_default_ReturnCreatedNoticeIdAndTitle() throws Exception{
        //given
        String title = testTitlePrefix;
        String content = testContentPrefix;
        Long noticeId = 1234L;
        Notice notice = new Notice(title, content);

        //mocking
        doAnswer(invocation -> new NoticePrev(noticeId, title)).when(noticeService).saveNotice(eq(notice));

        //when
        ResultActions actions = mockMvc.perform(
                post("/notice")
                        .queryParam("title", title)
                        .queryParam("content", content)
        );

        //then
        actions.andExpect(status().isOk());

        actions.andExpect(jsonPath("$.noticeId").value(noticeId))
                .andExpect(jsonPath("$.title").value(title));

        actions.andDo(
                document(
                        "/notice/post/new",
                        queryParameters(
                                parameterWithName("title").description("공지 제목").attributes(example(title)),
                                parameterWithName("content").description("공지 내용").attributes(example(content))
                        ),
                        responseFields(
                                fieldWithPath("noticeId").type(JsonFieldType.NUMBER).description("공지 식별자").attributes(example(noticeId.toString())),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("공지 제목").attributes(example(title))
                        )
                )
        );
    }
    
    /**
     * input : only title
     * expect result : MissingServletRequestParameterException
     */
    @Test
    public void createNotice_EmptyContent_MissingServletRequestParameterException() throws Exception{
        //given
        String title = testTitlePrefix;
        String content = null;

        //mocking
        
        //when
        MvcResult emptyContentResult = mockMvc.perform(
                        post("/notice")
                                .queryParam("title", title)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(emptyContentResult.getResolvedException()).isInstanceOf(MissingServletRequestParameterException.class);
    }
    
    /**
     * input : id & title & content
     * expect result : id & title
     */
    @Test
    public void updateNotice_Default_Success() throws Exception{
        //given
        Long noticeId = 123L;
        String title = testTitlePrefix;
        String content = testContentPrefix;
        Notice updateInfo = new Notice(title, content);

        //mocking
        doAnswer(invocation -> {
            TestUtils.setEntityId(updateInfo, noticeId);
            return updateInfo;
        }).when(noticeService).updateNotice(noticeId, updateInfo);
        
        //when
        ResultActions actions = mockMvc.perform(
                patch("/notice/{noticeId}", noticeId)
                        .queryParam("title", title)
                        .queryParam("content", content)
        );

        //then
        actions.andExpect(status().isOk());

        actions.andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));

        actions.andDo(
                document(
                        "/notice/patch/all",
                        pathParameters(
                                parameterWithName("noticeId").description("수정 하려는 공지 식별자").attributes(example(noticeId.toString()))
                        ),
                        queryParameters(
                                parameterWithName("title").description("제목 수정 내용").attributes(example(title)),
                                parameterWithName("content").description("본문 수정 내용").attributes(example(content))
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정된 공지 제목").attributes(example(title.toString())),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정된 공지 내용").attributes(example(content.toString()))
                                )
                )
        );
        
    }

    /**
     * input : id & content
     * expect result : MissingServletRequestParameterException
     */
    @Test
    public void updateNotice_EmptyTitle_MissingServletRequestParameterException() throws Exception{
        //given
        Long noticeId = 123L;
        String title = "";
        String content = testContentPrefix;
        Notice updateInfo = new Notice(title, content);

        //mocking

        //when
        MvcResult emptyTitleResult = mockMvc.perform(
                        patch("/notice/{noticeId}", noticeId)
                                .queryParam("content", content)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(emptyTitleResult.getResolvedException()).isInstanceOf(MissingServletRequestParameterException.class);

    }

    @Test
    public void getRecentNotices_savedNotices_ReturnEmptyList() throws Exception{
        //given
        Integer noticeCount = 3;
        List<NoticePrev> noticePrevs = readyNoticePrevs(noticeCount);

        //mocking
        given(noticeRepository.getNoticePrevsRecent()).willReturn(noticePrevs);

        //when
        ResultActions actions = mockMvc.perform(
                get("/notice/recent"
                ));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.noticePrevsCount").value(noticeCount));
        for (int i = 1; i < noticeCount; i++) {
            actions
                    .andExpect(jsonPath("$.noticePrevs[" + (i - 1) + "].noticeId").value(i))
                    .andExpect(jsonPath("$.noticePrevs[" + (i - 1) + "].title")
                            .value(testTitlePrefix + i));
        }
        actions.andDo(
                document(
                        "/notice/get/lists",
                        responseFields(
                                fieldWithPath("noticePrevsCount").type(JsonFieldType.NUMBER)
                                        .description("가져온 공지 개수(최대 5, 최소 0)").attributes(example("5")),
                                fieldWithPath("noticePrevs[].noticeId").type(JsonFieldType.NUMBER)
                                        .description("세부 조회시 사용될 공지 ID").attributes(example("123")),
                                fieldWithPath("noticePrevs[].title").type(JsonFieldType.STRING)
                                        .description("공지 제목").attributes(example("공지제목입니다"))
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
        ResultActions actions = mockMvc.perform(
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
        MvcResult badIDResult = mockMvc.perform(
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