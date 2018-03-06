package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.sequence.Sequence;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import model.Model;

/**
 * This class gets the contents of the text fields and uses that information to
 * read the terms file and construct the proper web pages.
 *
 * @author matts
 *
 */
public class GoButtonListener implements ActionListener {

    public GoButtonListener(Model model) {
        super();
        this.model = model;
    }

    Model model;

    @Override
    public final void actionPerformed(ActionEvent arg0) {

        this.model.setStatusLabel("Upload Failed");

        String inputFileName = this.model.getFileLocation();
        String outputFolderName = this.model.getFolderLocation();

        // Get an input stream for the txt file containing glossary terms
        SimpleReader inputFile = new SimpleReader1L(inputFileName);

        // Read in all info from the txt file, parsing the terms and
        // definitions into the map as {(key=term, value=definition)}
        Map<String, String> map = new Map1L<>();
        while (!inputFile.atEOS()) {
            String[] item = Controller.getNextItem(inputFile);
            Controller.addItemToMap(map, item);
        }
        // Sort the keys in map alphabetically into a sequence
        Sequence<String> sortedTerms = Controller.getSortedMapKeys(map);

        // Iterate over the sorted sequence, creating a new html page for
        // each term
        Controller.createIndexPage(outputFolderName, sortedTerms);
        for (String term : sortedTerms) {
            // Use the values in sortedTerms as keys to remove pairs
            Pair<String, String> termPair = map.remove(term);
            // Open the output stream to the file in the specified folder
            SimpleWriter output = new SimpleWriter1L(
                    outputFolderName + "/" + term + ".html");

            Controller.printTermHeader(term, output);
            Controller.printTermBody(termPair.key(), termPair.value(),
                    sortedTerms, output);
            Controller.printClosingTags(output);
            output.close();
        }

        // Print the css file into its own .css file
        Controller.printCSSFile(outputFolderName);

        this.model.setStatusLabel("Upload Successful");

        // If possible, open the browser and display the glossary
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(outputFolderName + "/index.html");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        inputFile.close();
    }
}
