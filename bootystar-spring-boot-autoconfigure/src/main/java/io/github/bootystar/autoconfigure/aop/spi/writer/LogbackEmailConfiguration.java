package io.github.bootystar.autoconfigure.aop.spi.writer;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.ThresholdFilter;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Programmatically configures a Logback appender to send emails on ERROR events.
 * This configuration is activated only if 'spring.mail.host' is configured.
 *
 * @author bootystar
 */
@Configuration
@ConditionalOnProperty("spring.mail.host")
public class LogbackEmailConfiguration {

    private final JavaMailSender mailSender;
    private final Environment environment;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Value("${bootystar.log.email.to:}") // Expects a comma-separated list
    private String[] toAddresses;

    public LogbackEmailConfiguration(JavaMailSender mailSender, Environment environment) {
        this.mailSender = mailSender;
        this.environment = environment;
    }

    @PostConstruct
    public void addEmailAppenderToLogback() {
        if (toAddresses == null || toAddresses.length == 0) {
            System.err.println("EmailAppender not configured: 'bootystar.log.email.to' is not set.");
            return;
        }

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Create our custom appender
        GlobalErrorEmailAppender appender = new GlobalErrorEmailAppender(
                mailSender,
                environment,
                fromAddress,
                toAddresses
        );
        appender.setContext(context);

        // Add a filter to only process ERROR level logs
        ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel(Level.ERROR.toString());
        filter.start();
        appender.addFilter(filter);

        // The appender must be started before use
        appender.start();

        // Attach the appender to the root logger
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(appender);

        System.out.println("GlobalErrorEmailAppender has been added to the root logger.");
    }
}
