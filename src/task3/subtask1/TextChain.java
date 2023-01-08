package task3.subtask1;

import java.util.*;

public class TextChain {

    public static void main(String[] args) {
        List<String[]> chains = new ArrayList<>(); //тут будуть зберігатися всі побудовані ланцюги
        String text = "тексті немає слів що починаються однакових літер кожного збігалася Якщо всі можна рубати біля";//"біля хвороба однакових якщо авокадо";
    //    String text = "біля хвороба однакових якщо авокадо";
    //    System.out.println(isNoWordsWithTheSameFirstLetter(text));
        List<String> words = Arrays.asList(text.toLowerCase().split(" "));
        //Цикл змінює слово з якого буде починатися будуватися ланцюг
        for (int i = 0; i < words.size(); i++)
        {
            List<String> wordsToChain = new ArrayList<>(words); // копіюємо колекцію, що б не зруйнувати оригінальну
            String startWord = wordsToChain.get(i);             // слово з якого починается ланцюг
            StringBuilder chain = new StringBuilder(startWord); // ланцюг

            while (true)
            {
                wordsToChain.remove(startWord); //видаляємо стартове слово з колекції
                String foundNext = null;         //наступне слово
                for (String word : wordsToChain) {
                    if (startWord.endsWith(word.charAt(0) + "")) { //шукаємо наступне слово по всій колекції
                        foundNext = word;
                    }
                }
                if (foundNext == null) {                              //якщо наступне слово не знайдено, значить ланцюг обірвався
                    chains.add(chain.toString().split(" "));    //зберігаємо його і виходимо з циклу, для спроби побудови нового ланцюга з наступного слова
                    break;
                }
                startWord = foundNext;               //якщо наступне слово знайдено, замінюємо початкове для наступної ітерації
                chain.append(" ").append(foundNext); //і додаємо його до ланцюга
            }

            if (wordsToChain.size() == 0) {          //якщо в колекції не залишилось слів, значить ланцю побудовано повністю
                System.out.println("Ланцюг побудовано! Всі слова використані!");
                System.out.println("Всього слів: " + words.size());
                System.out.println(chain);
                return;
            }
        }

        //Якщо програма дійшла до сюди, значить створити ланцюг зі всіх слів неможливо
        //Шукаємо найдовші з побудованих
        int maxLength = 0;
        for (String[] chain : chains) {
            if (maxLength < chain.length)
                maxLength = chain.length;
        }
        if (maxLength < 2) {
            System.out.println("Неможливо побудувати ланцюг!");
            return;
        }
        //Зберігаємо лише найдовші ланцюги
        List<String> longestChains = new ArrayList<>();
        for (String[] chain : chains) {
            if (chain.length == maxLength)
                longestChains.add(Arrays.toString(chain).replaceAll("[]\\[,]", ""));
        }
        if (longestChains.size() == 1) {
            System.out.println("Ланцюг побудовано! Використано слів: " + maxLength);
            System.out.println("Всього слів: " + words.size());
            System.out.println(longestChains.get(0));
        } else {
            System.out.println("Побудовано декілька ланцюгів! Використано слів в найдовших: " + maxLength);
            System.out.println("Всього слів: " + words.size());
            longestChains.forEach(System.out :: println);
        }



    }


    //Метод для перевірки тексту, на якому буду тестувати код, на наявність слів, що починаються з однакових літер.
    public static boolean isNoWordsWithTheSameFirstLetter(String text) {
        String[] words = text.toLowerCase().split(" ");
        Arrays.sort(words);
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].startsWith((words[i + 1].charAt(0) + "")))
                return false;
        }
        return true;
    }


}
