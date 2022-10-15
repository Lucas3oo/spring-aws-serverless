package se.solrike.cloud.serverless.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import se.solrike.cloud.serverless.app.Application;

@SpringBootTest(classes = { Application.class })
@WebAppConfiguration
class ItemControllerTest {

  protected MockMvc mMvc;

  @Autowired
  protected WebApplicationContext mWebApplicationContext;

  @BeforeEach
  protected void setUp() {
    mMvc = MockMvcBuilders.webAppContextSetup(mWebApplicationContext).build();
  }

  @Test
  void getAllItems() throws Exception {
    MvcResult mvcResult = mMvc.perform(MockMvcRequestBuilders.get("/items").accept(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertThat(status).isEqualTo(200);
    String content = mvcResult.getResponse().getContentAsString();
    assertThat(content).contains("Bilbo");
  }

  @Test
  void getItemThatDoesntExists() throws Exception {
    String itemIdThatDoesNotExists = "4711";
    MvcResult mvcResult = mMvc
        .perform(
            MockMvcRequestBuilders.get("/items/" + itemIdThatDoesNotExists).accept(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertThat(status).isEqualTo(404);
    String errorMsg = mvcResult.getResponse().getContentAsString();
    assertThat(errorMsg).contains(itemIdThatDoesNotExists);
  }

}
