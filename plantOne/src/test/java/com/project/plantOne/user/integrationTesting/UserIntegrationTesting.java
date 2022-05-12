package com.project.plantOne.user.integrationTesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.project.plantOne.jwt.JwtRequest;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserObjects;
import com.project.plantOne.user.UserRepository;
import com.project.plantOne.user.customUserModel.UserRequest;
import com.project.plantOne.user.customUserModel.UserResponse;
import com.project.plantOne.user.customUserModel.UserUpdateRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
public class UserIntegrationTesting {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private UserObjects userObjects;

    @Autowired
    private UserRepository userRepository;

    @Rule
    public TestName testName = new TestName();

    public UserIntegrationTesting(){
        userObjects = new UserObjects();
    }

    @After
    public void delete(){

    }

    @Before
    public void print() throws IOException {
        if(testName.getMethodName().equals("addUser") || testName.getMethodName().equals("getHome")){
            return;
        }
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        UserRequest userRequest = userObjects.getUserRequest();
        LinkedMultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("user", userRequest);
        map.add("file",getUserFileResource());

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,headers);
        ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(ADD_USER, HttpMethod.POST, entity, UserResponse.class);
        UserResponse userResponse = responseEntity.getBody();
        userResponse.setUser_id(userResponse.getUser_id());
    }

    public static Resource getUserFileResource() throws IOException {
        Path tempFile = Files.createTempFile("Sample", ".txt");
        Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
        File file = tempFile.toFile();
        return new FileSystemResource(file);
    }

    @Test
    public void addUser() throws IOException {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        UserRequest userRequest = userObjects.getUserRequest();
        LinkedMultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("user", userRequest);
        map.add("file",getUserFileResource());

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,headers);
        ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(ADD_USER, HttpMethod.POST, entity, UserResponse.class);
        UserResponse userResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userRequest.getFirst_name(), userResponse.getFirst_name());
        userRepository.deleteById(userResponse.getUser_id());
    }

    @Test
    public void updateUser() throws IOException {
        UserUpdateRequest userUpdateRequest = userObjects.getUpdatedUser();

        User user = userObjects.getUser();
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(user.getEmail());
        jwtRequest.setPassword(user.getPassword());
        String jwtResponse = restTemplate.postForObject(AUTHENTICATE_USER, jwtRequest, String.class);
        JsonNode jsonNode = objectMapper.readTree(jwtResponse);
        String bearerToken = jsonNode.get("jwtToken").textValue();
        UUID uuid = UUID. fromString(jsonNode.get("user_id").textValue());
        userUpdateRequest.setUser_id(uuid);
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        header.add(HEADER_STRING, "Bearer " + bearerToken);
        header.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);

        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        LinkedMultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("user", userUpdateRequest);
        map.add("file",getUserFileResource());

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,header);
        ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(ADD_USER, HttpMethod.PUT, entity, UserResponse.class);
        UserResponse userResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userUpdateRequest.getFirst_name(), userResponse.getFirst_name());
        userRepository.deleteById(userResponse.getUser_id());
    }

    @Test
    public void getUserById() throws IOException {

        UserRequest userRequest = userObjects.getUserRequest();
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(userRequest.getEmail());
        jwtRequest.setPassword(userRequest.getPassword());
        String jwtResponse = restTemplate.postForObject(AUTHENTICATE_USER, jwtRequest, String.class);
        JsonNode jsonNode = objectMapper.readTree(jwtResponse);
        String bearerToken = jsonNode.get("jwtToken").textValue();
        UUID uuid = UUID. fromString(jsonNode.get("user_id").textValue());

        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        header.add(HEADER_STRING, "Bearer " + bearerToken);
        header.add("Content-Type", "application/json");

        String user_id = jsonNode.get("user_id").textValue();
        HttpEntity<String> entityForUser = new HttpEntity<String>(header);
        ResponseEntity<UserResponse> responseEntityForUser = restTemplate.exchange(USERS_URL+"/{user_id}", HttpMethod.GET, entityForUser, UserResponse.class, user_id);
        UserResponse getUser = responseEntityForUser.getBody();
        assertEquals(HttpStatus.OK, responseEntityForUser.getStatusCode());
        assertEquals(user_id, getUser.getUser_id().toString());
        userRepository.deleteById(UUID. fromString(user_id));
    }

    @Test
    public void getHome(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(USERS_URL+"/home", String.class);
        String homeString = responseEntity.getBody();
        assertEquals("<h1>Welcome to PlantOne</h1>", homeString);
    }

    @Test
    public void deleteUser() throws JsonProcessingException {
        UserRequest userRequest = userObjects.getUserRequest();
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(userRequest.getEmail());
        jwtRequest.setPassword(userRequest.getPassword());
        String jwtResponse = restTemplate.postForObject(AUTHENTICATE_USER, jwtRequest, String.class);
        JsonNode jsonNode = objectMapper.readTree(jwtResponse);
        String bearerToken = jsonNode.get("jwtToken").textValue();
        UUID uuid = UUID. fromString(jsonNode.get("user_id").textValue());

        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        header.add(HEADER_STRING, "Bearer " + bearerToken);
        header.add("Content-Type", "application/json");

        String user_id = jsonNode.get("user_id").textValue();
        HttpEntity<String> entityForUser = new HttpEntity<String>(header);
        ResponseEntity<String> responseEntityForUser = restTemplate.exchange(USERS_URL+"/{user_id}", HttpMethod.DELETE, entityForUser, String.class, user_id);
        String getUser = responseEntityForUser.getBody();
        assertEquals(HttpStatus.OK, responseEntityForUser.getStatusCode());
        assertEquals("User deleted successfully", getUser);

    }

}
