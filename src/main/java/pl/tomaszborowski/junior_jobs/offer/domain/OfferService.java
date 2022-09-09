package pl.tomaszborowski.junior_jobs.offer.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferExistsException;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;

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
        List<Offer> offersDao = offers.stream().map(offerDto -> OfferMapper.mapToOffer(offerDto))
                .collect(Collectors.toList());
        List<Offer> offersSaved = offerRepo.saveAll(offersDao);
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
    public OfferDto createOrUpdateOffer(OfferDto offerDto) {
        OfferDto offerDtoElement;
        Offer offer;
        try{
            offer = offerRepo.save(OfferMapper.mapToOffer(offerDto));
            offerDtoElement = offer != null ? OfferMapper.mapOfferToDto(offer) : null;
        }
        catch (DuplicateKeyException exception){
            throw new OfferExistsException("There is already an offer with URL of " + offerDto.getOfferUrl());
        }
        return offerDtoElement;

    }
}