package pl.tomaszborowski.junior_jobs.offer;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDtoSamples;

@WebMvcTest
@ContextConfiguration(classes = OfferControllerConfig.class)
class OfferControllerTest implements OfferDtoSamples {


}