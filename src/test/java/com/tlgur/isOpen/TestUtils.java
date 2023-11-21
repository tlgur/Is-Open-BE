package com.tlgur.isOpen;

import org.springframework.restdocs.snippet.Attributes;

import java.lang.reflect.Field;

public interface TestUtils {
    public static <T> Long setEntityId(T entity, Long id) throws NoSuchFieldException, IllegalAccessException {
        Class<?> entityClass = entity.getClass();
        Field idField = entityClass.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
        return (Long) idField.get(entity);
    }

}
