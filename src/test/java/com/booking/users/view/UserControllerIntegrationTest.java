package com.booking.users.view;

import com.booking.App;
import com.booking.users.PasswordHistory;
import com.booking.users.PasswordHistoryRepository;
import com.booking.users.User;
import com.booking.users.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.reset;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordHistoryRepository passwordHistoryRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();

        reset(userRepository);
    }


    @Test
    public void shouldLoginSuccessfully() throws Exception {
        String password = bCryptPasswordEncoder.encode("Mike@1r566");
        userRepository.save(new User("Mike", password));
        mockMvc.perform(get("/login")
                        .with(httpBasic("Mike", "Mike@1r566")))
                        .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowErrorMessageForInvalidCredentials() throws Exception {
        mockMvc.perform(get("/login")
                        .with(httpBasic("Mike", "Mike@1r566")))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void shouldNotChangePasswordWhenUserIsNotPresent() throws Exception{
        final String requestJson = "{" +
                "\"userName\": \"Mike\", \"oldPassword\": \"Mike@1r566\",\"newPassword\":\"NewPass@123\""+"}";

        mockMvc.perform(put("/user/password")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

    }
    @Test
    public void shouldNotChangePasswordWhenNotAuthorized() throws Exception{
        userRepository.save(new User("Mike", "Mike@1r566"));

        final String requestJson = "{" +
                "\"userName\": \"Mike\", \"oldPassword\": \"Mike@1r566\",\"newPassword\":\"NewPass@123\""+"}";

        mockMvc.perform(put("/user/password")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void shouldNotChangePasswordWhenOldPasswordIsIncorrect() throws Exception{
        String password = bCryptPasswordEncoder.encode("Mike@1r566");

        userRepository.save(new User("Mike", password));

        final String requestJson = "{" +
                "\"userName\": \"Mike\", \"oldPassword\": \"Mike@1566\",\"newPassword\":\"NewPass@123\""+"}";

        mockMvc.perform(put("/user/password")
                        .content(requestJson)
                        .with(httpBasic("Mike","Mike@1r566"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldChangePasswordSuccessfullyWhenAuthorized() throws Exception{
        String password = bCryptPasswordEncoder.encode("Mike@1r566");

        userRepository.save(new User("Mike1", password));

        final String requestJson = "{" +
                "\"userName\": \"Mike1\",\"oldPassword\": \"Mike@1r566\",\"newPassword\":\"NewPass@123\""+"}";


        mockMvc.perform(put("/user/password")
                        .with(httpBasic("Mike1","Mike@1r566"))
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldNotChangePasswordWhenItMatchesWithLastThreePasswords() throws Exception{
        String password = bCryptPasswordEncoder.encode("Mike@1r566");
        String password1 = bCryptPasswordEncoder.encode("Mike@1r563");
        String password2 = bCryptPasswordEncoder.encode("Mike@1r564");
        User user = userRepository.save(new User("Mike3", password));
        passwordHistoryRepository.save(new PasswordHistory(user.getId(),password1));
        passwordHistoryRepository.save(new PasswordHistory(user.getId(),password2));

        final String requestJson = "{" +
                "\"userName\": \"Mike3\",\"oldPassword\": \"Mike@1r566\",\"newPassword\":\"Mike@1r563\""+"}";


        mockMvc.perform(put("/user/password")
                        .content(requestJson)
                        .with(httpBasic("Mike3","Mike@1r566"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void shouldChangePasswordWhenItNotMatchesWithLastThreePasswords() throws Exception{
        String password = bCryptPasswordEncoder.encode("Mike@1r566");
        String password1 = bCryptPasswordEncoder.encode("Mike@1r563");
        String password2 = bCryptPasswordEncoder.encode("Mike@1r564");
        User user = userRepository.save(new User("Mike3", password));
        passwordHistoryRepository.save(new PasswordHistory(user.getId(),password1));
        passwordHistoryRepository.save(new PasswordHistory(user.getId(),password2));

        final String requestJson = "{" +
                "\"userName\": \"Mike3\",\"oldPassword\": \"Mike@1r566\",\"newPassword\":\"Mike@1r567\""+"}";


        mockMvc.perform(put("/user/password")
                        .content(requestJson)
                        .with(httpBasic("Mike3","Mike@1r566"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
