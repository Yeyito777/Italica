package net.yeyito.italica.chat;

import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.yeyito.italica.mixin.StyleInvoker;

import java.util.*;

public class TextFormatter {
    public static List<Object> getStyle(String message){
        return getPrefixOccurences(message);
    }
    public static ArrayList<Object> applyPrefixOccurences(ArrayList<Object> PrefixList){
        ArrayList<Object> StyleList = convertLongPrefixToParent(PrefixList);
        selectorLogic(StyleList);
        return StyleList;
    }
    public static void selectorLogic(ArrayList<Object> StyleList) {
        ArrayList<Integer> PrefixMatches = new ArrayList<>();

        for (int i = 0; i < StyleList.size(); i++) { // Remove redundancy
            for (int t = 0; t < StyleRepository.GET_STYLES.length; t++) {
                if (StyleRepository.GET_STYLES[t] == StyleList.get(i)) {
                    int linesToSkip = StyleRepository.GET_PREFIXES[t].length();
                    boolean redundant = true;
                    for (int v = i+linesToSkip; v < StyleList.size(); v++) {
                        if (StyleList.get(v) == StyleRepository.GET_STYLES[t] && redundant) {
                            redundant = false;
                            for (int z = i; z < i + linesToSkip; z++) {
                                if (!PrefixMatches.contains(z) && !PrefixMatches.contains(v+(z-i))) {
                                    PrefixMatches.add(z);
                                    PrefixMatches.add(v + (z - i));
                                }
                            }
                        }
                    }
                    if (redundant && PrefixMatches.contains(i)) {redundant = false;}
                    if (redundant) {
                        for (int v = i; v < i + linesToSkip; v++) {
                            StyleList.set(v, StyleRepository.EMPTY);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < PrefixMatches.size(); i = i+2) { // Convert Characters inside prefixes into respective
            int prefixStart = PrefixMatches.get(i);
            int prefixEnd = PrefixMatches.get(i + 1);
            for (int v = prefixStart; v < prefixEnd; v++) {
                if (!StyleList.get(v).equals(false) && !StyleList.get(i).equals(false)) {
                    StyleList.set(v, combineStyles((Style) StyleList.get(v), (Style) StyleList.get(prefixStart)));
                } else {
                    StyleList.set(v, false);
                }
            }
        }
        for (Integer prefixMatch : PrefixMatches) { // Setting non-redundant prefixes to false
            StyleList.set(prefixMatch, false);
        }
    }
    public static ArrayList<Object> getPrefixOccurences(String message){
        ArrayList<Object> PrefixList = new ArrayList<>();
        for (int i = 0; i < message.length(); i++){
            PrefixList.add(StyleRepository.EMPTY);
            for (int t = 0; t < StyleRepository.GET_PREFIXES.length; t++){
                String prefix = "";
                for (int v = 0; v < StyleRepository.GET_PREFIXES[t].length(); v++) {
                    if (message.length() >= i+v+1) {
                        prefix = prefix + message.charAt(i + v);
                    }
                }
                if (prefix.equals(StyleRepository.GET_PREFIXES[t]) && PrefixList.get(i) == StyleRepository.EMPTY) {
                    PrefixList.set(i,StyleRepository.GET_STYLES[t]);
                }
            }
        }
        return applyPrefixOccurences(PrefixList);
    }
    public static ArrayList<Object> convertLongPrefixToParent(ArrayList<Object> PrefixList) {
        ArrayList<Integer> Unchangable = new ArrayList<>();

        for (int i = 0; i < PrefixList.size(); i++) { // Mark open and close statements
            Object CurrentPrefix = PrefixList.get(i);
            int CurrentPrefixLength = 0;

            for (int t = 0; t < StyleRepository.GET_STYLES.length; t++) {
                if (StyleRepository.GET_STYLES[t] == CurrentPrefix) {
                    CurrentPrefixLength = StyleRepository.GET_PREFIXES[t].length();
                }
            }
            for (int t = 0; t < CurrentPrefixLength; t++) {
                boolean Changable = true;
                for (Integer integer : Unchangable) {
                    if (integer == i) {
                        Changable = false;
                        break;
                    }
                }
                if (Changable && PrefixList.size() > i+t && PrefixList.get(i+t) != CurrentPrefix) {
                    PrefixList.set(i+t,CurrentPrefix);
                    Unchangable.add(i+t);
                }
            }
        }
        return PrefixList;
    }
    public static String styleArrayToString(ArrayList<Object> StyleList) {
        String StringStyleList = "";
        for (int i = 0; i < StyleList.size(); i++) {
            for (int t = 0; t < StyleRepository.GET_STYLES.length; t++) {
                if (StyleRepository.GET_STYLES[t] == StyleList.get(i)) {StringStyleList = StringStyleList + StyleRepository.GET_NAMES[t] + " ";}
                else if (StyleRepository.EMPTY == StyleList.get(i)) {StringStyleList = StringStyleList + "EMPTY" + " "; t = StyleRepository.GET_STYLES.length;}
                else if (StyleList.get(i).equals(false)) {StringStyleList = StringStyleList + "FALSE" + " "; t = StyleRepository.GET_STYLES.length;}
                else {StringStyleList = StringStyleList + "CUSTOM" + " "; t = StyleRepository.GET_STYLES.length;}
            }
        }
        return StringStyleList;
    }
    public static Style combineStyles(Style s1, Style s2) {
        Style newStyle;
        TextColor newColor = (s1.getColor()); // Add rgbs !!
        boolean isBold = (s2.isBold() || s1.isBold());
        boolean isItalic = (s2.isItalic() || s1.isItalic());
        boolean isUnderlined = (s2.isUnderlined() || s1.isUnderlined());
        boolean isStrikethrough = (s2.isStrikethrough() || s1.isStrikethrough());
        boolean isObfuscated = s2.isObfuscated() || s1.isObfuscated();

        newStyle = StyleInvoker.newStyle(newColor,isBold,isItalic,isUnderlined,isStrikethrough
                ,isObfuscated,s1.getClickEvent(),s1.getHoverEvent(),s1.getInsertion(),s1.getFont());
        return newStyle;
    }
}
