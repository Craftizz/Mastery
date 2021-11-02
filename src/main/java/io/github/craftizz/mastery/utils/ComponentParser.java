package io.github.craftizz.mastery.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentParser {

    public final static MiniMessage miniMessage = MiniMessage.get();

    /**
     * Use to parse languages for GUIs
     *
     * @param message the string message to be parsed
     * @return the parsed language in component
     */
    public static Component parse(@NotNull final String message) {
        return miniMessage.parse(message)
                .decoration(TextDecoration.ITALIC, false);
    }

    /**
     * Use to parse languages for GUIs with the placeholders as parsed
     *
     * @param message the string message to be parsed
     * @return the parsed language in component
     */
    public static Component parse(@NotNull final String message,
                                  @NotNull final String... placeholders) {
        return miniMessage.parse(message, placeholders)
                .decoration(TextDecoration.ITALIC, false);
    }

    /**
     * Use to parse many language for GUIs
     *
     * @param messages the list of string messages to be parsed
     * @return the parsed languages in component
     */
    public static List<Component> parse(@NotNull final List<String> messages) {

        final List<Component> parsedComponents = new ArrayList<>();

        for (final String message : messages) {
            parsedComponents.add(parse(message));
        }

        return parsedComponents;
    }

    /**
     * Use to parse many language for GUIs
     *
     * @param messages the list of string messages to be parsed
     * @param placeholders the placeholders that will be replaced
     * @return the parsed languages in component
     */
    public static List<Component> parse(@NotNull final List<String> messages,
                                        @NotNull final String... placeholders) {

        final List<Component> parsedComponents = new ArrayList<>();

        for (final String message : messages) {
            parsedComponents.add(parse(message, placeholders));
        }

        return parsedComponents;
    }

}
