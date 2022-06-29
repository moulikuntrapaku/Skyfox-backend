package com.booking.customers;

import com.booking.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;


    @BeforeEach
    public void before() {
        customerRepository.deleteAll();
    }

    @AfterEach
    public void after() {
        customerRepository.deleteAll();
    }

    @Test
    @AutoConfigureMockMvc(addFilters=false)
    public void should_save_user_and_customer_detail() throws Exception {
        final String requestJson = "{" +
                "\"name\": \"Customer 1\", \"phoneNumber\": \"9922334455\",\"email\":\"ark@gmail.com\"," +
                "\"user\": " + "{\"username\": \"Customer\", \"password\": \"Password@01\"}" + "}";
        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isCreated());

        assertThat(customerRepository.findAll().size(), is(1));
   }
}