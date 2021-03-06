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
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import model.Model;

/**
 * This class contains all of the methods used to manipulate the program.
 *
 * @author matts
 *
 */
public final class Controller {

    public Controller(Model model) {
        this.model = model;
    }

    Model model;

    String outputFolderName;

    /**
     * Reads the input file to get the next term/definition item. Item is
     * defined as the Pair: (term, definition).
     *
     * @param in
     *            input stream
     * @return String array containing {term, definition}
     *
     * @requires in.isOpen = true and [file is formatted properly] and [file is
     *           not empty]
     *
     * @ensures [return value is an array of length 2 containing the term and
     *          its definition as {term, definition}]
     *
     */
    public static String[] getNextItem(SimpleReader in) {
        assert in.isOpen() : "Violation of: in.isOpen";

        String[] result = { "", "" };
        // Read in the first line, which will always be the term
        // (Unless at end of stream)
        if (!in.atEOS()) {
            result[0] = in.nextLine();
        }

        // Read the next lines until an empty line is read or end of stream
        // is reached
        // Initialize line to "" so while loop does not run if atEOS = true
        String line = "";
        if (!in.atEOS()) {
            // Read initial line
            line = in.nextLine();
        }
        // Keep concatenating definition until empty line is read or the end of
        // stream is reached
        while (!line.equals("")) {
            result[1] += line;
            if (!in.atEOS()) {
                line = in.nextLine();
            } else {
                // If atEOS = true, exit loop by setting line = ""
                line = "";
            }

        }

        return result;
    }

    /**
     * Adds the values in {@code item} to {@code map} assuming item is {key,
     * value}.
     *
     * @param map
     *            the Map to which item's elements are added
     * @param item
     *            the array containing {key, value}
     *
     * @updates map
     *
     * @requires [none of the elements in item are an empty string] and [none of
     *           the elements in item are null] and [map is not null] and [item
     *           is not null]
     *
     * @ensures [the elements of item are added to map such that {(item[0],
     *          item[1])}]
     */
    public static void addItemToMap(Map<String, String> map, String[] item) {
        assert item[0] != null && item[1] != null : "Violation of: "
                + "none of the elements in item are null";
        assert item != null : "Violation of: item is not null";
        //        assert !item[0].equals("") && !item[1].equals(
        //                "") : "Violation of: none of the elements in item are an empty string";
        assert map != null : "Violation of: map is not null";

        if (!(item[0].equals("") && item.equals(""))) {
            String term = item[0];
            String definition = item[1];
            map.add(term, definition);
        }

    }

    /**
     * Returns a sequence containing all keys of {@code map} sorted
     * alphabetically.
     *
     * @param map
     *            map containing keys to be sorted
     * @return sequence containing all keys of {@code map} sorted
     *         alphabetically.
     *
     * @requires [map is not null]
     * @ensures [returned is a sequence containing all keys of {@code map}
     *          sorted in alphabetical order]
     */
    public static Sequence<String> getSortedMapKeys(Map<String, String> map) {
        assert map != null : "Violation of: map is not null";

        Sequence<String> sortedKeys = new Sequence1L<>();
        Map<String, String> mapTemp = new Map1L<>();
        // Sort all keys in map by removing the lowest (alphabetically) pair
        // and adding that pair's key to a sequence, repeating until none are
        // left in mapTemp
        mapTemp.transferFrom(map);
        while (mapTemp.size() > 0) {
            // find the key of the lowest pair (alphabetically)
            String low = getLowestKey(mapTemp);
            // Remove that pair from mapTemp
            Pair<String, String> lowestPair = mapTemp.remove(low);
            // Restore map
            map.add(lowestPair.key(), lowestPair.value());
            // Add the low to the sorted sequence
            sortedKeys.add(sortedKeys.length(), low);
        }

        return sortedKeys;
    }

    /**
     * Returns the key in {@code map} whose char values occur first
     * alphabetically.
     *
     * @param map
     *            map to be checked
     * @return string value of the key in map that occurs first alphabetically
     *
     * @requires [map is not null] and [map is not empty]
     * @ensures [a string containing the key in {@code map} whose characters
     *          come first alphabetically]
     */
    public static String getLowestKey(Map<String, String> map) {
        assert map != null : "Violation of: map is not null";

        AlphabeticComparator comparator = new AlphabeticComparator();
        // Remove any key from the map and use its key as arbitrary low value
        Pair<String, String> tempPair = map.removeAny();
        String lowKey = tempPair.key();

        // Iterate over each pair in the map, using the comparator to check
        // each new pair against the stored lowest
        for (Pair<String, String> pair : map) {
            String thisKey = pair.key();
            if (comparator.compare(thisKey, lowKey) < 0) {
                lowKey = thisKey;
            }
        }
        // Restore the pair removed from the map
        map.add(tempPair.key(), tempPair.value());

        return lowKey;
    }

