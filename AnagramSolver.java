// Joshua Lee
// CSE 143 BH
// TA: RanDair Porter
// Assignment #6: AnagramSolver

// This program will take any given string and show all the possible words that can be made
// with only the letters of the given string.

import java.util.*;

public class AnagramSolver {
   Map<String, LetterInventory> dictLetters; // Map of dictionary words & their letter counts.
   List<String> dictionary;                   // List of all strings in the original dictionary.
   
   // pre: list is a nonempty collection of nonempty sequences of letters with no duplicates
   //      (code will not function as smoothly if not)
   // post: stores the passed list of strings into a map of strings and their respective
   //       letter inventories for efficient and convenient access. List is not modified.
   public AnagramSolver(List<String> list) {
      dictionary = list;
      dictLetters = new HashMap<String, LetterInventory>();
      for (String word: list) {
         LetterInventory letters = new LetterInventory(word);
         dictLetters.put(word, letters);
      }
   }
   
   // pre: max >= 0 (otherwise throw IllegalArgumentException)
   // post: prunes the given dictionary into a more specific dictionary of words relevant
   //       to the given string. A word is relevant if it's letter inventory can be subtracted
   //       from the string's letter inventory. Then finds and prints all combinations of "max"
   //       or less number of words with the same letters as the string.
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      List<String> newDictionary = new ArrayList<String>();
      LetterInventory sLetters = new LetterInventory(s);
      for (String word: dictionary) {
         if (sLetters.subtract(dictLetters.get(word)) != null) {
            newDictionary.add(word);
         }
      }
      Stack<String> soFar = new Stack<String>();
      print(newDictionary, sLetters, max, soFar);
   }
   
   // post: searches through the new dictionary to find anagrams of the given string.
   //       Keeps track of each relevant word in a stack, up to "max" or less number of words.
   //       When a combination reaches max with no letters remaining, print the combination.
   private void print(List<String> newDictionary, LetterInventory sLetters,
   int max, Stack<String> soFar) {
      if (sLetters.isEmpty()) {
         if (max == 0 || soFar.size() <= max) {
            System.out.println(soFar.toString());
         }
      }
      for (String word: newDictionary) {
         if (sLetters.subtract(dictLetters.get(word)) != null) {
            soFar.push(word);
            print(newDictionary, sLetters.subtract(dictLetters.get(word)), max, soFar);
            soFar.pop();
         }
      }
   }
}