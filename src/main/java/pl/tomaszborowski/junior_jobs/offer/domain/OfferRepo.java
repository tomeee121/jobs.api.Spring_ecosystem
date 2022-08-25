package pl.tomaszborowski.junior_jobs.offer.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepo extends MongoRepository<Offer, String> {
}
