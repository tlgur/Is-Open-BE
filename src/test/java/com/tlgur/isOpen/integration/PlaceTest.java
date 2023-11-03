package com.tlgur.isOpen.integration;

import com.tlgur.isOpen.controller.PlaceController;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
public class PlaceTest {
    @Autowired
    private PlaceController placeController;

}
