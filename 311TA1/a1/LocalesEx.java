package a1;

import java.util.*;
import java.util.stream.*;

public class LocalesEx {
    static long numOfLocales() {
        return Stream.of(Locale.getAvailableLocales()).count();
    }
    //Part (a)
    static List<String> distinctLanguageCodeInReverseOrder() {
        List<String> result = Stream.of(Locale.getAvailableLocales())   
                                    .map(Locale::getLanguage)           
                                    .distinct()                         
                                    .sorted(Comparator.reverseOrder())  
                                    .collect(Collectors.toList());      
        return result;
    }
    //Part (b)
    static List<String> countryWithEnglishLanguage() {
        List<String> result = Stream.of(Locale.getAvailableLocales())       
                                       .filter(x->x.getDisplayLanguage(Locale.ENGLISH).equals("English"))  
                                       .map(a->a.getDisplayCountry(Locale.ENGLISH))     
                                       .collect(Collectors.toList());           
        
        return result;
    }
 
 public static void main(String[] args) {
     System.out.println("Number of locales: " + numOfLocales());
     System.out.println("Distinct language codes in reverse order: "
        + distinctLanguageCodeInReverseOrder());
     System.out.println("Country names with English language: "
        + countryWithEnglishLanguage());
 }
} 