    /**
     * Creates the index.html file in the specified folder in HTML5 format
     * containing links to the definition pages in {@code terms}.
     *
     * @param outputFolderName
     *            name of folder to place html file
     * @param terms
     *            sequence containing all of the terms in the glossary
     *
     * @ensures [a well formed HTML5 file is placed in the folder specified] and
     *          [the file will have the name index.html] and [a bulleted list
     *          containing all elements {@code terms} and links to their
     *          respective page is within the file]
     */
    public static void createIndexPage(String outputFolderName,
            Sequence<String> terms) {
        SimpleWriter out = new SimpleWriter1L(outputFolderName + "/index.html");
        printIndexHeader(out);
        printIndexBody(terms, out);
        printFooter(out);
    }

    /**
     * Prints the html markup for the index page header.
     *
     * @param out
     *            output stream
     *
     * @updates out.content
     * @requires out.isOpen = true
     *
     */
    public static void printIndexHeader(SimpleWriter out) {
        assert out.isOpen() : "Violation of: out.isOpen = true";

        SimpleReader in = new SimpleReader1L("data/indexHeader.html");
        while (!in.atEOS()) {
            out.println(in.nextLine());
        }
        in.close();
    }

    /**
     * Prints the html markup for the body of the index.html file.
     *
     * @param sortedKeys
     *            sequence of keys containing all terms in glossary
     * @param out
     *            output stream
     *
     * @updates out.content
     * @requires out.isOpen = true and [sortedKeys is not null]
     * @ensures [the body of the index.html file is printed containing links to
     *          all terms pages]
     */
    public static void printIndexBody(Sequence<String> sortedKeys,
            SimpleWriter out) {
        assert out.isOpen() : "Violation of: out.isOpen = true";
        assert sortedKeys != null : "Violation of: sortedKeys is not null";

        SimpleReader in = new SimpleReader1L("data/indexTerm.html");
        char currentLetter = sortedKeys.entry(sortedKeys.length() - 1).charAt(0);
        int counter = 0;
        int numberOfKeys = sortedKeys.length();
        out.println("            <table id=\"termTable\" width=\"100%\">") ;
        out.println("                <tr>");
        out.println("                    <td width=\"50%\">");
        out.println("                        <ul>");
        for (String key : sortedKeys) {
            if (counter == numberOfKeys / 2 + 1) {
                out.println("                        </ul>");
                out.println("                    </td>");
                out.println("                    <td width=\"50%\">");
                out.println("                        <ul> ");
            }
            if (!key.equals("")) {
                char firstLetter = Character.toLowerCase(key.charAt(0));
                if (firstLetter != currentLetter) {
                    createLetterPage(firstLetter, key, out);
                    out.println("                            <li id=\"" +firstLetter +"\" class=\"indexTerm\"><a href=\"" + key + ".html\">" + key + "</a></li>");
                }
                else {
                    out.println("                            <li class=\"indexTerm\"><a href=\"" + key + ".html\">" + key + "</a></li>");
                }
                currentLetter = firstLetter;
            }
            counter++;
        }

        out.println("                        </ul>");
        out.println("                    </td>");
        out.println("                </tr>");
        out.println("            <table>");
        in.close();
    }

    private static void createLetterPage(char letter, String firstTerm, SimpleWriter out) {

    }

    /**
     * Prints the footer and the closing tags to the output stream.
     *
     * @param out
     *            output stream
     *
     * @updates out.content
     * @requires out.isOpen = true
     */
    public static void printFooter(SimpleWriter out) {
        assert out.isOpen() : "Violation of: out.isOpen = true";
        SimpleReader in = new SimpleReader1L("data/footer.html");
        while (!in.atEOS()) {
            out.println(in.nextLine());
        }
        in.close();
    }

