package ir.snapppay.assignment.scrapper.track.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TrackControllerTest {
    final private String BODY_TEMPLATE = "{\"url\":\"%s\"}";
    @Autowired
    private MockMvc mvc;

    @Test
    public void checkTrackAnonymousAccessFails() throws Exception {
        String body = String.format(BODY_TEMPLATE, "https://www.digikala.com/product/dkp-9140230/");
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/track").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized()).andReturn();
    }


}