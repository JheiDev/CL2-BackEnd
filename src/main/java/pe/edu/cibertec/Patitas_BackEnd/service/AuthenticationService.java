package pe.edu.cibertec.Patitas_BackEnd.service;

import pe.edu.cibertec.Patitas_BackEnd.dto.LoginRequestDTO;

import java.io.IOException;

public interface AuthenticationService {
    String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException;
}