    /**
     * Prints the header specific to the term pages.
     * @param term
     *            the term whose page is being updated
     *
     * @param out
     *            output stream
     *
     * @updates out.content
     * @requires out.isOpen = true
     */
    public static void printTermHeader(String term, SimpleWriter out) {
        assert out.isOpen() : "Violation of: out.isOpen = true";

        // Use this header for each term page, as it imports the stylesheets
        // necessary and all term pages share a common theme
        String header = "";
        SimpleReader in = new SimpleReader1L("data/termHeader.html");
        while (!in.atEOS()) {
            String line = in.nextLine();
            if (line.equals("        <title>")) {
                out.print(line);
                out.print(term);
                out.println(in.nextLine());
            }
            else {
                out.println(line);
            }
        }
        in.close();
        out.println(header);
    }

    /**
     * Adds the proper links to any other glossary items referenced in the
     * definition of the term and updates the body of the file.
     *
     * @param term
     *            term whose page is being updated
     * @param definition
     *            definition of the term
     * @param terms
     *            list of all terms in glossary
     * @param out
     *            output stream
     *
     * @updates out.content
     * @requires [terms is not null] and out.isOpen = true
     *
     * @ensures [the output stream is updated with the body of the term html
     *          markup, including links to any other terms' pages used in the
     *          definition]
     */
    public static void printTermBody(String term, String definition,
            Sequence<String> terms, SimpleWriter out) {
        assert terms != null : "Violation of: terms is not null";
        assert out.isOpen() : "Violation of: out.isOpen = true";

        // Insert the links into the definition before adding it to the body
        String definitionWithLinks = insertLinksIntoDefinition(definition,
                terms);
        out.println("            <h1 class=\"term\">" + term + "</h1>");
        out.println("                <p class=\"definition\">" + definitionWithLinks + "</p>");
        out.println("                <p></p>");
        printNextAndPreviousButtons(term, terms, out);
    }

    /**
     * Prints links to the next and previous terms in the glossary at the bottom
     * of the output file html body.
     *
     * @param term
     *            term whose page is being updated
     * @param terms
     *            sequence containing all terms in glossary
     * @param out
     *            output stream
     *
     * @updates out.content
     * @requires out.isOpen = true and [terms is not null]
     *
     * @ensures [the html markup for the page is updated with a link to the
     *          previous term in the glossary and the next term in the glossary,
     *          and if either is out of range, links to the index page]
     *
     */
    public static void printNextAndPreviousButtons(String term,
            Sequence<String> terms, SimpleWriter out) {
        String nextTerm = "index";
        String previousTerm = "index";
        int indexOfTerm = 0;

        if (!term.equals("")) {
            // Iterate over the terms sequence to find index of term
            for (int k = 0; k < terms.length(); k++) {
                if (terms.entry(k).equals(term)) {
                    indexOfTerm = k;
                }
            }

            /*
             * If the next or previous term is out of index range, set that term to
             * index, otherwise set the next and previous to the appropriate link
             */
            if (terms.length() > 1) {
                if (indexOfTerm == terms.length() - 1) {
                    nextTerm = "index";
                    previousTerm = terms.entry(indexOfTerm - 1);
                } else if (indexOfTerm == 0) {
                    nextTerm = terms.entry(indexOfTerm + 1);
                } else {
                    nextTerm = terms.entry(indexOfTerm + 1);
                    previousTerm = terms.entry(indexOfTerm - 1);
                }
            } else {
                nextTerm = "index";
                previousTerm = "index";
            }

            out.print("<div id=\"buttonHolder\"><h3 class=\"prevButton\"><a href=\""
                    + previousTerm + ".html\">" + "Previous</a></h3>"
                    + "<h3 class=\"nextButton\"><a href=\"" + nextTerm + ".html\">"
                    + "Next</a></h3></div>");
        }

    }

    /**
     * Inserts a link to any terms used in the definition.
     *
     * @param definition
     *            string to be checked
     * @param terms
     *            sequence of terms in glossary
     * @return string conataining the definition with links inserted
     *
     * @requires [terms is not null]
     * @ensures [any words used in definition that are also in terms will be
     *          wrapped in <a></a> element with a link to that term's definition
     *          page]
     */
    public static String insertLinksIntoDefinition(String definition,
            Sequence<String> terms) {
        assert terms != null : "Violation of: terms is not null";

        // Initialize the result to the value of definition parameter in case
        // no links are necessary
        String result = definition;
        // Check the index of each term in the glossary against the contents of
        // the definition
        for (String term : terms) {
            // If the term is in the definition, wrap it in <a></a> element with
            // a link to its term page
            String lowerCaseResult = result.toLowerCase();
            String lowerCaseTerm = term.toLowerCase();
            if (lowerCaseResult.indexOf(lowerCaseTerm) != -1) {

                String openTag = "<a href=\"" + term + ".html\">";
                String closingTag = "</a>";
                StringBuilder resultBuilder = new StringBuilder(result);
                int indexStart = lowerCaseResult.indexOf(lowerCaseTerm);
                int indexEnd = indexStart + term.length();
                String taggedTerm = openTag + term + closingTag;
                resultBuilder.replace(indexStart, indexEnd, taggedTerm);
                result = resultBuilder.toString();
            }
        }

        return result;
    }

