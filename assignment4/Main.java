

import javafx.application.Application;

import java.io.IOException;

public class Main {
    public static void main(String[] args)throws IOException {
        ProgramManager.readProperties("properties.txt");
        ProgramManager.getData("backup.dat");
        Application.launch(LoginStage.class, args);
        ProgramManager.writeData("backup.dat");
    }
}
