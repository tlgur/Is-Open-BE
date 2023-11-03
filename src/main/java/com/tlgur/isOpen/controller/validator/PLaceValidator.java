package com.tlgur.isOpen.controller.validator;

import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.error.exceptions.InvalidUsageInformationException;

import java.util.List;

public interface PLaceValidator {
    static void validatePlaceClassification(PlaceType placeType, List<String> usageKeys){

    }

    private void validateBooleanType(List<String> usageValues) {
        if(usageValues.size() != 2) throw new InvalidUsageInformationException();
        if(!(usageValues.contains("TRUE") && usageValues.contains("FALSE")))
            throw new InvalidUsageInformationException();
    }

    private void validateEnumType(String typeName, List<String> usageValues) {
//        Class<?> type = Class.forName(typeName);
    }
}
