package com.example.tidetest;

import com.example.tidetest.model.Feature;
import com.example.tidetest.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TideTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TideApplicationIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String URL = "/features/users/";

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
                "http://localhost:" + this.port + URL, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //then(entity.getBody()).isEqualTo("Hi!");
    }

    @Test
    public void shouldReturn200WhenSendingRequestTogetUserById() throws Exception {
        @SuppressWarnings("rawtypes") ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + URL + "2", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //then(entity.getBody()).isEqualTo("Test!");
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // prepare
        // Need to import tide_demo_dump.sql  which will insert records in DB
        // fetched by this Restful web service call
        // execute
        ResponseEntity<List> responseEntity =
                this.testRestTemplate.getForEntity(URL,
                        List.class);
        // collect response
        int status = responseEntity.getStatusCodeValue();
        List<User> usrListResult = null;
        if (responseEntity.getBody() != null) {
            usrListResult = responseEntity.getBody();
        }
        // verify
        assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
        assertNotNull("Users not found", usrListResult);
        assertEquals("Incorrect Users List", 3, usrListResult.size());
    }

    @Test
    public void testGetUser() throws Exception {
        // prepare
        // Need to import tide_demo_dump.sql  which will insert records in DB
        // fetched by this Restful web service call
        // execute
        ResponseEntity<User> responseEntity = this.testRestTemplate.getForEntity(URL + "{id}",
                User.class,
                new Long(2));
        // collect response
        int status = responseEntity.getStatusCodeValue();
        User resultUser = responseEntity.getBody();
        // verify
        assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
        assertNotNull(resultUser);
        assertEquals(2l, resultUser.getId().longValue());
    }

    @Test
    public void testAddUser() throws Exception {
        // prepare
        User user = new User("User X", new HashSet<Feature>());
        // execute
        ResponseEntity<User> responseEntity = this.testRestTemplate.postForEntity(URL,
                user,
                User.class);
        // collect Response
        int status = responseEntity.getStatusCodeValue();
        User resultUser = responseEntity.getBody();
        // verify
        assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);
        assertNotNull(resultUser);
        assertNotNull(resultUser.getId().longValue());
    }

    @Test
    public void testUpdateUser() throws Exception {
        // prepare
        // here create the User object with ID equal to ID of
        // User need to be updated new properties

        Feature elements[] = {new Feature(1l, "xero"), new Feature(2l, "debit")};
        //Feature elements[] = {new Feature(1l, "xero"), new Feature(3l, "debit")};
        User user = new User(6l, "User Y", new HashSet(Arrays.asList(elements)));
        HttpEntity<User> requestEntity = new HttpEntity<User>(user);
        // execute
        ResponseEntity<Void> responseEntity = this.testRestTemplate.exchange(URL,
                HttpMethod.PUT,
                requestEntity,
                Void.class);

        // verify
        int status = responseEntity.getStatusCodeValue();
        assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
    }

    @Test
    public void testDeleteUser() throws Exception {
        // execute - delete the record added while initializing database with
        // test data
        ResponseEntity<Void> responseEntity = this.testRestTemplate.exchange(URL + "{id}",
                HttpMethod.DELETE, null, Void.class, new Long(6));
        // verify
        int status = responseEntity.getStatusCodeValue();
        assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);
    }
}
