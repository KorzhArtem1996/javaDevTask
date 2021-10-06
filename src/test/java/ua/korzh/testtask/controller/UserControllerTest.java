package ua.korzh.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ua.korzh.testtask.security.model.AppUser;
import ua.korzh.testtask.security.model.Role;
import ua.korzh.testtask.service.user.UserService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import java.util.Set;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final AppUser USER = new AppUser("artem", "password", Set.of(Role.ADMIN));

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void registerTest() throws Exception {

        when(userService.createUser(anyString(), anyString(), anySet())).thenReturn(USER);

        ResultActions resultActions = mockMvc.perform(post("/register?username={username}&password={password}",
                                                            USER.getUsername(), USER.getPassword())
                                                        .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(content().string(MAPPER.writeValueAsString(USER)));
    }

    @Test
    @WithMockUser(username = "artem")
    public void getUserByIdTest() throws Exception {

        when(userService.getUserById(anyLong())).thenReturn(USER);

        ResultActions resultActions = mockMvc.perform(get("/user/1").accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(content().string(MAPPER.writeValueAsString(USER)));
    }
}