package com.login.claribel.controller;

import com.login.claribel.dto.EmailDTO;
import com.login.claribel.model.Usuario;
import com.login.claribel.repository.UsuarioRepository;
import com.login.claribel.service.IEmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    IEmailService emailService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/olvidar")
    public ResponseEntity<String> enviarEmail(@RequestBody EmailDTO emailDTO) throws MessagingException {
        Usuario usuario = usuarioRepository.findByEmail(emailDTO.getDestinatario());
        if (usuario == null) {
            return ResponseEntity.badRequest().body("No se encontró ningún usuario con este email.");
        }

        String nuevaContrasena = generarNuevaContrasenaAleatoria();
        String encryptedPassword = passwordEncoder.encode(nuevaContrasena);
        usuario.setPassword(encryptedPassword);
        usuarioRepository.save(usuario);


        String mensaje = "Tu nueva contraseña es: " + nuevaContrasena;
        emailDTO.setMensaje(mensaje);
        emailDTO.setNuevaPass(nuevaContrasena);
        emailService.enviarCorreo(emailDTO);

        return ResponseEntity.ok("Contraseña actualizada y correo enviado exitosamente.");
    }

    private String generarNuevaContrasenaAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();

        int longitud = 8;

        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }

        return sb.toString();
    }


}
