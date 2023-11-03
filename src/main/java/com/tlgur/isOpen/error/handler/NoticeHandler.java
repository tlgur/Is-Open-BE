package com.tlgur.isOpen.error.handler;

import com.tlgur.isOpen.controller.NoticeController;
import com.tlgur.isOpen.error.exceptions.NoMatchNoticeIDException;
import com.tlgur.isOpen.error.handler.dto.ErrorDetail;
import com.tlgur.isOpen.error.handler.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {NoticeController.class})
public class NoticeHandler {
    @ExceptionHandler(NoMatchNoticeIDException.class)
    public ResponseEntity<ErrorResponse> noMatchNoticeIDException(NoMatchNoticeIDException e, HttpServletRequest request) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field("noticeID")
                .given("path parameter")
                .reasonMessage("해당 ID값을 존재하는 Pending 된 요청이 없습니다")
                .build();
        return ResponseEntity.badRequest().body(ErrorResponse.fromException(e, request, errorDetail));
    }
}
