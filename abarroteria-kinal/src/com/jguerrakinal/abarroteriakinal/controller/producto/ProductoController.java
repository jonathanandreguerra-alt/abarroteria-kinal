package com.jguerrakinal.abarroteriakinal.controller.producto;

import com.jguerrakinal.abarroteriakinal.model.producto.Producto;
import com.jguerrakinal.abarroteriakinal.service.producto.ProductoService;
import java.util.List;

public class ProductoController {
    //atributos
    private ProductoService productoService;
    private List<Producto> listaProducto;
    //constructor
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }
    //metodos
    public List<Producto> handleGetProductos(){
        return productoService.getProductos();
    }
}
