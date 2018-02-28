package view;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import controller.GoButtonListener;
import controller.ResetButtonListener;
import model.Model;

/**
 * This class constructs the gui.
 *
 * @author matts
 *
 */
public class GlossaryGUI extends JFrame {

    /**
     * No idea what this is, Eclipse suggested it.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor.
     */
    public GlossaryGUI(Model model) {
        super();
        this.model = model;
    }

    private Model model;

    /**
     * construc the GUI.
     */
    public void buildGUI() {
        JPanel background = new JPanel();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));

        // Create panel to hold text fields
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));

        // Create panel containing the file location text field
        JPanel fileLocationTextPanel = new JPanel();
        fileLocationTextPanel.setLayout(new FlowLayout());
        // Label for text box
        JLabel fileLocationText = new JLabel("File Path: ");
        fileLocationTextPanel.add(fileLocationText);
        fileLocationTextPanel.add(this.model.getFileLocationField());

        // Create panel containing the folder location
        JPanel folderLocationTextPanel = new JPanel();
        fileLocationTextPanel.setLayout(new FlowLayout());
        // Label for text box
        JLabel folderLocationText = new JLabel("Folder:     ");
        folderLocationTextPanel.add(folderLocationText);
        folderLocationTextPanel.add(this.model.getFolderLocationField());

        // Create a status label and panel to hold it (to keep it centered)
        JPanel statusPanel = new JPanel();
        statusPanel.add(this.model.getStatusLabel());

        // Add file panel, folder panel, and status panel to the text area panel
        textAreaPanel.add(fileLocationTextPanel);
        textAreaPanel.add(folderLocationTextPanel);
        textAreaPanel.add(statusPanel);

        // Create the panel for buttons
        JPanel buttonPanel = new JPanel();

        // Create the reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetButtonListener());
        buttonPanel.add(resetButton);

        //Create the button to run the program
        JButton goButton = new JButton("Go");
        goButton.addActionListener(new GoButtonListener(this.model));
        buttonPanel.add(goButton);

        // Add Text area panel and go button to the background panel
        background.add(textAreaPanel);
        background.add(buttonPanel);

        // Add background to frame (this class)
        this.add(background);

        this.pack();
        this.setVisible(true);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        Model model = new Model();
        Controller controller = new Controller(model);
        GlossaryGUI gui = new GlossaryGUI(model);
        gui.buildGUI();

    }

}
