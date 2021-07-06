package edu.summer.java;

import java.util.ResourceBundle;

/**
 * Class Controller is general controller contains app flow logic.
 * Contains references to:
 * @see ResourceBundle
 * @author Roman Bozhko
 * @version 1.0
 */
public class Controller {
    private final   ResourceBundle  bundle;

    public Controller(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void runApp() {
        new RecordController(bundle, new RecordView()).processInput();
    }
}
