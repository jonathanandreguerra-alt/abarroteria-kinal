package com.jguerrakinal.abarroteriakinal.service.autenticacion;
import com.jguerrakinal.abarroteriakinal.model.usuario.Usuario;
import com.jguerrakinal.abarroteriakinal.repository.autenticacion.AutenticacionRepository;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author informatica
 */
public class AutenticacionService {
 //atributos, un tipó de dato AutenticacionRepository
    private final AutenticacionRepository autenticacionRepository;
    
    /*inyectar la dependencia en el constructor
    si se intenta crear una instancia de este objeto nos vemos obligados
    a pasarle una instancia del repository asi aseguramos que el servicio tenga 
    de donde consumir
    */
    
    public AutenticacionService(AutenticacionRepository autenticacionRepository){
        this.autenticacionRepository = autenticacionRepository;
    }
    
    //metodo para validar un usuario
    
    public void crearUsuario(Usuario usuario){
        
        String hashContraseña = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt(12));
        Usuario usuarioARegistrar = new Usuario();
        usuarioARegistrar.setIdUsuario(usuario.getIdUsuario());
        usuarioARegistrar.setNombre(usuario.getNombre());
        usuarioARegistrar.setApellido(usuario.getApellido());
        usuarioARegistrar.setContrasena(hashContraseña);
        usuarioARegistrar.setEmail(usuario.getEmail());
        usuarioARegistrar.setIdRol(usuario.getIdRol());
        
        autenticacionRepository.save(usuarioARegistrar);
        
    }
    
}
