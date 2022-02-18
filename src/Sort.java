import java.util.ArrayList;

public class Sort {
    public static void insertionSortWordList(ArrayList<String> words) {
        int iterations = 0;
        for (int i = 1; i < words.size(); i++) {
            String temp = words.get(i);
            int possibleIndex = i;
            while (possibleIndex > 0 && (temp.compareTo(words.get(possibleIndex - 1)) < 0)) {
                words.set(possibleIndex, words.get(possibleIndex - 1));
                possibleIndex--;
                iterations++;
            }
            words.set(possibleIndex, temp);
        }
    }
}
