import io.github.bootystar.autoconfigure.BootystarAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * @author bootystar
 */
public class ApplicationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(BootystarAutoConfiguration.class));
    @Test
    void defaultServiceBacksOff() {
        this.contextRunner.withUserConfiguration(BootystarAutoConfiguration.class)
                .run((context) -> {
//                    context.
//            assertThat(context).hasSingleBean(MyService.class);
//            assertThat(context).getBean("myCustomService").isSameAs(context.getBean(MyService.class));
        });
    }

   


}
