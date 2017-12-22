package com.example.tidetest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TideTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TideTestApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToRoot() throws Exception {
        @SuppressWarnings("rawtypes") ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/features/", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo("Hi!");
    }

    @Test
    public void shouldReturn200WhenSendingRequestTolistAllUsers() throws Exception {
        @SuppressWarnings("rawtypes") ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/features/users/", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).toString();
    }

    @Test
    public void shouldReturn200WhenSendingRequestTogetUserById() throws Exception {
        @SuppressWarnings("rawtypes") ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/features/user/2", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //then(entity.getBody()).isEqualTo("Test!");
    }

}
