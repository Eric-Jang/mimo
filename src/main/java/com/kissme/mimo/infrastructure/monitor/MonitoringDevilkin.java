package com.kissme.mimo.infrastructure.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.domain.monitor.MonitoringContext;
import com.kissme.mimo.domain.ConfsRepository;
import com.kissme.mimo.domain.EmailConf;
import com.kissme.mimo.domain.MonitoringRecord;
import com.kissme.mimo.infrastructure.email.EmailService;

/**
 * 
 * @author loudyn
 * 
 */
@Aspect
@Component
public class MonitoringDevilkin {
	private static final String MONITORING_EXP = "@annotation(com.kissme.core.domain.monitor.Monitoring)&&@annotation(monitoring)";

	@Autowired
	private EmailService emailService;

	@Autowired
	private ConfsRepository confsRepository;

	/**
	 * 
	 * @param jp
	 * @param monitoring
	 */
	@AfterReturning(pointcut = MONITORING_EXP)
	public void monitoring(Monitoring monitoring) {

		MonitoringContext context = MonitoringContext.get();
		long elapsedTime = System.currentTimeMillis() - context.getCreateTime();
		MonitoringRecord record = new MonitoringRecord();
		record.setAction(monitoring.action()).setActor(context.getActor()).setElapsedTime(elapsedTime);
		record.setSource(context.getSource()).setTarget(context.getTarget()).create();

		EmailConf emailConf = confsRepository.getEmailConf();
		if (emailConf.supportEvent(monitoring.action())) {
			emailService.send(emailConf, "你的站点有新的消息提醒", createContent(record));
		}
	}

	private String createContent(MonitoringRecord record) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new StringBuilder().append("用户：").append(record.getActor()).append(";")
									.append("时间：").append(sdf.format(new Date(record.getCreateTime()))).append(";")
									.append("操作：").append(record.getAction()).append(";")
									.append("IP：").append(record.getSource()).append(";")
									.append("访问网址：").append(record.getTarget()).append(";")
									.toString();
	}
}
