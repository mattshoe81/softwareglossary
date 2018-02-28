import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controller.AlphabeticComparator;

public class AlphabeticComparatorTest {

    /*
     * Only 1 method in this class by definition, so tests will simply be named
     * test with some description of the test case for method name, to save time
     */

    @Test
    public void test_Empty_Strings() {
        String str1 = "";
        String str2 = "";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 0;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Empty_And_Nonempty_1() {
        String str1 = "";
        String str2 = "abcd";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = -1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Empty_And_Nonempty_2() {
        String str1 = "abcd";
        String str2 = "";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Equal_Strings() {
        String str1 = "abcd";
        String str2 = "abcd";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 0;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Prepended_Differing_1() {
        String str1 = "abcd";
        String str2 = "abcdefg";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = -1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Prepended_Differing_2() {
        String str1 = "abcdefg";
        String str2 = "abcd";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Single_Chars_Equal() {
        String str1 = "a";
        String str2 = "a";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 0;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Single_Chars_Unequal_1() {
        String str1 = "a";
        String str2 = "b";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = -1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Single_Chars_Unequal_2() {
        String str1 = "b";
        String str2 = "a";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Single_Char__And_String_1() {
        String str1 = "a";
        String str2 = "abcdef";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = -1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Single_Char_And_String_2() {
        String str1 = "abcdef";
        String str2 = "a";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Equal_Strings_With_Uppercase() {
        String str1 = "AbCd";
        String str2 = "aBcD";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 0;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Prepended_Differing_1_With_Uppercase() {
        String str1 = "aBcD";
        String str2 = "AbCdEfG";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = -1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Prepended_Differing_2_With_Uppercase() {
        String str1 = "AbCdEfG";
        String str2 = "aBcD";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Single_Chars_Equal_With_Uppercase() {
        String str1 = "A";
        String str2 = "a";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 0;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Single_Char__And_String_1_With_Uppercase() {
        String str1 = "A";
        String str2 = "abcdef";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = -1;
        assertEquals(comparison, expectedResult);
    }

    @Test
    public void test_Single_Char_And_String_2_With_Uppercase() {
        String str1 = "Abcdef";
        String str2 = "a";
        AlphabeticComparator comparator = new AlphabeticComparator();
        int comparison = comparator.compare(str1, str2);

        int expectedResult = 1;
        assertEquals(comparison, expectedResult);
    }

}
