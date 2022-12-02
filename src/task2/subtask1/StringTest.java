package task2.subtask1;

public class StringTest {
    public static void main(String[] args) {
        String someString = "isu([[syvs()t((())asfdw)c]ts(crs)])cs";
        System.out.println(testString(someString));
    }

    public static boolean testString(String str) {
        if (str == null) {
            System.out.println("The string is null!");
            return false;
        }

        boolean isValid = true;
        int countOpeningRoundParenthesis = 0;
        int countClosingRoundParenthesis = 0;
        int countOpeningSquareParenthesis = 0;
        int countClosingSquareParenthesis = 0;

        //  Parenthesis counting
        for (char ch : str.toCharArray()) {
            switch (ch) {
                case '(':
                    countOpeningRoundParenthesis++;
                    break;
                case ')':
                    countClosingRoundParenthesis++;
                    break;
                case '[':
                    countOpeningSquareParenthesis++;
                    break;
                case ']':
                    countClosingSquareParenthesis++;
                    break;
            }
        }

        //  Parenthesis count validation
        if (countOpeningRoundParenthesis != countClosingRoundParenthesis ||
                countOpeningSquareParenthesis != countClosingSquareParenthesis) {
            isValid = false;
        } else
        // Different types parenthesis cross validation
        {
            // Знаю, що наступні дії (або, навіть увесь метод), скоріше за все, можна зробити одним реджексом,
            // але не став його вигадувати або шукатию Так, наче, простіше і зрозуміліше, але не впевнений, що швидче.

            // Removing all symbols except parenthesis
            str = str.replaceAll("[^()\\]\\[]+", "");
            // Removing "()" and "[]" until found "(]" or "[), or str.isEmpty"
            while (!str.isEmpty()) {
                str = str.replaceAll("\\(\\)", "").replaceAll("\\[]", "");
                if (str.matches(".*\\(].*") || str.matches(".*\\[\\).*")) {
                    isValid = false;
                    break;
                }
            }
        }

        return isValid;
    }
}
