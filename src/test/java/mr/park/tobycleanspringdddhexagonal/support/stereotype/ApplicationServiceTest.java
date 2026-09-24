package mr.park.tobycleanspringdddhexagonal.support.stereotype;

import jakarta.transaction.Transactional;
import mr.park.tobycleanspringdddhexagonal.SplearnTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
public @interface ApplicationServiceTest {
}
