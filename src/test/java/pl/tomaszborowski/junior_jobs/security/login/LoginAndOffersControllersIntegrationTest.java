package pl.tomaszborowski.junior_jobs.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.tomaszborowski.junior_jobs.JuniorJobsApplication;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.UserRepo;
import pl.tomaszborowski.junior_jobs.security.login.domain.exceptions.LoginErrorResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JuniorJobsApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("container")
@Testcontainers
class LoginAndOffersControllersIntegrationTest {

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer("mongo");

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    public void whenPositiveAuthenticationWithJwtToken_thenShouldReturnOffersResponse200Success(@Autowired MockMvc mockMvc,
                                                                                                @Autowired UserRepo userRepository) throws Exception {
        //given
        assertThat(userRepository.findUserByUsername("ExistentUser")).isNotEmpty();
        String body = getExistentUser();

        //when
        //then
        MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        final String token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void whenNegativeAuthenticationWithJwtToken_thenShouldReturnOffersResponse401Unauthorized(@Autowired MockMvc mockMvc,
                                                                                                     @Autowired UserRepo userRepository,
                                                                                                     @Autowired ObjectMapper objectMapper) throws Exception {

        //given
        final LoginErrorResponse expectedLoginErrorResponse = new LoginErrorResponse(
                "Bad Credentials",
                HttpStatus.UNAUTHORIZED
        );
        assertThat(userRepository.findUserByUsername("NotExistentUser")).isEmpty();

        String body = getNotExistentUser();

        //when
        MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isUnauthorized())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        final LoginErrorResponse loginErrorResponse = objectMapper.readValue(response, LoginErrorResponse.class);

        //then
        assertThat(loginErrorResponse).isEqualTo(expectedLoginErrorResponse);

        mockMvc.perform(get("/offers"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    private String getExistentUser() {
        return getUser("ExistentUser", "password");
    }

    private String getNotExistentUser() {
        return getUser("NotExistentUser", "password");
    }

    private String getUser(String userName, String password) {
        return "{\"userName\":\"" + userName + "\", \"password\":\"" + password + "\"}";
    }

}