package com.ai.lab6.week13;

// Import necessary JavaFX classes
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main JavaFX application class.
 * Sets up the GUI, captures user input, and initiates 5 concurrent lotto generation threads.
 */
public class MainDriver extends Application { 

    private Label lastRunResultLabel = new Label("Press 'Generate Lotto' to start 5 runs...");
    private Button startButton = new Button("Generate Lotto Numbers (5 Runs)");
    
    // GUI components for user input
    private TextField minNumberField = new TextField("0");
    private TextField maxNumberField = new TextField("100");
    private TextField countBallsField = new TextField("6"); 
    
    // Instantiate the DatabaseManager once
    private final DatabaseManager dbManager = new DatabaseManager(); 

    @Override
    public void start(Stage primaryStage) {
        
        startButton.setOnAction(e -> startLottoRuns());

        // HBox layout for Min/Max input
        HBox minMaxBox = new HBox(10, new Label("Min Number:"), minNumberField, 
                                      new Label("Max Number:"), maxNumberField);
        // HBox layout for Count input
        HBox countBox = new HBox(10, new Label("Ball Count:"), countBallsField);
        
        minMaxBox.setStyle("-fx-alignment: center-left;");
        countBox.setStyle("-fx-alignment: center-left;");

        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 20;");
        root.getChildren().addAll(
            new Label("Lotto Game Simulation"),
            new Label("Runs: 5 (using Threads) | Persistence: MySQL"),
            minMaxBox, 
            countBox,  
            startButton,
            new Label("-------------------------------------------------------"),
            lastRunResultLabel
        );

        primaryStage.setTitle("Lab6_AdewaleIbrahim - Threaded Lotto");
        primaryStage.setScene(new Scene(root, 550, 300));
        primaryStage.show();
    }
    
    /**
     * Reads user input and starts 5 concurrent LottoTask threads.
     */
    private void startLottoRuns() {
        startButton.setDisable(true);
        lastRunResultLabel.setText("Running threads...");
        
        try {
            // Read and parse input strings into integers
            int minNum = Integer.parseInt(minNumberField.getText());
            int maxNum = Integer.parseInt(maxNumberField.getText());
            int count = Integer.parseInt(countBallsField.getText());
            
            // Loop to initiate 5 separate threads
            for (int i = 1; i <= 5; i++) {
                // Create task, passing user inputs, and start a new thread
                LottoTask task = new LottoTask(i, dbManager, this, minNum, maxNum, count);
                Thread thread = new Thread(task); 
                thread.start();
            }
        } catch (NumberFormatException e) {
            // Catch error if user enters non-numeric text
            lastRunResultLabel.setText("ERROR: Invalid input. Please enter valid integer numbers for Min/Max/Count.");
            startButton.setDisable(false);
        }
    }

    /**
     * Safely updates the GUI label from a non-JavaFX thread.
     * @param result The generated lotto number string.
     */
    public void updateLastResult(String result) {
        // Platform.runLater ensures GUI updates happen on the correct thread
        Platform.runLater(() -> {
            lastRunResultLabel.setText("Your lotto numbers are: " + result); 
            startButton.setDisable(false); 
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}


