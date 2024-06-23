package com.login.claribel.service;

import com.login.claribel.dto.LoginDTO;
import com.login.claribel.model.Usuario;
import com.login.claribel.repository.UsuarioRepository;
import com.login.claribel.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String saveUsuario(Usuario usuario) {

        if (!isValidPassword(usuario.getPassword())) {
            throw new IllegalArgumentException("La contraseña debe tener al menos una letra mayúscula, una minúscula, un número, un símbolo y ser de al menos 8 caracteres");
        }

        Usuario usuario1 = new Usuario();
        String contraseñaEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(contraseñaEncriptada);


        usuarioRepository.save(usuario);
        return "Usuario creado con éxito";
    }

    //validar la contraseña
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex) && password.length() >= 8;
    }


    @Override
    public LoginResponse loginUsuario(LoginDTO loginDTO) {

        String msj ="";
        Usuario usuario1 = usuarioRepository.findByEmail(loginDTO.getEmail());

        if(usuario1 != null){
            String password = loginDTO.getPassword();
            String encodedPass = usuario1.getPassword();
            Boolean isPasswordRight = passwordEncoder.matches(password, encodedPass);
            if (isPasswordRight){
                Optional<Usuario> usuario = usuarioRepository.findByEmailAndPassword(loginDTO.getEmail(),encodedPass);
                if(usuario.isPresent()){
                    return new LoginResponse("Inicio de sesión exitoso", true);
                }else {
                    return new LoginResponse("Inicio de sesión fallido", false);
                }
            }else {
                return new LoginResponse("Password incorrecta", false);
            }
        }else{
                return new LoginResponse("Email incorrecto", false);
            }

    }


}
