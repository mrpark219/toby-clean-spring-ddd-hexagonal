package mr.park.tobycleanspringdddhexagonal.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class MemberDetail extends AbstractEntity {
   private String profile;

   private String introduction;

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;
}
