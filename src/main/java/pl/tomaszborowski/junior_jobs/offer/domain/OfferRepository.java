package pl.tomaszborowski.junior_jobs.offer.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer,String> {
}
