package com.adamzurada.bus_routes_service.web;

import com.adamzurada.bus_routes_service.BusRoutesServiceApplicationTests;
import com.adamzurada.bus_routes_service.service.DataLoadingService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusRoutesServiceApplicationTests.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Slf4j

public class BusStationsControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DataLoadingService dataLoadingService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void givenValidParameters_whenCallingEndpoint_thenReturnOK() throws Exception {
        dataLoadingService.loadBusRoutesCoordinatesFromFile("src/test/resources/example/data");
        log.info(mockMvc.perform(
                get("/api/direct?dep_sid=40&arr_sid=41")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direct_bus_route", is(false)))
                .andExpect(jsonPath("$.dep_sid", is(40)))
                .andExpect(jsonPath("$.arr_sid", is(41)))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void givenValidParameters_whenCallingEndpoint_thenReturnOK2() throws Exception {
        dataLoadingService.loadBusRoutesCoordinatesFromFile("src/test/resources/example/data");
        log.info(mockMvc.perform(
                get("/api/direct?dep_sid=121&arr_sid=114")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direct_bus_route", is(true)))
                .andExpect(jsonPath("$.dep_sid", is(121)))
                .andExpect(jsonPath("$.arr_sid", is(114)))
                .andReturn().getResponse().getContentAsString());
    }
    //TODO add tests
    public void givenInvalidParameters_whenCallingEndpoint_thenReturnError(){}
    public void givenTheSameDepAndArrSids_whenCallingEndpoint_thenReturnError(){}
    public void givenNoData_whenCallingEndpoint_thenReturnError(){}
}
