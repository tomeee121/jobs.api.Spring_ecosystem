package pl.tomaszborowski.junior_jobs.offer;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/offers")
@RequiredArgsConstructor
public class OfferController {

    @Autowired
    OfferService offerService;

    @GetMapping
    public ResponseEntity<List<OfferDto>> findAllOffers() {
        if(offerService.findAllOffers().isEmpty()){
            log.error("Invoked allOffers endpoint with empty data");
            throw new OfferNotFoundException(-1);
        }
        log.info("Invoked allOffers endpoint with {} data", offerService.findAllOffers());
        return ResponseEntity.ok(offerService.findAllOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> findOfferById(@PathVariable long id) {
        if(offerService.findOfferById(id) == null){
            log.error("There is no offer with id of {}", id);
            throw new OfferNotFoundException(id);
        }
        return ResponseEntity.ok(offerService.findOfferById(id));
    }
}