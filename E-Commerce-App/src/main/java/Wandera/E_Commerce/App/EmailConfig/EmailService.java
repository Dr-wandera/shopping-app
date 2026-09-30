package Wandera.E_Commerce.App.EmailConfig;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public static final Logger log = LoggerFactory.getLogger(EmailService.class);


    //this sends notification to the seller
    @Async
    public void sendEmailToSeller(
            String to,
            String subject,
            String templateName,
            Map<String, String> variables
    ) throws MessagingException, IOException {

        String templatePath = "Template/" + templateName ;

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(templatePath);

        if (inputStream == null) {
            throw new FileNotFoundException("Email template not found: " + templatePath);
        }

        String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    // this sends email containing  the otp code for reset password that user forget
    @Async
    public void sendEmailResetToken(String to, String subject, String templateName, Map<String, String> variables)
            throws MessagingException, IOException {

        String templatePath = "Template/" + templateName;

        // this loads HTML template
        ClassLoader classLoader = getClass().getClassLoader();
        String htmlContent = new String(classLoader.getResourceAsStream(templatePath).readAllBytes());

        // Replace template values
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    //this sends verification token to the user vie email
    @Async
    public void sendOtpRegisterVerification(String to, String subject, String templateName, Map<String, String> variables)
            throws MessagingException, IOException {

        String templatePath = "Template/" + templateName;

        // this loads HTML template
        ClassLoader classLoader = getClass().getClassLoader();
        String htmlContent = new String(classLoader.getResourceAsStream(templatePath).readAllBytes());

        // Replace template values
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    //this resends the verification otp for user to verify/activate account
    @Async
    public void resendOtpEmail(String to, String subject, String templateName, Map<String, String> variables) throws MessagingException, IOException {

        String templatePath = "Template/" + templateName;

        // this loads HTML template
        ClassLoader classLoader = getClass().getClassLoader();
        String htmlContent = new String(classLoader.getResourceAsStream(templatePath).readAllBytes());

        // Replace template values
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    //this sends the email for the resend
    @Async
    public void sendOtp(String to, String subject, String templateName, Map<String, String> variables) throws IOException, MessagingException {
        String templatePath = "Template/" + templateName;

        // this loads HTML template
        ClassLoader classLoader = getClass().getClassLoader();
        String htmlContent = new String(classLoader.getResourceAsStream(templatePath).readAllBytes());

        // Replace template values
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }


    //this sends notification email to the buyer after placing the order
    @Async
    public void sendEmailToBuyer(
            String to,
            String subject,
            String templateName,
            Map<String, String> variables
    ) throws MessagingException, IOException {
        String templatePath = "Template/" + templateName + ".html";

        // this loads HTML template
        ClassLoader classLoader = getClass().getClassLoader();
        String htmlContent = new String(classLoader.getResourceAsStream(templatePath).readAllBytes());

        // Replace template values
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

}
