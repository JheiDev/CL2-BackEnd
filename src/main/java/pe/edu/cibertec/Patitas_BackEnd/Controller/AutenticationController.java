package pe.edu.cibertec.Patitas_BackEnd.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.Patitas_BackEnd.dto.LogOutRequestDTO;
import pe.edu.cibertec.Patitas_BackEnd.dto.LogOutResponseDTO;
import pe.edu.cibertec.Patitas_BackEnd.dto.LoginRequestDTO;
import pe.edu.cibertec.Patitas_BackEnd.dto.LoginResponseDTO;
import pe.edu.cibertec.Patitas_BackEnd.service.AuthenticationService;
import pe.edu.cibertec.Patitas_BackEnd.service.implementation.AuthenticationServiceImpl;
import pe.edu.cibertec.Patitas_BackEnd.service.implementation.ServiceLogOut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@RestController
@RequestMapping("/authentication")
public class AutenticationController {

    @Autowired
    AuthenticationService authenticationService;

   @Autowired
    ServiceLogOut serviceLogOut;

    @PostMapping("/inicio")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
        try {
            Thread.sleep(Duration.ofSeconds(5));
            String[] datosUsuario = authenticationService.validarUsuario(loginRequestDTO);
            System.out.println(Arrays.toString(datosUsuario));
            if (datosUsuario == null) {
                return new LoginResponseDTO("01", "Error: Usuario no encontrado", "", "");
            }
            session.setAttribute("tipoDocumento", loginRequestDTO.tipoDocumento());
            session.setAttribute("numeroDocumento", loginRequestDTO.numeroDocumento());


            return new LoginResponseDTO("00", "", datosUsuario[0], datosUsuario[1]); // nombre y correo

        } catch (Exception e) {
            return new LoginResponseDTO("99", "Error: Ocurrió un problema", "", "");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LogOutResponseDTO> logout(@RequestBody LogOutRequestDTO logoutRequestDTO) {
        try {
            Thread.sleep(Duration.ofSeconds(5));

            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
                    logoutRequestDTO.tipoDocumento(),
                    logoutRequestDTO.numeroDocumento(),
                    ""
            );

             serviceLogOut.cerrarSesion(loginRequestDTO);

            return ResponseEntity.ok(new LogOutResponseDTO("00", "Cierre de sesión exitoso"));
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LogOutResponseDTO("02", "Error: operación interrumpida"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

