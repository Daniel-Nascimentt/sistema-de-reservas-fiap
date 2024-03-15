package br.com.fiap.email.msemail.service;

import br.com.fiap.email.msemail.dto.request.EmailRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSerivce {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.password}")
    private String key;

    @Async
    public void enviarEmail(@Valid EmailRequest request){

        try {
            System.out.println(key);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getDestinatario());
            message.setSubject(request.getTitulo());
            message.setText(request.getTexto());
            javaMailSender.send(message);
            System.out.println("EMAIL ENVIADO COM SUCESSO!!");
        } catch(MailException ex){

            // Apenas printa mensagem de erro.
            System.out.println(ex.getMessage());

        }
    }

}
