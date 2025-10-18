package io.github.bootystar.autoconfigure.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A custom Logback Appender that sends an email for ERROR level events.
 * This class is NOT a Spring Bean. It is instantiated and managed programmatically.
 *
 * @author bootystar
 */
public class GlobalErrorEmailAppender extends AppenderBase<ILoggingEvent> {

    private final JavaMailSender mailSender;
    private final Environment environment;
    private final String from;
    private final String[] to;

    public GlobalErrorEmailAppender(JavaMailSender mailSender, Environment environment, String from, String... to) {
        this.mailSender = mailSender;
        this.environment = environment;
        this.from = from;
        this.to = to;
        // The appender name is required by Logback
        setName("GLOBAL_EMAIL_APPENDER");
    }

    @Override
    protected void append(ILoggingEvent event) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);

            String appName = environment.getProperty("spring.application.name", "N/A");
            helper.setSubject(String.format("⚠️ Global Error in %s: %s", appName, event.getLoggerName()));

            String stackTrace = "No stack trace available.";
            if (event.getThrowableProxy() instanceof ThrowableProxy) {
                ThrowableProxy throwableProxy = (ThrowableProxy) event.getThrowableProxy();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwableProxy.getThrowable().printStackTrace(pw);
                stackTrace = sw.toString();
            }

            String port = environment.getProperty("server.port", "N/A");
            String body = buildHtmlEmailBody(appName, port, event.getLoggerName(), event.getFormattedMessage(), stackTrace);

            helper.setText(body, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Log to stderr to avoid infinite loops if email sending fails
            System.err.println("Error sending email notification from Appender: " + e.getMessage());
            addError("Error sending email notification", e);
        }
    }

    private String buildHtmlEmailBody(String appName, String port, String loggerName, String message, String stackTrace) {
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "<style>" +
               "body {font-family: Arial, sans-serif; margin: 20px; color: #333;}" +
               "h2 {color: #D32F2F; border-bottom: 2px solid #f2f2f2; padding-bottom: 10px;}" +
               "table {border-collapse: collapse; width: 100%; margin-top: 20px; border: 1px solid #ddd;}" +
               "th, td {text-align: left; padding: 12px; border-bottom: 1px solid #ddd;}" +
               "th {background-color: #f8f8f8; font-weight: bold; width: 120px;}" +
               "pre {white-space: pre-wrap; word-wrap: break-word; background-color: #f5f5f5; padding: 15px; border-radius: 4px; border: 1px solid #ccc; font-family: 'Courier New', Courier, monospace;}" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<h2>Application Error Report</h2>" +
               "<table>" +
               "<tr><th>Application</th><td>" + appName + "</td></tr>" +
               "<tr><th>Port</th><td>" + port + "</td></tr>" +
               "<tr><th>Logger</th><td>" + loggerName + "</td></tr>" +
               "<tr><th>Message</th><td>" + message + "</td></tr>" +
               "</table>" +
               "<h3>Stack Trace</h3>" +
               "<pre>" + stackTrace + "</pre>" +
               "</body>" +
               "</html>";
    }
}
