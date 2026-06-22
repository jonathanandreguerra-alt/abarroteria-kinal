package com.jguerrakinal.abarroteriakinal.util;

import com.jguerrakinal.abarroteriakinal.controller.autenticacion.AutenticacionController;
import com.jguerrakinal.abarroteriakinal.repository.autenticacion.AutenticacionRepository;
import com.jguerrakinal.abarroteriakinal.service.autenticacion.AutenticacionService;
import com.jguerrakinal.abarroteriakinal.view.autenticacion.LoginView;
import com.jguerrakinal.abarroteriakinal.view.autenticacion.RegistroView;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneManager {
    //atributos
    private final Stage stage;

    
    //constructor
    public SceneManager(Stage stage){
       this.stage = stage;
   
       
    }
    
    //metodos
    public void mostrarLogin(){
        AutenticacionRepository autenticacionRepository = new AutenticacionRepository();
        AutenticacionService autenticacionService = new AutenticacionService(autenticacionRepository);
        AutenticacionController autenticacionController = new AutenticacionController(autenticacionService, this);
        LoginView loginView = new LoginView(autenticacionController, this );
        Scene scene = new Scene(loginView.getRoot(), 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Abarroteria Kinal");
        stage.centerOnScreen();
        scene.getStylesheets().add(getClass().getResource("/com/jguerrakinal/abarroteriakinal/util/styles.css").toExternalForm());
    }
    
    public void mostrarRegistroView(){
        AutenticacionRepository autenticacionRepository = new AutenticacionRepository();
        AutenticacionService autenticacionService = new AutenticacionService(autenticacionRepository);
        AutenticacionController autenticacionController = new AutenticacionController(autenticacionService, this);
        RegistroView registroView = new RegistroView(autenticacionController, this);
        Scene scene = new Scene(registroView.getRoot(), 600, 600);
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.getStylesheets().add(getClass().getResource("/com/jguerrakinal/abarroteriakinal/util/styles.css").toExternalForm());
        
}
    
}
