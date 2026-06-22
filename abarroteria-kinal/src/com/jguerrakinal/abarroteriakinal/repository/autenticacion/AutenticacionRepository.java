/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jguerrakinal.abarroteriakinal.repository.autenticacion;

import com.jguerrakinal.abarroteriakinal.config.MySQLConnection;
import com.jguerrakinal.abarroteriakinal.model.usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;



public class AutenticacionRepository {
    
    public void save(Usuario usuario){
        String sql = "Insert into usaurios values (?,?,?,?,?,?)";
        try (Connection conn = MySQLConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)){
        pstm.setString(1, usuario.getIdUsuario());
        pstm.setString(2, usuario.getNombre());
        pstm.setString(3, usuario.getApellido());
        pstm.setString(4, usuario.getEmail());
        pstm.setString(6, usuario.getIdRol());
        
        if (pstm.execute()){
            System.out.println("Usuario registrado con exito: "
                                + usuario.getIdUsuario()
                                + "nombre: " + usuario.getNombre());
        }
        
    }catch(SQLException e){
            System.out.println("Ocurrio un error al registrar el usuario" + e.getMessage());
    }
    
 }
}
