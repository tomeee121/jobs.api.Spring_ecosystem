package pl.tomaszborowski.junior_jobs.offer.domain.Dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Document("offers")
public class Offer {

    @Id
    private String id;

    @Field("position")
    private String position;

    @Field("company")
    private String company;

    @Field("salary")
    private String salary;

    @Field("offerUrl")
    @Indexed(unique = true)
    private String offerUrl;
}
