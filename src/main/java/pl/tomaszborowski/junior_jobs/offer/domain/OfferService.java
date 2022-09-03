package pl.tomaszborowski.junior_jobs.offer.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
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

    @Cacheable(cacheNames = "jobOffersCached")
    public List<OfferDto> findAllOffers() {
        return offerRepo.findAll()
                .stream().map(OfferMapper::mapOfferToDto)
                .collect(Collectors.toList());
    }

    public OfferDto findOfferById(String id) {
        return offerRepo.findById(id).map(OfferMapper::mapOfferToDto).orElseThrow(() -> new OfferNotFoundException(id));
    }
    public List<OfferDto> saveOffers(List<OfferDto> offers) {
        List<Offer> offersSaved = offerRepo.saveAll(offers.stream().map(offerDto -> OfferMapper.mapToOffer(offerDto))
                .collect(Collectors.toList()));
        return offersSaved.stream().map(offer -> OfferMapper.mapOfferToDto(offer)).collect(Collectors.toList());
    }
    public List<OfferDto> saveOffersDirectlyFromHttpClient(List<pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDto> offersFromHttpClient) {
        List<Offer> offersDao = offersFromHttpClient.stream()
                .filter(offerDto -> !offerDto.getOfferUrl().equals(null))
                .filter(offerDto -> !offerRepo.existsByOfferUrl(offerDto.getOfferUrl()))
                .map(offerDto -> OfferMapper.mapToOfferFromHttpClientOfferDto(offerDto))
                .collect(Collectors.toList());
        offerRepo.saveAll(offersDao);
        return offersDao.stream().map(offer -> OfferMapper.mapOfferToDto(offer)).collect(Collectors.toList());
    }

}