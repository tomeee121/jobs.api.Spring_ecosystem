package pl.tomaszborowski.junior_jobs.offer.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;


import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {


//    private final OfferRepo offerRepository;

//    @Autowired
//    MongoTemplate mongoTemplate;
//    @EventListener(ApplicationReadyEvent.class)
//    public void init(){
//        mongoTemplate.save(Arrays.asList(cyberSource(), cdqPoland()));
//        offerRepository.save(cyberSource());
//    }

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

    public List<OfferDto> findAllOffers() {
        final OfferDto cybersourceDto = OfferMapper.mapOfferToDto(
                "Software Engineer - Mobile (m/f/d)",
                "4k - 8k PLN",
                "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn",
                "Cybersource"
        );
        final OfferDto cdqPolandDto = OfferMapper.mapOfferToDto(
                "Junior DevOps Engineer",
                "8k - 14k PLN",
                "https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd",
                "CDQ Poland"
        );
        return Arrays.asList(cybersourceDto, cdqPolandDto);
    }

    public OfferDto findOfferById(long id) {
        if (id == 1L) {
            return OfferMapper.mapOfferToDto(
                    "Software Engineer - Mobile (m/f/d)",
                    "4k - 8k PLN",
                    "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn",
                    "Cybersource"
            );
        } else if (id == 2L) {
            return OfferMapper.mapOfferToDto(
                    "Junior DevOps Engineer",
                    "8k - 14k PLN",
                    "https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd",
                    "CDQ Poland"
            );
        }
        throw new OfferNotFoundException(id);
    }
}