package com.example.Exam.configuration;

import com.example.Exam.entities.*;
import com.example.Exam.repositories.*;
import com.example.Exam.security.entities.ERole;
import com.example.Exam.security.entities.Role;
import com.example.Exam.security.entities.User;
import com.example.Exam.security.repositories.RoleRepository;
import com.example.Exam.security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!test")
public class DataSetup implements CommandLineRunner {
    CandidateRepository candidateRepository;
    VoteRepository voteRepository;
    PartyRepository partyRepository;
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public DataSetup(CandidateRepository candidateRepository,
                     VoteRepository voteRepository,
                     PartyRepository partyRepository,
                     RoleRepository roleRepository,
                     UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.voteRepository = voteRepository;
        this.partyRepository = partyRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Party a = new Party("A - Socialdemokratiet");
        Party c = new Party("C - Det konservative Folkeparti");
        Party f = new Party("F - SF, Socialistisk Folkeparti");
        Party o = new Party("O - Dansk Folkeparti");
        Party v = new Party("V - Venstre, Danmarks Liberale Parti");
        Party g = new Party("Ø - Enhedslisten + De Rød Grønne");

        partyRepository.save(a);
        partyRepository.save(c);
        partyRepository.save(f);
        partyRepository.save(o);
        partyRepository.save(v);
        partyRepository.save(g);

        Candidate aCandidate1 = new Candidate("Marcel Meijer", a);
        Candidate aCandidate2 = new Candidate("Michael Kristensen", a);
        Candidate aCandidate3 = new Candidate("Helle Hansen", a);
        Candidate aCandidate4 = new Candidate("Karina Knobelauch", a);
        Candidate aCandidate5 = new Candidate("Stefan Hafstein Wolffbrandt", a);
        Candidate aCandidate6 = new Candidate("Robert V. Rasmussen", a);
        Candidate aCandidate7 = new Candidate("Pia Ramsing", a);
        Candidate aCandidate8 = new Candidate("Anders Baun Sørensen", a);

        candidateRepository.save(aCandidate1);
        candidateRepository.save(aCandidate2);
        candidateRepository.save(aCandidate3);
        candidateRepository.save(aCandidate4);
        candidateRepository.save(aCandidate5);
        candidateRepository.save(aCandidate6);
        candidateRepository.save(aCandidate7);
        candidateRepository.save(aCandidate8);

        Candidate cCandidate1 = new Candidate("Per Urban Olsen", c);
        Candidate cCandidate2 = new Candidate("Peter Askjær", c);
        Candidate cCandidate3 = new Candidate("Martin Sørensen", c);
        Candidate cCandidate4 = new Candidate("Louise Bramstorp", c);
        Candidate cCandidate5 = new Candidate("Sigfred Jensen", c);
        Candidate cCandidate6 = new Candidate("Jørn C. Nissen", c);
        Candidate cCandidate7 = new Candidate("Morten Ø. Kristensen", c);
        Candidate cCandidate8 = new Candidate("Susanne Andersen", c);
        Candidate cCandidate9 = new Candidate("Iulian V. Paiu", c);
        Candidate cCandidate10 = new Candidate("Per Hingel", c);

        candidateRepository.save(cCandidate1);
        candidateRepository.save(cCandidate2);
        candidateRepository.save(cCandidate3);
        candidateRepository.save(cCandidate4);
        candidateRepository.save(cCandidate5);
        candidateRepository.save(cCandidate6);
        candidateRepository.save(cCandidate7);
        candidateRepository.save(cCandidate8);
        candidateRepository.save(cCandidate9);
        candidateRepository.save(cCandidate10);

        Candidate fCandidate1 = new Candidate("Ulla Holm", f);
        Candidate fCandidate2 = new Candidate("Kjeld Bønkel", f);
        Candidate fCandidate3 = new Candidate("Anne Grethe Olsen", f);
        Candidate fCandidate4 = new Candidate("Lone Krag", f);
        Candidate fCandidate5 = new Candidate("Børge S. Buur", f);

        candidateRepository.save(fCandidate1);
        candidateRepository.save(fCandidate2);
        candidateRepository.save(fCandidate3);
        candidateRepository.save(fCandidate4);
        candidateRepository.save(fCandidate5);

        Candidate oCandidate1 = new Candidate("Per Mortensen", o);

        candidateRepository.save(oCandidate1);

        Candidate vCandidate1 = new Candidate("Søren Wiese", v);
        Candidate vCandidate2 = new Candidate("Anita Elgaard Højholt Olesen", v);
        Candidate vCandidate3 = new Candidate("Carsten Bruun", v);
        Candidate vCandidate4 = new Candidate("Mogens Exner", v);
        Candidate vCandidate5 = new Candidate("Anja Guldborg", v);
        Candidate vCandidate6 = new Candidate("Klaus Holdorf", v);

        candidateRepository.save(vCandidate1);
        candidateRepository.save(vCandidate2);
        candidateRepository.save(vCandidate3);
        candidateRepository.save(vCandidate4);
        candidateRepository.save(vCandidate5);
        candidateRepository.save(vCandidate6);

        Candidate gCandidate1 = new Candidate("Katrine Høegh Mc Quaid", g);
        Candidate gCandidate2 = new Candidate("Jette M. Søgaard", g);
        Candidate gCandidate3 = new Candidate("Søren Caspersen", g);
        Candidate gCandidate4 = new Candidate("Pia Birkmand", g);

        candidateRepository.save(gCandidate1);
        candidateRepository.save(gCandidate2);
        candidateRepository.save(gCandidate3);
        candidateRepository.save(gCandidate4);

        Vote vote1 = new Vote("1434567895", gCandidate4);
        voteRepository.save(vote1);

        Vote vote2 = new Vote("1234527895", gCandidate2);
        voteRepository.save(vote2);
        Vote vote3 = new Vote("1234547895", vCandidate3);
        voteRepository.save(vote3);
        Vote vote4 = new Vote("1234167895", fCandidate2);
        voteRepository.save(vote4);
        Vote vote5 = new Vote("1264567895", gCandidate4);
        voteRepository.save(vote5);
        Vote vote6 = new Vote("1254567895", gCandidate4);
        voteRepository.save(vote6);
        Vote vote7 = new Vote("1234567891", aCandidate4);
        voteRepository.save(vote7);
        Vote vote8 = new Vote("1234867895", cCandidate7);
        voteRepository.save(vote8);
        Vote vote9 = new Vote("1234267895", vCandidate1);
        voteRepository.save(vote9);
        Vote vote10 = new Vote("1236267895", gCandidate4);
        voteRepository.save(vote10);
        Vote vote11 = new Vote("1237267895", vCandidate1);
        voteRepository.save(vote11);
        Vote vote12 = new Vote("1232467895", gCandidate4);
        voteRepository.save(vote12);


        Role adminRole = new Role();
        adminRole.setName(ERole.ROLE_ADMIN);

        roleRepository.save(adminRole);

        User admin = new User("admin", "admin@example.com", passwordEncoder.encode("test"));
        admin.addRole(adminRole);
        userRepository.save(admin);
    }
}
