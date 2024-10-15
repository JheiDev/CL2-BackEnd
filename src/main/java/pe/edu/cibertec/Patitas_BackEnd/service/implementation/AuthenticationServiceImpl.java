package pe.edu.cibertec.Patitas_BackEnd.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.Patitas_BackEnd.dto.LoginRequestDTO;
import pe.edu.cibertec.Patitas_BackEnd.service.AuthenticationService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException {

        String[] datosUsuario = null;

        Resource resource = resourceLoader.getResource("classpath:Users.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {
            String linea;
            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                if (datos.length < 5) {
                    System.out.println("Línea inválida: " + linea);
                    continue;
                }

                System.out.println("Datos leídos: " + Arrays.toString(datos));

                if (loginRequestDTO.tipoDocumento().equals(datos[0]) &&
                        loginRequestDTO.numeroDocumento().equals(datos[1]) &&
                        loginRequestDTO.password().equals(datos[2])) {

                    datosUsuario = new String[2];
                    datosUsuario[0] = datos[3]; // Recupera el nombre (índice 3)
                    datosUsuario[1] = datos[4]; // Recupera el correo (índice 4)
                    break;
                }
            }
        } catch (IOException e) {
            datosUsuario = null;
            throw new IOException(e);
        }

        return datosUsuario;
    }
}