    /**
     * Checks to see if {@code term} is a word in the string and not just part
     * of a word in the string.
     *
     * @param string
     *            string to be checked against
     * @param term
     *            term to search for
     * @return true IFF term is a word in the string
     *
     * @requires [string is not null] and [term is not null]
     * @ensures [truee IFF indexOf(term) + |term| = one of {' ',
     *          ',',':',';','/'} or |string|]
     */
    public static boolean isAWordInTheString(String string, String term) {
        assert string != null : "Violation of: string is not null";
        assert term != null : "Violation of: term is not null";

        // Check the index of the end of the term
        int indexOfEndOfTerm = string.indexOf(term) + term.length();
        // Check the index of the first instance of escape characters after the term
        int indexOfFirstSpace = string.indexOf(" ", string.indexOf(term));
        int indexOfFirstComma = string.indexOf(",", string.indexOf(term));
        int indexOfFirstColon = string.indexOf(":", string.indexOf(term));
        int indexOfFirstSemiColon = string.indexOf(";", string.indexOf(term));
        int indexOfFirstForwardSlash = string.indexOf("/",
                string.indexOf(term));

        // If the index of the end of the term is the same as the index of one
        // of the escape characters, then it must be a word in the glossary, so
        // return true. Othewrise false
        return indexOfEndOfTerm == indexOfFirstSpace
                || indexOfEndOfTerm == indexOfFirstComma
                || indexOfEndOfTerm == indexOfFirstColon
                || indexOfEndOfTerm == indexOfFirstSemiColon
                || indexOfEndOfTerm == indexOfFirstForwardSlash
                || indexOfEndOfTerm == string.length();

    }

    /**
     * Prints the hardcoded css file containing the styling for the glossary to
     * its own file named glossary.css.
     *
     * @param outputFolderName
     *            folder in which the html files are located
     *
     * @requires [output folder exists]
     */
    public static void printCSSFile(String outputFolderName) {
        SimpleWriter out = new SimpleWriter1L(
                outputFolderName + "/glossary.css");
        SimpleReader in = new SimpleReader1L("data/style.css");
        while (!in.atEOS()) {
            out.println(in.nextLine());
        }
        in.close();
        out.close();
    }

    /**
     * Updates the status message in the GUI.
     *
     * @param status
     *            message to display
     *
     * @requires [status is not null]
     */
    public void updateStatus(String status) {
        assert status != null : "Violation of: status is not null";
        this.model.setStatusLabel(status);
    }

    /**
     * Returns the file path given in the file path text field.
     *
     * @return the contents of the file path text field.
     */
    public String getFilePath() {
        return this.model.getFileLocation();
    }

    /**
     * Returns the folder path given in the folder text field.
     *
     * @return the contents of the folder text field.
     */
    public String getFolder() {
        return this.model.getFolderLocation();
    }

    /**
     * Returns a new GoButtonListener instance.
     *
     * @return new instance of a GoButtonListener
     */
    public GoButtonListener getGoButtonListener() {
        return new GoButtonListener(this.model);
    }

    /**
     * Returns a new ResetButtonListener instance.
     *
     * @return new instance of a ResetButtonListener
     */
    public ResetButtonListener getResetButtonListener() {
        return new ResetButtonListener();
    }

    /**
     * Sets the contents of both the file path and folder text fields to an
     * empty string.
     */
    public void resetTextFields() {
        this.model.setFileLocationField("");
        this.model.setFolderLocation("");
    }

    /* ************************************************************************
     * ************************Button Listeners********************************
     * ************************************************************************
     */

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
                Controller.printFooter(output);
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

    /**
     * This class resets the text fields to the empty string.
     *
     * @author matts
     *
     */
    public class ResetButtonListener implements ActionListener {

        @Override
        public final void actionPerformed(ActionEvent arg0) {
            Controller.this.resetTextFields();
            Controller.this.updateStatus("Ready");
        }
    }
}
