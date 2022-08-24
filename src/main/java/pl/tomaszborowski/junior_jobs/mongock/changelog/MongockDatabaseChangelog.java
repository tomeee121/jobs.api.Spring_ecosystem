package pl.tomaszborowski.junior_jobs.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.tomaszborowski.junior_jobs.offer.domain.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferRepository;

import java.util.Arrays;

@ChangeLog(order = "1")
public class MongockDatabaseChangelog {

    @Autowired
    MongoTemplate mongoTemplate;

    @ChangeSet(order = "001", author = "tomasz.borowski", id = "two.offers.initializing")
    public void dataInitDB(OfferRepository offerRepository){
        mongoTemplate.save(Arrays.asList(cyberSource(), cdqPoland()));
//        offerRepository.insert(Arrays.asList(cyberSource(), cdqPoland()));
    }

    private Offer cyberSource() {
        final Offer cybersource = new Offer();
        cybersource.setOfferUrl("https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn");
        cybersource.setTitle("Software Engineer - Mobile (m/f/d)");
        cybersource.setSalary("4k - 8k PLN");
        cybersource.setCompany("Cybersource");
        return cybersource;
    }

    private Offer cdqPoland() {
        final Offer cybersource = new Offer();
        cybersource.setOfferUrl("https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd");
        cybersource.setTitle("Junior DevOps Engineer");
        cybersource.setSalary("8k - 14k PLN");
        cybersource.setCompany("CDQ Poland");
        return cybersource;
    }
}