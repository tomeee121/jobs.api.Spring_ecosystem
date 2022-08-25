package pl.tomaszborowski.junior_jobs.offer;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

import java.util.List;

@RestController
@RequestMapping(value = "/offers")
@RequiredArgsConstructor
public class OfferController {

    @Autowired
    OfferService offerService;

    @GetMapping
    public ResponseEntity<List<OfferDto>> findAllOffers() {
        return ResponseEntity.ok(offerService.findAllOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> findOfferById(@PathVariable long id) {
        return ResponseEntity.ok(offerService.findOfferById(id));
    }
}