package com.login.claribel.service;


import com.login.claribel.dto.LoginDTO;
import com.login.claribel.model.Usuario;
import com.login.claribel.response.LoginResponse;

public interface IUsuarioService {

    String saveUsuario(Usuario usuario);

    LoginResponse loginUsuario(LoginDTO loginDTO);



}
