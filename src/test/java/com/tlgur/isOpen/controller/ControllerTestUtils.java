package com.tlgur.isOpen.controller;

import com.tlgur.isOpen.domain.Place;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.domain.place.SchoolPlace;
import org.springframework.restdocs.snippet.Attributes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ControllerTestUtils {
    String LINK_PlACE_TYPE = "link:common/campus-type.html[캠퍼스 종류,role=\"popup\"]";
    String LINK_MEDICAL_PlACE_TYPE = "link:common/campus-type.html[캠퍼스 종류,role=\"popup\"]";
    String LINK_BOOLEAN_TYPE = "link:common/campus-type.html[캠퍼스 종류,role=\"popup\"]";
    String LINK_FOOD_TYPE = "link:common/campus-type.html[캠퍼스 종류,role=\"popup\"]";
    String LINK_TALKABLE_TYPE = "link:common/campus-type.html[캠퍼스 종류,role=\"popup\"]";
    String LINK_EATABLE_TYPE = "link:common/campus-type.html[캠퍼스 종류,role=\"popup\"]";
    String LINK_TYPEABLE_TYPE = "link:common/campus-type.html[캠퍼스 종류,role=\"popup\"]";
    String LINK_CAMPUS_TYPE = "link:common/campus-type.html[캠퍼스 종류,role=\"popup\"]";

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
}
