package pl.tomaszborowski.junior_jobs.offer.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;

public interface OfferRepo extends MongoRepository<Offer, String> {
}
