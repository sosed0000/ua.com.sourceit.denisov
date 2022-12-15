package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tests {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list.remove(0));

        String text = "sdfv asrg dfg dfhj fguj" ;
        List<String> words = new ArrayList<>(Arrays.asList(text.split(" ")));
        //List<String> words = Arrays.asList(text.split(" "));
        System.out.println(words.remove(0));

        Character ch = 'd';

    }


}