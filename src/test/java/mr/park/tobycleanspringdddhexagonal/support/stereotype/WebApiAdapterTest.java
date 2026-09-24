package mr.park.tobycleanspringdddhexagonal.support.stereotype;

import mr.park.tobycleanspringdddhexagonal.SplearnTestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Import(SplearnTestConfiguration.class)
public @interface WebApiAdapterTest {
}
