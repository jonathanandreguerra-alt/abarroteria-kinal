package com.jguerrakinal.abarroteriakinal.repository.producto;

import com.jguerrakinal.abarroteriakinal.config.MySQLConnection;
import java.sql.Connection;
import com.jguerrakinal.abarroteriakinal.model.producto.Producto;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ProductoRepository {
    
    private List<Producto> listaProducto;
    
    public ProductoRepository(){
        
        this.listaProducto =  new ArrayList<>();
        
    }
    
    public List<Producto> findAll(){
        
        String sql = "SELECT * FROM productos;";
        
        try(Connection conn = MySQLConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)){
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                
                listaProducto.add(
                new Producto(rs.getString("id_producto"), 
                                      rs.getString("nombre_producto"), 
                                      rs.getInt("stock"), 
                                      rs.getDouble("precio"))
                );
                
            }
            
        }catch(SQLException e){
            
            System.out.println("Error en la consulta" + " " + e.getMessage());
            
        }
        
        return listaProducto;
    }
    
}
