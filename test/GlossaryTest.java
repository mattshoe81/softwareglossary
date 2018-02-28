import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import controller.Controller;

public class GlossaryTest {

    @Test
    public void getNextItemTest_Single_Term() {
        SimpleReader in = new SimpleReader1L(
                "test/Test_Files_txt/getNextItemTest_Routine.txt");
        String[] result = Controller.getNextItem(in);

        assertEquals(result[0], "term");
        assertEquals(result[1], "describes something");
    }

    @Test
    public void getNextItemTest_Multiple_Terms() {
        SimpleReader in = new SimpleReader1L(
                "test/Test_Files_txt/getNextItemTest_Multiple_Terms.txt");
        String[] result = Controller.getNextItem(in);
        String termResult = result[0];
        String defnResult = result[1];

        assertEquals(termResult, "term");
        assertEquals(defnResult, "describes something");
    }

    @Test
    public void getNextItemTest_Multiple_Line_Definition() {
        SimpleReader in = new SimpleReader1L(
                "test/Test_Files_txt/getNextItem_Multiple_Line_Definition.txt");
        String[] result = Controller.getNextItem(in);

        assertEquals(result[0], "term");
        assertEquals(result[1], "describes something");
    }

    @Test
    public void addItemToMapTest_Routine() {
        Map<String, String> map = new Map1L<>();
        String key = "term";
        String value = "describes something";
        String[] testItem = { key, value };
        String[] testItemOriginal = { key, value };
        Controller.addItemToMap(map, testItem);
        Pair<String, String> pair = map.remove(key);

        assertEquals(pair.key(), key);
        assertEquals(pair.value(), value);
        // Make sure testItem was restored
        assertEquals(testItem[0], key);
        assertEquals(testItem[1], value);
    }

    @Test
    public void getSortedMapKeysTest_Empty_Map() {
        Map<String, String> map = new Map1L<>();
        Sequence<String> correctOrder = new Sequence1L<>();
        Sequence<String> testOrder = Controller.getSortedMapKeys(map);

        assertEquals(correctOrder, testOrder);
    }

    @Test
    public void getSortedMapKeysTest_Single_Char() {
        Map<String, String> map = new Map1L<>();
        String value = "irrelevant";
        map.add("c", value);
        map.add("b", value);
        map.add("f", value);
        map.add("a", value);
        map.add("e", value);
        map.add("d", value);
        Map<String, String> mapOriginal = new Map1L<>();
        mapOriginal.add("c", value);
        mapOriginal.add("b", value);
        mapOriginal.add("f", value);
        mapOriginal.add("a", value);
        mapOriginal.add("e", value);
        mapOriginal.add("d", value);
        Sequence<String> correctOrder = new Sequence1L<>();
        correctOrder.add(0, "a");
        correctOrder.add(1, "b");
        correctOrder.add(2, "c");
        correctOrder.add(3, "d");
        correctOrder.add(4, "e");
        correctOrder.add(5, "f");
        Sequence<String> testOrder = Controller.getSortedMapKeys(map);

        assertEquals(correctOrder, testOrder);
        // Make sure map is restored
        assertEquals(map, mapOriginal);
    }

    @Test
    public void getSortedMapKeysTest_Full_Words() {
        Map<String, String> map = new Map1L<>();
        String value = "irrelevant";
        map.add("meaning", value);
        map.add("term", value);
        map.add("word", value);
        map.add("definition", value);
        map.add("glossary", value);
        map.add("language", value);
        map.add("book", value);
        Map<String, String> mapOriginal = new Map1L<>();
        mapOriginal.add("meaning", value);
        mapOriginal.add("term", value);
        mapOriginal.add("word", value);
        mapOriginal.add("definition", value);
        mapOriginal.add("glossary", value);
        mapOriginal.add("language", value);
        mapOriginal.add("book", value);
        Sequence<String> correctOrder = new Sequence1L<>();
        correctOrder.add(0, "book");
        correctOrder.add(1, "definition");
        correctOrder.add(2, "glossary");
        correctOrder.add(3, "language");
        correctOrder.add(4, "meaning");
        correctOrder.add(5, "term");
        correctOrder.add(6, "word");
        Sequence<String> testOrder = Controller.getSortedMapKeys(map);

        assertEquals(correctOrder, testOrder);
        assertEquals(mapOriginal, map);
    }

