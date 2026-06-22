package com.jguerrakinal.abarroteriakinal.repository.autenticacion;

import com.jguerrakinal.abarroteriakinal.config.MySQLConnection;
import com.jguerrakinal.abarroteriakinal.model.usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AutenticacionRepository {

    public void save(Usuario usuario) {
        String sql = "INSERT INTO usaurios (id_usuario, nombre, apellido, email, contrasena, id_rol) VALUES (?,?,?,?,?,?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, usuario.getIdUsuario());
            pstm.setString(2, usuario.getNombre());
            pstm.setString(3, usuario.getApellido());
            pstm.setString(4, usuario.getEmail());
            pstm.setString(5, usuario.getContrasena());
            pstm.setString(6, usuario.getIdRol());

            int filas = pstm.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario registrado con exito: " + usuario.getNombre());
            }

        } catch (SQLException e) {
            System.out.println("Ocurrio un error al registrar el usuario: " + e.getMessage());
        }
    }
}