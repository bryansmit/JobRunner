package me.bryansmit.utils;

import me.bryansmit.Command;
import me.bryansmit.CommandType;

import java.util.Optional;

public final class CommandParser {
    public static Optional<Command> parse(String input) {
        int firstSpace = input.indexOf(" ");
        String commandName = firstSpace > -1 ? input.substring(0, firstSpace) : input;
        String argument = input.substring(firstSpace + 1).trim();

        CommandType type;

        try {
            type = CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        return Optional.of(new Command(type, argument));
    }
}