    @Test
    public void getSortedMapKeysTest_Similar_Terms() {
        Map<String, String> map = new Map1L<>();
        String value = "irrelevant";
        map.add("aaabc", value);
        map.add("aaac", value);
        map.add("aaa", value);
        map.add("aaabd", value);
        map.add("abcd", value);
        map.add("aacb", value);
        Map<String, String> mapOriginal = new Map1L<>();
        mapOriginal.add("aaabc", value);
        mapOriginal.add("aaac", value);
        mapOriginal.add("aaa", value);
        mapOriginal.add("aaabd", value);
        mapOriginal.add("abcd", value);
        mapOriginal.add("aacb", value);
        Sequence<String> correctOrder = new Sequence1L<>();
        correctOrder.add(0, "aaa");
        correctOrder.add(1, "aaabc");
        correctOrder.add(2, "aaabd");
        correctOrder.add(3, "aaac");
        correctOrder.add(4, "aacb");
        correctOrder.add(5, "abcd");
        Sequence<String> testOrder = Controller.getSortedMapKeys(map);

        assertEquals(correctOrder, testOrder);
        assertEquals(mapOriginal, map);
    }

    @Test
    public void getLowestKeyTest_Similar_Terms() {
        Map<String, String> map = new Map1L<>();
        String value = "irrelevant";
        map.add("aaabc", value);
        map.add("aaac", value);
        map.add("aaa", value);
        map.add("aaabd", value);
        map.add("abcd", value);
        map.add("aacb", value);
        Map<String, String> mapOriginal = new Map1L<>();
        mapOriginal.add("aaabc", value);
        mapOriginal.add("aaac", value);
        mapOriginal.add("aaa", value);
        mapOriginal.add("aaabd", value);
        mapOriginal.add("abcd", value);
        mapOriginal.add("aacb", value);

        String lowestKey = "aaa";
        String lowestKeyTest = Controller.getLowestKey(map);

        assertEquals(lowestKeyTest, lowestKey);
        assertEquals(mapOriginal, map);
    }

    @Test
    public void getLowestKeyTest_Full_Words() {
        Map<String, String> map = new Map1L<>();
        String value = "irrelevant";
        map.add("meaning", value);
        map.add("term", value);
        map.add("word", value);
        map.add("definition", value);
        map.add("glossary", value);
        map.add("language", value);
        map.add("book", value);
        Map<String, String> mapOriginal = new Map1L<>();
        mapOriginal.add("meaning", value);
        mapOriginal.add("term", value);
        mapOriginal.add("word", value);
        mapOriginal.add("definition", value);
        mapOriginal.add("glossary", value);
        mapOriginal.add("language", value);
        mapOriginal.add("book", value);

        String lowestKey = "book";
        String lowestKeyTest = Controller.getLowestKey(map);

        assertEquals(lowestKeyTest, lowestKey);
        assertEquals(mapOriginal, map);
    }

    @Test
    public void getLowestKeyTest_Single_Char() {
        Map<String, String> map = new Map1L<>();
        String value = "irrelevant";
        map.add("c", value);
        map.add("b", value);
        map.add("f", value);
        map.add("a", value);
        map.add("e", value);
        map.add("d", value);
        Map<String, String> mapOriginal = new Map1L<>();
        mapOriginal.add("c", value);
        mapOriginal.add("b", value);
        mapOriginal.add("f", value);
        mapOriginal.add("a", value);
        mapOriginal.add("e", value);
        mapOriginal.add("d", value);

        String lowestKey = "a";
        String lowestKeyTest = Controller.getLowestKey(map);

        assertEquals(lowestKeyTest, lowestKey);
        assertEquals(mapOriginal, map);
    }

    @Test
    public void insertLinksIntoDefinitionTest_Single_Link_With_Plurals() {
        String definition = "a list of difficult or specialized terms, with their definitions,"
                + "usually near the end of a book";
        Sequence<String> terms = new Sequence1L<>();
        terms.add(0, "book");
        terms.add(1, "definition");
        terms.add(2, "glossary");
        terms.add(3, "language");
        terms.add(4, "meaning");
        terms.add(5, "term");
        terms.add(6, "word");
        String definitionTest = Controller.insertLinksIntoDefinition(definition,
                terms);
        String definitionActual = "a list of difficult or specialized terms, with their definitions,"
                + "usually near the end of a <a href=\"book.html\">book</a>";

        assertEquals(definitionActual, definitionTest);
    }

    @Test
    public void insertLinksIntoDefinitionTest_Double_Link_With_Plurals() {
        String definition = "a sequence of words that gives meaning to a term";
        Sequence<String> terms = new Sequence1L<>();
        terms.add(0, "book");
        terms.add(1, "definition");
        terms.add(2, "glossary");
        terms.add(3, "language");
        terms.add(4, "meaning");
        terms.add(5, "term");
        terms.add(6, "word");
        String definitionTest = Controller.insertLinksIntoDefinition(definition,
                terms);
        String definitionActual = "a sequence of words that gives "
                + "<a href=\"meaning.html\">meaning</a> to a "
                + "<a href=\"term.html\">term</a>";

        assertEquals(definitionActual, definitionTest);
    }

