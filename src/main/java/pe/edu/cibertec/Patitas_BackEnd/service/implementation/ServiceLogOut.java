package pe.edu.cibertec.Patitas_BackEnd.service.implementation;

import org.springframework.stereotype.Service;
import pe.edu.cibertec.Patitas_BackEnd.dto.LogOutRequestDTO;
import pe.edu.cibertec.Patitas_BackEnd.dto.LogOutResponseDTO;
import pe.edu.cibertec.Patitas_BackEnd.dto.LoginRequestDTO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ServiceLogOut {

    private static final String path = "src/main/resources/LogOut.csv";

    public LogOutResponseDTO cerrarSesion(LogOutRequestDTO logoutRequestDTO) {
        try {
            userLogout(logoutRequestDTO); // Registra el cierre de sesión
            return new LogOutResponseDTO("00", "Cierre de sesión exitoso");
        } catch (IOException e) {
            return new LogOutResponseDTO("99", "Error al cerrar sesión");
        }
    }

    // Ajusta el método `userLogout` para que acepte `LogoutRequestDTO`
    private void userLogout(LogOutRequestDTO logoutRequestDTO) throws IOException {
        File file = new File(path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(logoutRequestDTO.tipoDocumento() + ";" + logoutRequestDTO.numeroDocumento() + ";" + LocalDate.now() + "\n");
        }
    }
}


