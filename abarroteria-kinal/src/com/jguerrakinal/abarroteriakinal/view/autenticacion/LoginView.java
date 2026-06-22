package com.jguerrakinal.abarroteriakinal.view.autenticacion;

import com.jguerrakinal.abarroteriakinal.controller.autenticacion.AutenticacionController;
import com.jguerrakinal.abarroteriakinal.util.SceneManager;
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


public class LoginView {
    //atributos
    private AutenticacionController autenticacionController;
    
    //constructor de la escena
    private SceneManager sceneManager;
    
    //componentes JavaFX.
    private GridPane gridPane;
    private StackPane stackPaneRoot;
    
    //nodos 
    private Label labelEmail = new Label("Email");
    private TextField txtFieldEmail = new TextField();
    private Label labelContrasena = new Label("Contraseña");
    private TextField txtFieldContrasena = new TextField();
    private Button btnIniciarSesion = new Button("Iniciar sesion");
    private Button btnRegistrar = new Button("Registrar");
     
    
    //constructor
    public LoginView(AutenticacionController autenticacionController, SceneManager sceneManager){
        this.autenticacionController = autenticacionController;
        this.sceneManager = sceneManager;
        this.gridPane = new GridPane();
        this.stackPaneRoot = new StackPane();
        buildUi();
        eventosBotones();
    }
    
    //metodos
    public void buildUi(){
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
      
        //CONSTRUCCION DE LOS CONTENEDORES PARA LOS NODOS
      VBox datosUsuario = new VBox(5);
      datosUsuario.getChildren().addAll(
                                        labelEmail,
                                        txtFieldEmail,
                                        labelContrasena,
                                        txtFieldContrasena);
      
      HBox accionesUsuario = new HBox(5);
      accionesUsuario.getChildren().addAll(btnIniciarSesion, btnRegistrar);
        
      
      VBox contenedorCentral = new VBox(10);
      contenedorCentral.getChildren().addAll(datosUsuario, accionesUsuario);
      
      gridPane.add(contenedorCentral, 1, 1);
      stackPaneRoot.getChildren().add(gridPane);
    }
    
    public void eventosBotones(){
        btnRegistrar.setOnAction(
        e -> {
        autenticacionController.handleMostrarRegistroView();
        }
        
        );
        
        
    }
    
    
    //retornamos el contenedor raiz para poder crear
    //la escena en el Scene Manager
    
    public Parent getRoot(){
        return stackPaneRoot;
        
    }
}
