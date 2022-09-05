package pl.tomaszborowski.junior_jobs.offer;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/offers")
@RequiredArgsConstructor
public class OfferController {

    OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<OfferDto>> findAllOffers() {
        if(offerService.findAllOffers().isEmpty()){
            log.error("Invoked allOffers endpoint with empty data");
            throw new OfferNotFoundException("-1");
        }
        log.info("Invoked allOffers endpoint with {} data", offerService.findAllOffers());
        return ResponseEntity.ok(offerService.findAllOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> findOfferById(@PathVariable String id) {
        return ResponseEntity.ok(offerService.findOfferById(id));
    }

    @PostMapping
    public ResponseEntity<OfferDto> createOrUpdateOffer(@Valid @RequestBody OfferDto offerDto) {
        if (offerDto.getId() != null) {
            log.info("Updating offer with id {} ", offerDto.getId());
            return new ResponseEntity<>(offerService.createOrUpdateOffer(offerDto), HttpStatus.OK);
        }
        log.info("Added new offer");
        return new ResponseEntity<>(offerService.createOrUpdateOffer(offerDto), HttpStatus.CREATED);
    }
}