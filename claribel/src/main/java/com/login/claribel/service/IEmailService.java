package com.login.claribel.service;

import com.login.claribel.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface IEmailService {

    public void enviarCorreo(EmailDTO emailDTO) throws MessagingException;
}
