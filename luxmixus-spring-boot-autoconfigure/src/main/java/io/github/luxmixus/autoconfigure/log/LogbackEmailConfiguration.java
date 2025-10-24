package io.github.luxmixus.autoconfigure.log;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.ThresholdFilter;
import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Programmatically configures a Logback appender to send emails on ERROR events.
 * This configuration is activated only if 'spring.mail.host' is configured.
 *
 * @author luxmixus
 */
@Configuration
@ConditionalOnProperty("spring.mail.host")
public class LogbackEmailConfiguration implements ApplicationListener<ApplicationFailedEvent> {

    private final JavaMailSender mailSender;
    private final Environment environment;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Value("${luxmixus.log.email.to:}") // Expects a comma-separated list
    private String[] toAddresses;

    public LogbackEmailConfiguration(JavaMailSender mailSender, Environment environment) {
        this.mailSender = mailSender;
        this.environment = environment;
    }

    @PostConstruct
    public void addEmailAppenderToLogback() {
        if (toAddresses == null || toAddresses.length == 0) {
            System.err.println("EmailAppender not configured: 'luxmixus.log.email.to' is not set.");
            return;
        }

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Create our custom appender
        GlobalErrorEmailAppender emailAppender = new GlobalErrorEmailAppender(
                mailSender,
                environment,
                fromAddress,
                toAddresses
        );
        emailAppender.setContext(context);

        // Add a filter to only process ERROR level logs
        ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel(Level.ERROR.toString());
        filter.start();
        emailAppender.addFilter(filter);

        // The appender must be started before use
        emailAppender.start();

        // Wrap the email appender in an async appender
        AsyncAppender asyncAppender = new AsyncAppender();
        asyncAppender.setContext(context);
        asyncAppender.addAppender(emailAppender);
        asyncAppender.setQueueSize(256); // Set queue size
        asyncAppender.setDiscardingThreshold(0); // Never drop logs unless queue is full
        asyncAppender.start();


        // Attach the async appender to the root logger
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(asyncAppender);

        System.out.println("GlobalErrorEmailAppender has been added to the root logger via AsyncAppender.");
    }

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        if (toAddresses == null || toAddresses.length == 0) {
            return; // Not configured to send email
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String appName = environment.getProperty("spring.application.name", "Application");
            helper.setSubject(appName + " - Application Failed to Start");
            helper.setFrom(fromAddress);
            helper.setTo(toAddresses);

            Throwable exception = event.getException();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);

            String body = "The application failed to start.\n\n" +
                    "Exception: " + exception.getMessage() + "\n\n" +
                    "Stack Trace:\n" + sw.toString();
            helper.setText(body);

            mailSender.send(message);
            System.out.println("Successfully sent startup failure email notification.");
        } catch (Exception e) {
            // Use System.err because the logger might not be initialized.
            System.err.println("Failed to send startup failure email notification.");
            e.printStackTrace(System.err);
        }
    }
}
