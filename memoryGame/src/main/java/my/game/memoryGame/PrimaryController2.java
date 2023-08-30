package my.game.memoryGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PrimaryController2 {
	@FXML private Label errorMSG;
	@FXML private Button card1;
	@FXML private Button card2;
	@FXML private Button card3;
	@FXML private Button card4;
	@FXML private Button card5;
	@FXML private Button card6;
	@FXML private Button card7;
	@FXML private Button card8;
	@FXML private Button card9;
	@FXML private Button card10;
	@FXML private Button card11;
	@FXML private Button card12;
	@FXML private Label timerLabel;
	
	private ArrayList<Button> allBtns = new ArrayList<Button>();
	private Button selectedBtns[] = new Button[2];
	private int cnt = 0; 
	private Timer myTimer;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public boolean initialize(Stage myStage) {
		for(int i = 0; i < 13; i++) { // Have to set the first position NULL.
			allBtns.add((Button) myStage.getScene().lookup("#card" + i));
			System.out.println(allBtns.get(i));
		}
		
		ArrayList<String> labels = new ArrayList<String>();
		int c1 = 1;
		int c2 = 1;
		for(int i = 0; i < 12; i++) {
			if(i % 2 == 0) {
				labels.add(i, c1+"");
				c1++;
			}else {
				labels.add(i, c2+"");
				c2++;
			}
		}
		
		Collections.shuffle(labels, new Random());
		
		for(int i = 1; i < 13; i++) {
			((Labeled) allBtns.get(i).lookup(".label")).setText(labels.get(i-1));
		}
		
		/*for(String element: labels) {
			System.out.println(element);
		}*/
		
		return true;
	}
	
	public void stop() {
		myTimer.cancel();
		
    	Platform.exit();
    }
	
	public void reset(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("app.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void selectCards(ActionEvent e) {
		
		if(cnt == 0) {
			selectedBtns[0] = (Button) e.getSource();
			selectedBtns[0].lookup(".label").setVisible(true);
			cnt++;
		}else if(cnt == 1) {
			
			Button tmp = (Button) e.getSource();
			if(tmp.getId().equals(selectedBtns[0].getId()) == true) {
				errorMSG.setVisible(true);
			}else {
				errorMSG.setVisible(false);
				selectedBtns[1] = (Button) e.getSource();
				selectedBtns[1].lookup(".label").setVisible(true);
				
				checkPair(e);
				cnt = 0;
			}
			
			//errorMSG.setVisible(false);
			
		}
		
	}
	
	public boolean checkPair(ActionEvent event) {
		Label label1 = (Label) selectedBtns[0].lookup(".label");
		Label label2 = (Label) selectedBtns[1].lookup(".label");
		
		String btnText1 = label1.getText();
		String btnText2 = label2.getText();
		System.out.println(btnText1+ " " + btnText2);
		
		if(btnText1.equals(btnText2) == true) {
			selectedBtns[0].setDisable(true);
			selectedBtns[1].setDisable(true);
			return true;
		}
		
		
		Stage tmpStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		if(tmpStage!= null) {
			Scene currentScene = tmpStage.getScene();
			
			EventHandler<KeyEvent> keyEventF = new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {
					e.consume();
				}
			};
			EventHandler<MouseEvent> mouseEventF = new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					e.consume();
				}
			};
			
			if(currentScene!= null) {
				currentScene.addEventFilter(KeyEvent.ANY, keyEventF);
				currentScene.addEventFilter(MouseEvent.ANY, mouseEventF);
			}
			
			myTimer = new Timer();
			myTimer.schedule(new TimerTask() {
				
				public void run() {
					selectedBtns[0].lookup(".label").setVisible(false);
					selectedBtns[1].lookup(".label").setVisible(false);
					selectedBtns[0] = null;
					selectedBtns[1] = null;
					currentScene.removeEventFilter(KeyEvent.ANY, keyEventF);
					currentScene.removeEventFilter(MouseEvent.ANY, mouseEventF);
				}
				
			}, 3500);
			
		}
		return false;
	}	
}

