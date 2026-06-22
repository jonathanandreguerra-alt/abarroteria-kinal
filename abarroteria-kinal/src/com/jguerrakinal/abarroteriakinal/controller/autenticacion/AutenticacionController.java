package com.jguerrakinal.abarroteriakinal.controller.autenticacion;
import com.jguerrakinal.abarroteriakinal.model.usuario.Usuario;
import com.jguerrakinal.abarroteriakinal.service.autenticacion.AutenticacionService;
import com.jguerrakinal.abarroteriakinal.util.SceneManager;

/**
 *
 * @author informatica
 */
public class AutenticacionController {
    private final AutenticacionService autenticacionService;
    private final SceneManager stage;
    
    public AutenticacionController(AutenticacionService autenticacionService, SceneManager stage){
        this.autenticacionService = autenticacionService;
        this.stage = stage;
    }
    
    
    //metodos
    public void handleCrearUsuario(Usuario usuario){
        autenticacionService.crearUsuario(usuario);
    }
    
    public void handleMostrarRegistroView(){
        stage.mostrarRegistroView();
    }
    
    ///
    
}
