package ua.korzh.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ua.korzh.testtask.model.Location;
import ua.korzh.testtask.service.location.LocationService;
import ua.korzh.testtask.service.user.UserService;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LocationService locationService;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "artem", authorities = "WRITE")
    public void searchLocationsByAddressTest() throws Exception {

        var locations = List.of(new Location());
        when(locationService.searchLocations(anyString())).thenReturn(locations);

        ResultActions resultActions = mockMvc.perform(post("/location?address=address").accept(
                                                                                            MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(content().string(MAPPER.writeValueAsString(locations)));
    }
}