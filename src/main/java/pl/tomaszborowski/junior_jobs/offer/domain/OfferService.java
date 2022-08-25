package pl.tomaszborowski.junior_jobs.offer.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepo offerRepo;

    public List<OfferDto> findAllOffers() {
        return offerRepo.findAll()
                .stream().map(OfferMapper::mapOfferToDto)
                .collect(Collectors.toList());
    }

    public OfferDto findOfferById(String id) {
        return offerRepo.findById(id).map(OfferMapper::mapOfferToDto).orElseThrow(() -> new OfferNotFoundException(id));
    }

}