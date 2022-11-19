package net.yeyito.italica.chat;
import net.minecraft.text.Style;
import net.yeyito.italica.mixin.StyleInvoker;

public class StyleRepository {
    public static final int MAX_DEPTH = 3;
    //STYLES
    public static final Style EMPTY = StyleInvoker.newStyle(null, null, null, null, null, null, null, null, null, null);
    public static final Style ITALIC = StyleInvoker.newStyle(null, null, true, null, null, null, null, null, null, null);
    public static final Style BOLD = StyleInvoker.newStyle(null, true, false, null, null, null, null, null, null, null);
    public static final Style BOLDITALIC = StyleInvoker.newStyle(null, true, true, null, null, null, null, null, null, null);
    public static final Style STRIKETHROUGH = StyleInvoker.newStyle(null, null, null, null, true, null, null, null, null, null);
    public static final Style UNDERLINE = StyleInvoker.newStyle(null, null, null, true, null, null, null, null, null, null);
    public static final Style OBFUSCATED = StyleInvoker.newStyle(null,null,null,null,null,true,null,null,null,null);
    //PREFIXES
    public static final String ITALIC_PREFIX = "*";
    public static final String BOLD_PREFIX = "**";
    public static final String BOLDITALIC_PREFIX = "***";
    public static final String STRIKETHROUGH_PREFIX = "--";
    public static final String UNDERLINE_PREFIX = "__";
    public static final String OBFUSCATED_PREFIX = "###";
    //TABLES
    public static final String[] GET_PREFIXES = {BOLDITALIC_PREFIX,BOLD_PREFIX,ITALIC_PREFIX,STRIKETHROUGH_PREFIX,UNDERLINE_PREFIX,OBFUSCATED_PREFIX}; // Must be matching with GET_STYLES and in order of priority
    public static final Style[] GET_STYLES = {BOLDITALIC,BOLD,ITALIC,STRIKETHROUGH,UNDERLINE,OBFUSCATED}; // Must be matching with GET_PREFIXES and in order of priority
    public static final String[] GET_NAMES = {"BOLDITALIC","BOLD","ITALIC","STRIKETHROUGH","UNDERLINE","OBFUSCATED"}; // Must be matching with GET_STYLES and GET_PREFIXES and in order of priority
}
