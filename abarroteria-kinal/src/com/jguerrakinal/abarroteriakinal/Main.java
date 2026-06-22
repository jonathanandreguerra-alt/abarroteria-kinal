package com.jguerrakinal.abarroteriakinal;
import com.jguerrakinal.abarroteriakinal.util.SceneManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

    
public class Main extends Application{
    
    //atributos
    private static Stage primary;
    //notaciones
    @Override
    public void start(Stage mainScene){
        this.primary = mainScene;
mainScene.getIcons().add(new Image(getClass().getResourceAsStream("/com/jguerrakinal/abarroteriakinal/util/icon_kinal.png")));
       
        SceneManager manager = new SceneManager(primary);
       manager.mostrarLogin();
       mainScene.show();
    }
    
    //metodo
    public static void main(String[] args) {
           launch(); 
        
    }
    
}
