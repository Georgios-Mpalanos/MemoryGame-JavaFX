package my.game.memoryGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    private Stage myStage;
	private Scene myScene;
	private Parent myRoot;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("app"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void switchToFirstMode(ActionEvent event) throws IOException {
		myRoot = FXMLLoader.load(getClass().getResource("sixMode.fxml"));
		myStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		myScene = new Scene(myRoot);
		myStage.setScene(myScene);
		myStage.show();
		myStage.setResizable(false);
		
		PrimaryController primaryController = new PrimaryController();
		primaryController.initialize(myStage);
	}
    
    public void switchToSecondMode(ActionEvent event) throws IOException {
		myRoot = FXMLLoader.load(getClass().getResource("twelveMode.fxml"));
		myStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		myScene = new Scene(myRoot);
		myStage.setScene(myScene);
		myStage.show();
		myStage.setResizable(false);
		
		PrimaryController2 primaryController2 = new PrimaryController2();
		primaryController2.initialize(myStage);
	}
    
    @Override
    public void stop() {
    	Platform.exit();
    }
}