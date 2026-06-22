package com.jguerrakinal.abarroteriakinal.service.producto;
import com.jguerrakinal.abarroteriakinal.model.producto.Producto;
import com.jguerrakinal.abarroteriakinal.repository.producto.ProductoRepository;
import java.util.List;
import com.jguerrakinal.abarroteriakinal.exception.ServicioException;

public class ProductoService {
    /*Validaciones complejas, lógica de negocio*/
    //atributos
    private ProductoRepository productoRepository;
    // metodos

    public ProductoService(ProductoRepository productoRepository){
        if (productoRepository == null){
            throw new IllegalArgumentException("El repositorio no puede ser nulo. Arquitectura incorrecta.");
        }
    this.productoRepository = productoRepository;
}
    
    
    
    // metodo
    public List<Producto> getProductos(){
        List<Producto> productos = productoRepository.findAll();
 
    // 1. Validar que no sea null

    if (productos == null) {

        throw new RuntimeException("La lista de productos es null");

    }
 
    // 2. Validar que no esté vacía

    if (productos.isEmpty()) {

        throw new RuntimeException("No hay productos registrados");

    }
 
    // 3. Validar datos internos de cada producto

    for (Producto p : productos) {
 
        // 4. Validar nombre

        if (p.getNombreProducto() == null ||

            p.getNombreProducto().trim().isEmpty()) {

            throw new RuntimeException("Producto con nombre inválido");

        }
 
        // 5. Validar stock

        if (p.getStock() < 0) {

            throw new RuntimeException(

                "Producto con stock negativo: " + p.getIdProducto()

            );

        }
 
        // 6. Validar precio

        if (p.getPrecio() <= 0) {

            throw new RuntimeException(

                "Producto con precio inválido: " + p.getIdProducto()

            );

        }
 
        // 7. Validar ID

        if (p.getIdProducto() == null ||

            p.getIdProducto().trim().isEmpty()) {

            throw new RuntimeException("Producto con ID inválido");

        }

    }
 
        
        return productoRepository.findAll();
    }
}
