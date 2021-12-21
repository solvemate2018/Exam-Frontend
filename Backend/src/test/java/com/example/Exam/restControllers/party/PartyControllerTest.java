package com.example.Exam.restControllers.party;

import com.example.Exam.dtos.CandidateDTO;
import com.example.Exam.dtos.PartyDTO;
import com.example.Exam.entities.Candidate;
import com.example.Exam.entities.Party;
import com.example.Exam.repositories.CandidateRepository;
import com.example.Exam.repositories.PartyRepository;
import com.example.Exam.repositories.VoteRepository;
import com.example.Exam.security.entities.ERole;
import com.example.Exam.security.entities.Role;
import com.example.Exam.security.entities.User;
import com.example.Exam.security.payload.request.LoginRequest;
import com.example.Exam.security.payload.response.JwtResponse;
import com.example.Exam.security.repositories.RoleRepository;
import com.example.Exam.security.repositories.UserRepository;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureTestDatabase
@EnableAutoConfiguration
@SpringBootTest(classes = {com.example.Exam.ExamApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.properties"})
class PartyControllerTest {
    private final String BASE_PATH = "/api/party";
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
    public PartyControllerTest(CandidateRepository candidateRepo, VoteRepository voteRepo, PartyRepository partyRepo) {
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
    void getAllParties() {
        ResponseEntity<List<PartyDTO>> response = getResponseFromAllParties();
        List<Party> parties = partyRepo.findAll();
        assertEquals(6,response.getBody().size());
        assertEquals(response.getBody().get(0).getName(), "A - Socialdemokratiet");
        assertEquals(parties.size(),response.getBody().size());
    }

    @Test
    void getPartyById() {
        HttpEntity<String> entity = new HttpEntity<>(null,headersForRequest);
        ResponseEntity<PartyDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/" + 1),
                HttpMethod.GET,
                entity,
                PartyDTO.class);
        assertEquals("A - Socialdemokratiet",response.getBody().getName());
    }

    @Test
    void createParty() {
        PartyDTO newParty = new PartyDTO();
        newParty.setName("New Party");
        HttpEntity<PartyDTO> entity = new HttpEntity<>(newParty, headersForRequest);
        ResponseEntity<PartyDTO> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.POST,
                entity,
                PartyDTO.class);
        assertEquals("New Candidate",response.getBody().getName());
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

    private ResponseEntity<List<PartyDTO>> getResponseFromAllParties() {
        HttpEntity<String> entity = new HttpEntity<>(null, headersForRequest);
        ResponseEntity<List<PartyDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<PartyDTO>>() {
                });
        return response;
    }
}