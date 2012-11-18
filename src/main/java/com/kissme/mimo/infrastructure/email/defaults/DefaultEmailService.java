package com.kissme.mimo.infrastructure.email.defaults;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.kissme.mimo.domain.EmailConf;
import com.kissme.mimo.infrastructure.email.EmailService;

/**
 * 
 * @author loudyn
 * 
 */
@Component
public class DefaultEmailService implements EmailService {

	private final Executor sendExecutor = Executors.newFixedThreadPool(1);

	@Override
	public void send(final EmailConf emailConf, final String subject, final String content) {
		sendExecutor.execute(new Runnable() {

			@Override
			public void run() {
				if (!emailConf.hasReceiver()) {
					return;
				}

				JavaMailSender mailSender = createMailSender(emailConf);
				mailSender.send(new MimeMessagePreparator() {

					@Override
					public void prepare(MimeMessage mimeMessage) throws Exception {
						MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

						helper.setFrom(emailConf.getSender());
						for (String recevicer : emailConf.getReceiversSet()) {
							helper.addTo(recevicer);
						}
						helper.setSubject(subject);
						helper.setText(content, true);
					}

				});
			}
		});
	}

	protected JavaMailSender createMailSender(EmailConf emailConf) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailConf.getSmtpServer());
		mailSender.setDefaultEncoding("utf-8");
		mailSender.setUsername(emailConf.getUsername());
		mailSender.setPassword(emailConf.getPassword());
		return mailSender;
	}

}
