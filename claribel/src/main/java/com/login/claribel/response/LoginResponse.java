package com.login.claribel.response;


public class LoginResponse {

    String mensaje;
    Boolean status;


    @Override
    public String toString() {
        return "loginResponse{" +
                "mensaje='" + mensaje + '\'' +
                ", status=" + status +
                '}';
    }

    public LoginResponse() {
    }

    public LoginResponse(String mensaje, Boolean status) {
        this.mensaje = mensaje;
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
