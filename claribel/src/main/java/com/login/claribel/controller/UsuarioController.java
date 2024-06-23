package com.login.claribel.controller;

import com.login.claribel.dto.LoginDTO;


import com.login.claribel.dto.EmailDTO;
import com.login.claribel.model.Usuario;
import com.login.claribel.repository.UsuarioRepository;
import com.login.claribel.response.LoginResponse;
import com.login.claribel.service.IUsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JavaMailSender javaMailSender;


    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario) {

        if (usuario.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un nombre");
        }

        if (usuario.getApellido().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un apellido");
        }
        if (usuario.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un email");
        } else if (!usuario.getEmail().contains("@")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email debe contener '@'");
        }


        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya tiene un registro, ingrese otro");
        }

        if (usuario.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar una contraseña");
        }


        usuarioService.saveUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con éxito.");
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginDTO loginDTO) {
        System.out.println("Contraseña recibida en el backend: " + loginDTO.getPassword());
        LoginResponse loginResponse = usuarioService.loginUsuario(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUsuario(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }


}



