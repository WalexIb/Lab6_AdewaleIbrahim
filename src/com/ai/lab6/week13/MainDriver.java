package com.ai.lab6.week13;

// Import necessary JavaFX and other classes
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainDriver extends Application { 
// UI Components
    private Label lastRunResultLabel = new Label("Press 'Generate Lotto' to start 5 runs...");
    private Button startButton = new Button("Generate Lotto Numbers (5 Runs)");
    
    // Single shared DatabaseManager instance
    private final DatabaseManager dbManager = new DatabaseManager(); 

    @Override
    public void start(Stage primaryStage) {
        
        startButton.setOnAction(e -> startLottoRuns());

        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 20;");
        root.getChildren().addAll(
            new Label("Lotto Game Simulation"),
            new Label("Runs: 5 (using Threads) | Range: 0-100 | Persistence: MySQL"),
            startButton,
            new Label("-------------------------------------------------------"),
            lastRunResultLabel
        );
// Set up the stage
        primaryStage.setTitle("Lab6_AdewaleIbrahim - Threaded Lotto");
        primaryStage.setScene(new Scene(root, 550, 300));
        primaryStage.show();
    }
    // Method to start lotto runs in separate threads
    private void startLottoRuns() {
        startButton.setDisable(true);
        lastRunResultLabel.setText("Running threads...");
        
        for (int i = 1; i <= 5; i++) {
            LottoTask task = new LottoTask(i, dbManager, this);
            Thread thread = new Thread(task); 
            thread.start();
        }
    }
// Method to update the last result label from LottoTask
    public void updateLastResult(String result) {
        Platform.runLater(() -> {
            lastRunResultLabel.setText("Your lotto numbers are: " + result); 
            startButton.setDisable(false); 
        });
    }
// Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}