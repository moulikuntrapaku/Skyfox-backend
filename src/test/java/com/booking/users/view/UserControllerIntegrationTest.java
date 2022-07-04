package com.booking.users.view;

import com.booking.App;
import com.booking.users.User;
import com.booking.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void before() {
        userRepository.deleteAll();
    }

    @AfterEach
    public void after() {
        userRepository.deleteAll();
    }


    @Test
    public void shouldLoginSuccessfully() throws Exception {
        userRepository.save(new User("Mike", "Mike@1r566"));
        mockMvc.perform(get("/login")
                        .with(httpBasic("Mike", "Mike@1r566")))
                        .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowErrorMessageForInvalidCredentials() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @AutoConfigureMockMvc(addFilters=false)
    public void shouldNotChangePasswordWhenUserIsNotPresent() throws Exception{
        final String requestJson = "{" +
                "\"userName\": \"Mike\", \"oldPassword\": \"Mike@1r566\",\"newPassword\":\"NewPass@123\""+"}";

        mockMvc.perform(put("/user/password")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
    @Test

    public void shouldNotChangePasswordWhenNotAuthorized() throws Exception{
        userRepository.save(new User("Mike", "Mike@1r566"));

        final String requestJson = "{" +
                "\"userName\": \"Mike\", \"oldPassword\": \"Mike@1566\",\"newPassword\":\"NewPass@123\""+"}";

        mockMvc.perform(put("/user/password")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldChangePasswordSuccessfullyWhenAuthorized() throws Exception{
       userRepository.save(new User("Mike", "Mike@1r566"));

        final String requestJson = "{" +
                "\"userName\": \"Mike\", \"oldPassword\": \"Mike@1r566\",\"newPassword\":\"NewPass@123\""+"}";

        mockMvc.perform(put("/user/password")
                        .with(httpBasic("Mike","Mike@1r566"))
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
