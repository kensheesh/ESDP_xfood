package kg.attractor.xfood.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Appeal;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final AppealRepository appealRepository;
    private final CheckListRepository checkListRepository;

    @Value("${spring.mail.username}")
    private String EMAIL_FROM;

    @Override
    public void sendEmail(Long appealsId, String supervisorsMessage) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        User user = userRepository.findByEmail(AuthParams.getPrincipal().getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
        Appeal appeal = appealRepository.findById(appealsId).orElseThrow(() -> new NotFoundException("Appeal not found"));
        CheckListsCriteria checkListsCriteria = appeal.getCheckListsCriteria();
        CheckList checkList = checkListRepository.findByCheckListsCriteriaId(checkListsCriteria.getId());

        helper.setFrom(EMAIL_FROM, "ДОДО ПИЦЦА");
        helper.setTo(appeal.getEmail());


        String decision;
        if (Boolean.TRUE.equals(appeal.getIsAccepted())) {
            decision = "Удовлетворена";
        } else {
            decision = "Отклонена";
        }

//        String date = checkList.getOpportunity().getDate().toString();
        String manager = appeal.getFullName();

        String subject = "Решение по апелляции";
        String content = "<p>Дата проверки: " + "<br>"
                + "Менеджер: " + manager + "<br>"
                + "Критерий: " + appeal.getCheckListsCriteria().getValue() + " " + appeal.getCheckListsCriteria().getCriteria().getDescription() + "<br>"
//                + "Апелляция: " + appeal.getComment() + "</p>"

                + "<p>Решение по апелляции: " + decision + "<br>"
                + "Комментарий: " + supervisorsMessage + " </p>"
                + "<br>"
                + "<strong>" + user.getName() + " " + user.getSurname() + "</strong>" + "<br>"
                + "Менеджер по контролю качества" + "<br>"
                + "Сеть Дмитрия Сотира" + "<br>"
                + EMAIL_FROM + "</p>";


        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
	    log.info("sent email to {}", user.getEmail());
    }
}
