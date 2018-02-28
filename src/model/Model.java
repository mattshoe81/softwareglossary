package model;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Program that will read terms and definitions from a .txt file and create html
 * files containing a glossary index containing a bulleted list of all terms,
 * and a discrete page for each term with its definition. The files will be
 * saved in a folder specified by the user. Folder and .txt file must exist.
 *
 * .txt file must be formattedly exactly as: <pre>
 *          term [on one line]
 *          definition [on multiple lines if necessary]
 *          [empty line]
 * </pre>
 *
 * @author Matthew Shoemaker
 *
 */
public final class Model {

    public Model() {
        this.statusLabel = new JLabel("Ready");
        this.fileLocationField = new JTextField(20);
        this.folderLocationField = new JTextField(20);
    }

    // JLabel to display the status of upload
    private JLabel statusLabel;
    // JTextfields for the terms file location and the output folder location
    private JTextField fileLocationField;
    private JTextField folderLocationField;




    /**
     * Sets the display message for the status label.
     *
     * @param status status to display
     */
    public void setStatusLabel(String status) {
        this.statusLabel.setText(status);
    }

    /**
     * Returns an alias to the statusLabel.
     *
     * @return this.statusLabel
     */
    public JLabel getStatusLabel() {
        return this.statusLabel;
    }

    /**
     * Returns the contents of the the fileLocationField.
     *
     * @return contents of this.fileLocationField
     */
    public String getFileLocation() {
        return this.fileLocationField.getText();
    }

    /**
     * Sets the contents of the this.fileLocationField to str
     *
     * @param str updated contents of fileLocationField
     */
    public void setFileLocationField(String str) {
        this.fileLocationField.setText(str);
    }

    /**
     *
     *
     * @return
     */
    public JTextField getFileLocationField() {
        return this.fileLocationField;
    }

    public String getFolderLocation() {
        return this.folderLocationField.getText();
    }

    public void setFolderLocation(String str) {
        this.folderLocationField.setText(str);
    }

    public JTextField getFolderLocationField() {
        return this.folderLocationField;
    }







}
