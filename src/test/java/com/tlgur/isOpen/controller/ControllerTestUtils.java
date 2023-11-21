package com.tlgur.isOpen.controller;

import com.tlgur.isOpen.domain.Place;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.domain.place.SchoolPlace;
import com.tlgur.isOpen.dto.PlaceCard;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public interface ControllerTestUtils {
    String LINK_MEDICAL_PlACE_TYPE = "link:common/medical-place.html[병원 종류,role=\"popup\"]";
    String LINK_BOOLEAN_TYPE = "link:common/boolean.html[boolean 값 종류,role=\"popup\"]";
    String LINK_FOOD_TYPE = "link:common/food.html[식당 종류,role=\"popup\"]";
    String LINK_TALKABLE_TYPE = "link:common/talkable.html[대화 여부 종류,role=\"popup\"]";
    String LINK_EATABLE_TYPE = "link:common/eatable.html[취식 여부 종류,role=\"popup\"]";
    String LINK_TYPEABLE_TYPE = "link:common/typeable.html[타이핑 여부 종류,role=\"popup\"]";
    String LINK_CAMPUS_TYPE = "link:common/campus.html[캠퍼스 종류,role=\"popup\"]";
    String LINK_SORT_PLACE = "link:common/place-keyword.html[장소 정렬 키워드,role=\"popup\"]";

    static Attributes.Attribute example(String value){
        return new Attributes.Attribute("example", value);
    }

    static Map<String, List<String>> getInternalFieldNameAndValuesByClassType(Class<?> placeClass) {
        Map<String, List<String>> fieldNameAndValues = new HashMap<>();

        Field[] fields = placeClass.getDeclaredFields();

        for (Field field : fields) {
            String usageKey = field.getName();
            Class<?> usageType = field.getType();
            List<String> usageValues = Arrays.stream(usageType.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());
            if (usageType == Boolean.class)
                usageValues = Arrays.asList("TRUE", "FALSE");
            else {
                usageValues.remove(usageValues.size() - 1);
            }
            fieldNameAndValues.put(usageKey, usageValues);
        }
        return fieldNameAndValues;
    }

    static Slice<PlaceCard> readyPlaceCards(Integer size, Pageable pageable) {
        List<PlaceCard> placeCards = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            placeCards.add(
                    new PlaceCard(Long.valueOf(i), "testPlaceCard" + i, now(), now(), "testAddress" + i, "testsavedImage" + i, "testImage" + i)
            );
        }
        return new SliceImpl<>(placeCards, pageable, true);
    }

    public static void addPageableResponseFields(List<FieldDescriptor> base) {
        base.add(fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 상태 - true인 필드만 확인").attributes(example("false")));
        base.add(fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 상태 - true인 필드만 확인").attributes(example("true")));
        base.add(fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 상태 - true인 필드만 확인").attributes(example("false")));

        base.add(fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("페이지 첫번째 요소의 전체 순번").attributes(example("0")));
        base.add(fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("Page Index (start 0)").attributes(example("0")));
        base.add(fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("Elements Count Per Page").attributes(example("20")));
        base.add(fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부 - true인 필드만 확인").attributes(example("true")));
        base.add(fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 여부 - true인 필드만 확인").attributes(example("false")));

        base.add(fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수").attributes(example("5")));
        base.add(fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("요소 전체 개수").attributes(example("100")));
        base.add(fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지인지?").attributes(example("true")));
        base.add(fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지인지?").attributes(example("false")));
        base.add(fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("빈 페이지?").attributes(example("false")));
        base.add(fieldWithPath("size").type(JsonFieldType.NUMBER).description("무시해줭..").attributes(example("")));
        base.add(fieldWithPath("number").type(JsonFieldType.NUMBER).description("무시해줭...").attributes(example("")));

        base.add(fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("무시해줭..").attributes(example("")));
        base.add(fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("무시해줭..").attributes(example("")));
        base.add(fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("무시해줭..").attributes(example("")));

        base.add(fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("무시해줭..").attributes(example("")));
    }
}
