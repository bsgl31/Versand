package versand;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import versand.core.ShippingObject;

import java.io.File;

public class Versand extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.setTitle("Versand");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> ShippingObject.saveObjects());
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
