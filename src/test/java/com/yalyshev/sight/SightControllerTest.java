package com.yalyshev.sight;

import com.yalyshev.sight.entity.Sight;
import com.yalyshev.sight.repo.SightRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@DisplayName("Sight Unit Tests")

public class SightControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @MockBean
    private SightRepo sightRepo;

    private MockMvc mockMvc;

    @Test
    public void testCont1() throws Exception {
        Sight sight1 = new Sight(1, 60.01, 60.02);
        Sight sight2 = new Sight(2, 70.01, 70.02);

        Mockito.when(sightRepo.getAll()).thenReturn(asList(sight1, sight2));

        mockMvc.perform(get("/sight/all"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].latitude", Matchers.is(60.01)))
                .andExpect(jsonPath("$[0].longitude", Matchers.is(60.02)))
                .andExpect(jsonPath("$[1].latitude", Matchers.is(70.01)))
                .andExpect(jsonPath("$[1].longitude", Matchers.is(70.02)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)));

    }
}