    @Test
    public void insertLinksIntoDefinitionTest_Single_Link_With_Comma() {
        String definition = "testing a definition, hoping it works";
        Sequence<String> terms = new Sequence1L<>();
        terms.add(0, "book");
        terms.add(1, "definition");
        terms.add(2, "glossary");
        terms.add(3, "language");
        terms.add(4, "meaning");
        terms.add(5, "term");
        terms.add(6, "word");
        String definitionTest = Controller.insertLinksIntoDefinition(definition,
                terms);
        String definitionActual = "testing a "
                + "<a href=\"definition.html\">definition</a>, hoping it "
                + "works";

        assertEquals(definitionActual, definitionTest);
    }

    @Test
    public void insertLinksIntoDefinitionTest_Single_Link_With_Colon() {
        String definition = "testing a definition: hoping it works";
        Sequence<String> terms = new Sequence1L<>();
        terms.add(0, "book");
        terms.add(1, "definition");
        terms.add(2, "glossary");
        terms.add(3, "language");
        terms.add(4, "meaning");
        terms.add(5, "term");
        terms.add(6, "word");
        String definitionTest = Controller.insertLinksIntoDefinition(definition,
                terms);
        String definitionActual = "testing a "
                + "<a href=\"definition.html\">definition</a>: hoping it "
                + "works";

        assertEquals(definitionActual, definitionTest);
    }

    @Test
    public void insertLinksIntoDefinitionTest_Single_Link_With_Semicolon() {
        String definition = "testing a definition; hoping it works";
        Sequence<String> terms = new Sequence1L<>();
        terms.add(0, "book");
        terms.add(1, "definition");
        terms.add(2, "glossary");
        terms.add(3, "language");
        terms.add(4, "meaning");
        terms.add(5, "term");
        terms.add(6, "word");
        String definitionTest = Controller.insertLinksIntoDefinition(definition,
                terms);
        String definitionActual = "testing a "
                + "<a href=\"definition.html\">definition</a>; hoping it "
                + "works";

        assertEquals(definitionActual, definitionTest);
    }

    @Test
    public void insertLinksIntoDefinitionTest_Single_Link_With_Forward_Slash() {
        String definition = "testing a definition/ hoping it works";
        Sequence<String> terms = new Sequence1L<>();
        terms.add(0, "book");
        terms.add(1, "definition");
        terms.add(2, "glossary");
        terms.add(3, "language");
        terms.add(4, "meaning");
        terms.add(5, "term");
        terms.add(6, "word");
        String definitionTest = Controller.insertLinksIntoDefinition(definition,
                terms);
        String definitionActual = "testing a "
                + "<a href=\"definition.html\">definition</a>/ hoping it "
                + "works";

        assertEquals(definitionActual, definitionTest);
    }

    @Test
    public void insertLinksIntoDefinitionTest_No_Links() {
        String definition = "a printed or written literary work";
        Sequence<String> terms = new Sequence1L<>();
        terms.add(0, "book");
        terms.add(1, "definition");
        terms.add(2, "glossary");
        terms.add(3, "language");
        terms.add(4, "meaning");
        terms.add(5, "term");
        terms.add(6, "word");
        String definitionTest = Controller.insertLinksIntoDefinition(definition,
                terms);
        String definitionActual = "a printed or written literary work";

        assertEquals(definitionActual, definitionTest);
    }

    @Test
    public void isAWordInTheStringTest_False() {
        String string = "Yes hello";
        String term = "No";

        assertTrue(!Controller.isAWordInTheString(string, term));
    }

    @Test
    public void isAWordInTheStringTest_True_Space() {
        String string = "Yes hello";
        String term = "Yes";

        assertTrue(Controller.isAWordInTheString(string, term));
    }

    @Test
    public void isAWordInTheStringTest_True_Comma() {
        String string = "Yes, hello";
        String term = "Yes";

        assertTrue(Controller.isAWordInTheString(string, term));
    }

    @Test
    public void isAWordInTheStringTest_True_Colon() {
        String string = "Yes: hello";
        String term = "Yes";

        assertTrue(Controller.isAWordInTheString(string, term));
    }

    @Test
    public void isAWordInTheStringTest_True_Semicolon() {
        String string = "Yes; hello";
        String term = "Yes";

        assertTrue(Controller.isAWordInTheString(string, term));
    }

    @Test
    public void isAWordInTheStringTest_True_Forward_Slash() {
        String string = "Yes/ hello";
        String term = "Yes";

        assertTrue(Controller.isAWordInTheString(string, term));
    }

    @Test
    public void isAWordInTheStringTest_True_End_Of_String() {
        String string = "Hello, Yes";
        String term = "Yes";

        assertTrue(Controller.isAWordInTheString(string, term));
    }

}
