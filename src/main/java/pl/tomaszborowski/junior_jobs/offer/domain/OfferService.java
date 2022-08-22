package pl.tomaszborowski.junior_jobs.offer.domain;

import org.springframework.stereotype.Service;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;

import java.util.Arrays;
import java.util.List;

@Service
public class OfferService {

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