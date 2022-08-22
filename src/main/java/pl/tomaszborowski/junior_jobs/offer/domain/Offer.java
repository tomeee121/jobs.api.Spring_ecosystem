package pl.tomaszborowski.junior_jobs.offer.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document("offer")
public class Offer {

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("company")
    private String company;

    @Field("salary")
    private String salary;

    @Field("offerUrl")
    private String offerUrl;
}
