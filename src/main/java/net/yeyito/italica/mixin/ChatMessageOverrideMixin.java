package net.yeyito.italica.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.client.util.TextCollector;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.yeyito.italica.chat.StyleRepository;
import net.yeyito.italica.chat.TextFormatter;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@Mixin(ChatMessages.class)
public class ChatMessageOverrideMixin {
    @Shadow private static String getRenderedChatMessage(String messagex) {return null;}
    @Shadow private static @Final OrderedText SPACES;
    /**
     * @author Yeyito
     * @reason Idk bro this code is old as hell I haven't revisited it in a while there's probably a better way to do this through an inject or something but
     * you know WHAT? IT WORKS.
     */
    @Overwrite

    public static List<OrderedText> breakRenderedChatMessageLines(StringVisitable message, int width, TextRenderer textRenderer) {
        TextCollector textCollector = new TextCollector();
        message.visit((style, messagex) -> {
            List<Object> StyleList = TextFormatter.getStyle(messagex);
            for (int i = 0; i < messagex.getBytes().length; i++) {
                if (!StyleList.get(i).equals(false)) {
                    textCollector.add(StringVisitable.styled(Character.toString(messagex.charAt(i)), TextFormatter.combineStyles(style, (Style) StyleList.get(i))));
                }
            }
            return Optional.empty();
        }, StyleRepository.EMPTY);
        List<OrderedText> list = Lists.newArrayList();
        textRenderer.getTextHandler().wrapLines(textCollector.getCombined(), width, StyleRepository.EMPTY, (stringVisitable, boolean_) -> {
            OrderedText orderedText = Language.getInstance().reorder(stringVisitable);
            list.add(boolean_ ? OrderedText.concat(SPACES, orderedText) : orderedText);
        });
        return list.isEmpty() ? Lists.newArrayList(new OrderedText[]{OrderedText.EMPTY}) : list;
    }
}
