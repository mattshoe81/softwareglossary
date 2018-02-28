package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class resets the text fields to the empty string.
 *
 * @author matts
 *
 */
public class ResetButtonListener implements ActionListener {

    @Override
    public final void actionPerformed(ActionEvent arg0) {
        Controller.resetTextFields();
        Controller.updateStatus("Ready");
    }

}
