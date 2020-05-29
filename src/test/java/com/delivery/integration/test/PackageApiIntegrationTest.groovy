package com.delivery.integration.test

import groovy.json.JsonSlurper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PackageApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc

    private JsonSlurper jsonSlurper = new JsonSlurper()

    private String login = "login"
    private String password = "password"
    private String token

    @Test
    void test() {
        signUp()
        signIn()
    }

    private void signUp() {
        String signUpBody = """{"login":"$login","password":"$password"}"""

        mockMvc
                .perform(
                        post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(signUpBody)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.message').value('User registered successfully!'))
    }

    private void signIn() {
        String signInBody = """{"login":"$login","password":"$password"}"""
        def signInResult = mockMvc
                .perform(
                        post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(signInBody)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.login').value(login))
                .andExpect(jsonPath('$.token').exists())
                .andExpect(jsonPath('$.tokenType').value('Bearer'))
                .andReturn()

        def signInResponseJson = json(signInResult)
        token = signInResponseJson.accessToken
    }

    private Object json(MvcResult mvcResult) {
        jsonSlurper.parseText(mvcResult.getResponse().getContentAsString())
    }
}
