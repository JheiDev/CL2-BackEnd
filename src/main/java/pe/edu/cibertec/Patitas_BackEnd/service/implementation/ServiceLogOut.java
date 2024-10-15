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

    public LogOutResponseDTO cerrarSesion(LoginRequestDTO logoutRequestDTO) throws IOException {
        try {
            userLogout(logoutRequestDTO);
            return new LogOutResponseDTO("00", "Cierre de sesión exitoso");
        } catch (IOException e) {
            return new LogOutResponseDTO("99", "Error al cerrar sesión");
        }
    }


    public void userLogout(LoginRequestDTO logoutRequestDTO) throws IOException {
        File file = new File(path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(logoutRequestDTO.tipoDocumento() + ";" + logoutRequestDTO.numeroDocumento() + ";" + LocalDate.now() + "\n");
        } catch (IOException e) {
            throw new IOException("Error al escribir en LogOut.csv", e);
        }
    }
}



