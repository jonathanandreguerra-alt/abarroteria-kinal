/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jguerrakinal.abarroteriakinal.view.autenticacion;

import com.jguerrakinal.abarroteriakinal.controller.autenticacion.AutenticacionController;
import com.jguerrakinal.abarroteriakinal.model.usuario.Usuario;
import com.jguerrakinal.abarroteriakinal.util.SceneManager;
import java.util.UUID;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author informatica
 */
public class RegistroView {
  
     //atributos
    private AutenticacionController autenticacionController;
    
     //constructor de la escena
    private SceneManager sceneManager;
    
     //componentes JavaFX.
    private GridPane gridPane;
    private StackPane stackPaneRoot;
    //nodos
    private Label labelNombre = new Label("Nombre: ");
    private TextField txtNombre = new TextField();
    private Label labelApellido = new Label("Apellido: ");
    private TextField txtApellido = new TextField();
    private Label labelEmail = new Label("Correo Electrónico: ");
    private TextField txtEmail = new TextField();
    private Label labelContrasena = new Label("Contraseña: ");
    private TextField txtContrasena = new TextField();
    private Label labelIdRol = new Label("Rol del Usuario: ");
    private TextField txtIdRol = new TextField();
    private Button btnRegistrarse = new Button("Registrarse");
    
    public RegistroView(AutenticacionController autenticacionController, SceneManager sceneManager) {
        this.autenticacionController = autenticacionController;
        this.sceneManager = sceneManager;
        this.gridPane = new GridPane();
        this.stackPaneRoot = new StackPane();
        buildUi();
    }

    private void buildUi(){
        
            //ColumnConstraint?: Porque nos permite realizar pantallas responsivas
      ColumnConstraints columnaUno = new ColumnConstraints();
      ColumnConstraints columnaDos = new ColumnConstraints();
      ColumnConstraints columnaTres = new ColumnConstraints();
      // filas
      RowConstraints filaUno = new RowConstraints();
      RowConstraints filaDos = new RowConstraints();      
      RowConstraints filaTres = new RowConstraints();
      
      //definir crecimiento de las filas y columnas
      columnaUno.setHgrow(Priority.ALWAYS);
      columnaDos.setHgrow(Priority.ALWAYS);
      columnaTres.setHgrow(Priority.ALWAYS);
      
      filaUno.setVgrow(Priority.ALWAYS);
      filaDos.setVgrow(Priority.ALWAYS);
      filaTres.setVgrow(Priority.ALWAYS);
      
        //CONSTRUCCION DE GRIDPANE
      gridPane.getColumnConstraints().addAll(columnaUno, columnaDos, columnaTres);
      gridPane.getRowConstraints().addAll(filaUno, filaDos, filaTres);
      gridPane.setPrefWidth(Double.MAX_VALUE);
      gridPane.setHgap(5);
      gridPane.setVgap(5);
      gridPane.setPadding(new Insets(5));
      
      // CONTENEDORES
      VBox datosRegistro = new VBox(10);
      datosRegistro.getChildren().addAll(
                                        labelNombre,
                                        txtNombre,
                                        labelApellido,
                                        txtApellido,
                                        labelEmail,
                                        txtEmail,
                                        labelContrasena,
                                        txtContrasena,
                                        labelIdRol,
                                        txtIdRol,
                                        btnRegistrarse
                                            );
      
 ;
      
      // constructor del grid con sus nodos
      gridPane.add(datosRegistro, 1, 1);
      stackPaneRoot.getChildren().add(gridPane);
        
    }
    
    // DEFINIMOS LAS ACCIONES 
    public void eventosBotones(){
     btnRegistrarse.setOnAction(
     e-> {
         Usuario usuario = new Usuario(
         UUID.randomUUID().toString(),
                 txtNombre.getText(),
                 txtApellido.getText(),
                 txtEmail.getText(),
                 txtContrasena.getText(),
                 txtIdRol.getText()
         );
         autenticacionController.handleCrearUsuario(usuario);
     }
     
     
     );
}
    
    
    
    // construccion de los eventos en botones
    
    public Parent getRoot(){
        
        return stackPaneRoot;
        
    }
    
}
