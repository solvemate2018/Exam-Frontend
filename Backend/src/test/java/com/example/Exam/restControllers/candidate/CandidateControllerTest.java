package com.example.Exam.restControllers.candidate;

import com.example.Exam.dtos.CandidateDTO;
import com.example.Exam.entities.Candidate;
import com.example.Exam.repositories.*;
import com.example.Exam.security.entities.*;
import com.example.Exam.security.payload.request.LoginRequest;
import com.example.Exam.security.payload.response.JwtResponse;
import com.example.Exam.security.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.Exam.testUtils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase
@EnableAutoConfiguration
@SpringBootTest(classes = {com.example.Exam.ExamApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.properties"})
class CandidateControllerTest {
    private final String BASE_PATH = "/api/candidate";
    private final HttpHeaders headers = new HttpHeaders();
    private String securityToken;
    private HttpHeaders headersForRequest;
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;

    //For creating a user
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    CandidateRepository candidateRepo;
    VoteRepository voteRepo;
    PartyRepository partyRepo;
    ArrayList<Integer> ids;

    @Autowired
    public CandidateControllerTest(CandidateRepository candidateRepo, VoteRepository voteRepo, PartyRepository partyRepo) {
        this.candidateRepo = candidateRepo;
        this.voteRepo = voteRepo;
        this.partyRepo = partyRepo;
    }

    @BeforeEach
    void setUp() {
        TestDataMaker.makeDataForTests(candidateRepo, voteRepo, partyRepo);
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        roleRepository.save(adminRole);
        User admin = new User("admin", "admin@a.dk", encoder.encode("test"));
        admin.addRole(adminRole);
        userRepository.save(admin);
        securityToken = "Bearer "+ login("admin","test").getBody().getAccessToken();
        headersForRequest = new HttpHeaders();
        headersForRequest.add("Authorization",securityToken);
    }

    @AfterEach
    void clear(){
        TestDataMaker.clear(candidateRepo, voteRepo,
                partyRepo, userRepository,
                roleRepository);
    }

    @Test
    void getAllCandidates() {
        ResponseEntity<List<CandidateDTO>> response = getResponseFromAllCandidates();
        List<Candidate> candidates = candidateRepo.findAll();
        assertEquals(34,response.getBody().size());
        assertEquals(response.getBody().get(0).getFullName(), "Marcel Meijer");
        assertEquals(candidates.size(),response.getBody().size());
    }

    @Test
    void getPartyCandidates() {
        HttpEntity<String> entity = new HttpEntity<>(null, headersForRequest);
        ResponseEntity<List<CandidateDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/party/" + 3),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CandidateDTO>>() {
                });
        assertEquals("Ulla Holm", response.getBody().get(0).getFullName());
    }

    @Test
    void getCandidateById() {
        HttpEntity<String> entity = new HttpEntity<>(null,headersForRequest);
        ResponseEntity<CandidateDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/" + 1),
                HttpMethod.GET,
                entity,
                CandidateDTO.class);
        assertEquals("Marcel Meijer",response.getBody().getFullName());
        assertEquals(0,response.getBody().getNumberOfVotes());
    }

    @Test
    void createCandidate() {
        CandidateDTO newCandidate = new CandidateDTO();
        newCandidate.setFullName("New Candidate");
        HttpEntity<CandidateDTO> entity = new HttpEntity<>(newCandidate, headersForRequest);
        ResponseEntity<CandidateDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/party/" + 1),
                HttpMethod.POST,
                entity,
                CandidateDTO.class);
        assertEquals("New Candidate",response.getBody().getFullName());
        assertEquals("A - Socialdemokratiet",response.getBody().getPartyName());
    }

    @Test
    void editCandidate() {
        CandidateDTO candidateToEdit = new CandidateDTO();
        candidateToEdit.setFullName("Updated name");

        HttpEntity<CandidateDTO> entity = new HttpEntity<CandidateDTO>(candidateToEdit, headersForRequest);
        ResponseEntity<CandidateDTO> res = restTemplate.exchange(makeUrl(BASE_PATH+"/" + 1 + "/party/"+2),
                HttpMethod.PUT ,
                entity,
                CandidateDTO.class);

        ResponseEntity<CandidateDTO> response = restTemplate.exchange(makeUrl(BASE_PATH + "/" + 1),
                HttpMethod.GET,
                entity,
                CandidateDTO.class);
        assertEquals("Updated name",response.getBody().getFullName());
        assertEquals("C - Det konservative Folkeparti", response.getBody().getPartyName());
    }

    @Test
    void deleteCandidate() {
        HttpEntity<String> entity = new HttpEntity<>(null,headersForRequest);
        ResponseEntity<CandidateDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/"+1),
                HttpMethod.DELETE,
                entity,
                CandidateDTO.class);
        ResponseEntity<List<CandidateDTO>> res = getResponseFromAllCandidates();
        assertEquals(33,res.getBody().size());
    }

    private ResponseEntity<JwtResponse> login(String userName, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(userName);
        loginRequest.setPassword(password);

        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest,headers);
        ResponseEntity<JwtResponse> response = restTemplate.exchange(makeUrl("/api/auth/signin"),
                HttpMethod.POST,
                entity,
                JwtResponse.class);
        return response;
    }

    private String makeUrl(String path) {
        String pathBuilt = "http://localhost:" + port + path;
        return pathBuilt;
    }

    private ResponseEntity<List<CandidateDTO>> getResponseFromAllCandidates() {
        HttpEntity<String> entity = new HttpEntity<>(null, headersForRequest);
        ResponseEntity<List<CandidateDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CandidateDTO>>() {
                });
        return response;
    }
}