Glossary:

This program will allow you to enter the location of a text file containing a 
list of words, and will output a well formatted, attractive html page with a 
list of terms with links to their definitions in a glossary style index. The 
html and css files will be output to the folder you specify.

This project was written in eclipse, and may require an import to an eclipse 
workspace in order to run. 

** The text file fed into the program must have a strict format that adheres to
   the following guidelines without exception:
        - The Term is on its own line, followed by its definition on the next
          lines. The term may only be a single line, while its definition can
          span many lines.
        - There must be exactly 1 empty line between the defition and the next
          term.
        - There must not be any empty lines at the beginning or end of the text
          file.
        - You may embed html tags within a definition, but not a term.
        - In order for terms that appear within a definition to link properly,
          they must match the spelling and case of the term on the index page.
        Example:
                Term1
                This term has a precise definition
                
                Term2
                The definition for this term is irrelevant, but here's an html
                tag to put <em>emphasis</em> on what I'm saying. Also note that
                this definition spans many lines.
                
                Term3
                You may reference other terms in your definitions. I can 
                mention Term1 in my definition and it will automatically link,
                but if I spelled it term1 it would not link due to case 
                sensitivity.
                
                

To run this program:

1. Download the zip and import it to your Eclipse workspace, or whatever IDE you 
may wish to use. 

2. The main method is located in the view package. Open the view package in the 
src folder and run GlossaryGUI.java

3. In the "File Path" field, enter the absolute location of the file, or you may
use the location relative to the location in the workspace.

4. Enter the folder in which you would like the HTML and CSS files to be output.