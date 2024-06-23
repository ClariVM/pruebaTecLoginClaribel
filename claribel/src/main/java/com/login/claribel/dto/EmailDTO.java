package com.login.claribel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor@AllArgsConstructor
public class EmailDTO {
    private String destinatario;
    public String asunto;
    private String mensaje;
    private String nuevaPass;

    public String getNuevaPass() {
        return nuevaPass;
    }

    public void setNuevaPass(String nuevaPass) {
        this.nuevaPass = nuevaPass;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
