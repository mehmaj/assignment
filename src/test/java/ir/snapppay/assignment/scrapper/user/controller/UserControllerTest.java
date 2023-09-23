package ir.snapppay.assignment.scrapper.user.controller;

import ir.snapppay.assignment.scrapper.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    final private String BODY_TEMPLATE = "{\"email\":\"%s\", \"password\":\"%s\"}";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void checkEmailPatternFails() throws Exception {
        String body = String.format(BODY_TEMPLATE, "noPattern", "1234567a");
        mvc.perform(MockMvcRequestBuilders.post("/auth/sign-up").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void checkPasswordConstraintsFails() throws Exception {
        String body = String.format(BODY_TEMPLATE, "user@gmail.com", "1");
        mvc.perform(MockMvcRequestBuilders.post("/auth/sign-up").header("Content-type", "application/json")
                        .content(body))
                .andExpect(status().isBadRequest()).andReturn();
    }


    @Test
    public void signUpSuccess() throws Exception {
        String body = String.format(BODY_TEMPLATE, "user@gmail.com", "1234567a");

        mvc.perform(MockMvcRequestBuilders.post("/auth/sign-up").header("Content-type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void checkSignInConstraintsFails() throws Exception {
        //Bad username and password
        String body = String.format(BODY_TEMPLATE, "", "");
        mvc.perform(MockMvcRequestBuilders.post("/auth/sign-in").header("Content-type", "application/json")
                        .content(body))
                .andExpect(status().isBadRequest()).andReturn();
    }

}