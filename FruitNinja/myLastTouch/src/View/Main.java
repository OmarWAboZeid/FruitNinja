package View;

import Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClassicView classicView = View.ClassicView.getInstance();
        Model model = Model.getInstance();
        ArcadeView arcadeView = ArcadeView.getInstance();
        MainMenu mainMenu = MainMenu.getInstance();
        controller = Controller.getInstance();
        controller.setModel(model);
        controller.setClassicView(classicView);
        controller.setArcadeView(arcadeView);
        controller.setStage(primaryStage);
        mainMenu.setController(controller);
        mainMenu.PrepareMainmenuscene();
        mainMenu.setArcade(arcadeView);
        primaryStage.setScene(mainMenu.getMainmenuscene());
        primaryStage.show();
    }
}
