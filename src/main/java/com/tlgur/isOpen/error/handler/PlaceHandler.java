package com.tlgur.isOpen.error.handler;

import com.tlgur.isOpen.controller.PlaceController;
import com.tlgur.isOpen.controller.place.*;
import com.tlgur.isOpen.controller.validator.PlaceValidator;
import com.tlgur.isOpen.error.exceptions.BadPlaceIDException;
import com.tlgur.isOpen.error.exceptions.NoUsageInAllTypeException;
import com.tlgur.isOpen.error.handler.dto.BindingErrorResponse;
import com.tlgur.isOpen.error.handler.dto.ErrorDetail;
import com.tlgur.isOpen.error.handler.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice(basePackageClasses = {PlaceController.class, PlaceValidator.class,
        SchoolPlaceController.class, MartController.class, CafeController.class, MedicalPlaceController.class, PcCafeController.class, RestaurantController.class})
public class PlaceHandler {
    @ExceptionHandler(BindException.class)
    public ResponseEntity<BindingErrorResponse> bindException(BindException e, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(BindingErrorResponse.fromException(e, request));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field(e.getParameterName())
                .given("none")
                .reasonMessage("명시된 field는 필수 입니다.")
                .build();
        return ResponseEntity.badRequest().body(ErrorResponse.fromException(e, request, errorDetail));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String parameterName = "placeType";
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field(parameterName)
                .given(request.getParameter(parameterName))
                .reasonMessage("정의된 ENUM 값 이외의 값이 입력되었습니다.")
                .build();
        return ResponseEntity.badRequest().body(ErrorResponse.fromException(e, request, errorDetail));
    }
    @ExceptionHandler(NoUsageInAllTypeException.class)
    public ResponseEntity<ErrorResponse> noUsageInAllTypeException(NoUsageInAllTypeException e, HttpServletRequest request) {
        String parameterName = "placeType";
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field(parameterName)
                .given(request.getParameter(parameterName))
                .reasonMessage("PlaceType이 ALL인 경우 UsageInformation을 검색할 수 없습니다.")
                .build();
        return ResponseEntity.badRequest().body(ErrorResponse.fromException(e, request, errorDetail));
    }

    @ExceptionHandler(BadPlaceIDException.class)
    public ResponseEntity<ErrorResponse> badPlaceIDException(BadPlaceIDException e, HttpServletRequest request) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field("placeID")
                .given("path variable")
                .reasonMessage("주어진 ID로 검색되는 해당 종류의 Place가 존재하지 않습니다.")
                .build();
        return ResponseEntity.badRequest().body(ErrorResponse.fromException(e, request, errorDetail));
    }
}
