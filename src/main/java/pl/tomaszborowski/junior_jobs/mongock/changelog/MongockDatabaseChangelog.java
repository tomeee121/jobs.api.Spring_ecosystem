package pl.tomaszborowski.junior_jobs.mongock.changelog;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferRepo;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.User;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.UserRepo;

import java.util.Arrays;

@ChangeLog(order = "1")
@Profile("!container")
public class MongockDatabaseChangelog {

    @ChangeSet(order = "001", author = "tomasz.borowski", id = "two.offers.initializing")
    public void dataInitDB(OfferRepo offerRepo){
        offerRepo.saveAll(Arrays.asList(cyberSource(), cdqPoland()));
    }

    @ChangeSet(order = "002", author = "tomasz.borowski", id = "user.admin.added")
    public void dataInitDB(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        userRepo.insert(user);
    }

    private Offer cyberSource() {
        final Offer cybersource = new Offer();
        cybersource.setOfferUrl("https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn");
        cybersource.setPosition("Software Engineer - Mobile (m/f/d)");
        cybersource.setSalary("4k - 8k PLN");
        cybersource.setCompany("Cybersource");
        return cybersource;
    }

    private Offer cdqPoland() {
        final Offer cybersource = new Offer();
        cybersource.setOfferUrl("https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd");
        cybersource.setPosition("Junior DevOps Engineer");
        cybersource.setSalary("8k - 14k PLN");
        cybersource.setCompany("CDQ Poland");
        return cybersource;
    }
}