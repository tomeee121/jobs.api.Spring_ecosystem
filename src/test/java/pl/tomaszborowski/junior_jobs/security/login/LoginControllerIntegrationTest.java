package pl.tomaszborowski.junior_jobs.security.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.tomaszborowski.junior_jobs.JuniorJobsApplication;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.UserRepo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LoginControllerIntegrationTest.TestConfig.class)
@ActiveProfiles("container")
@AutoConfigureMockMvc
@Testcontainers
class LoginControllerIntegrationTest {

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer("mongo");


     static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }


    @Test
    public void whenUserIsValid_thenShouldReturn2XXSuccessWithJwtToken(@Autowired MockMvc mockMvc,
                                                                       @Autowired UserRepo userRepository) throws Exception {
        //given
        assertThat(userRepository.findUserByUsername("ExistentUser")).isNotEmpty();
        String body = getExistentUser();

        //when
        MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        //then
        final String actualToken = result.getResponse().getContentAsString();
        assertThat(actualToken).isNotBlank();
    }

    @Test
    public void whenUsernameOrPasswordIsNotValid_thenShouldReturn401Unauthorized(@Autowired MockMvc mockMvc,
                                                                                 @Autowired UserRepo userRepository) throws Exception {
        //given
        assertThat(userRepository.findUserByUsername("NotExistentUser")).isEmpty();
        String body = getNotExistentUser();

        //when
        // then
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void whenRequestBodyIsNotValidOfNoUsername_thenShouldReturn400BadRequest(@Autowired MockMvc mockMvc) throws Exception {
        //given
        String expectedResponse = "{\"message\":\"[username must not be empty, username must not be of whitespaces, username must not be null]\",\"httpStatus\":\"BAD_REQUEST\"}";
        String body = "{\"password\":\"passwd\"}";

        //when
        final MvcResult mvcResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andReturn();
        final String responseActual = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(responseActual).isEqualTo(expectedResponse);
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

    @Import(JuniorJobsApplication.class)
    static class TestConfig {

    }

}