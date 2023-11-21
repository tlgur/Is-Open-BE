package com.tlgur.isOpen;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.place.SchoolPlace;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.repository.PlaceRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class IsOpenApplicationTests {

	@Test
	void contextLoads() {
	}

}
