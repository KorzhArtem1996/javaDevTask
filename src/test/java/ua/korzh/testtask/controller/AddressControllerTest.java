package ua.korzh.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ua.korzh.testtask.cache.CoordinatePair;
import ua.korzh.testtask.model.Address;
import ua.korzh.testtask.service.address.AddressService;
import ua.korzh.testtask.service.user.UserService;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddressService addressService;
    @MockBean
    private UserService userService;
    @MockBean
    private LoadingCache<CoordinatePair, Address> cache;

    @Test
    @WithMockUser(authorities = "WRITE")
    public void getAllAddressesTest() throws Exception {

        var addresses = List.of(new Address(), new Address(), new Address());
        when(addressService.getAllAddresses()).thenReturn(addresses);

        ResultActions resultActions = mockMvc.perform(get("/address").accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(content().string(MAPPER.writeValueAsString(addresses)));
    }

    @Test
    @WithMockUser(authorities = "WRITE")
    public void getAddressByCoordinates() throws Exception {

        var address = new Address();
        var lat = 55.3242d;
        var lon = 33.5324d;
        when(cache.getUnchecked(any())).thenReturn(address);

        ResultActions resultActions = mockMvc.perform(get("/address/coordinate?lat={lat}&lon={lon}", lat, lon)
                                                        .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(content().string(MAPPER.writeValueAsString(address)));
    }
}