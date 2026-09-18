package me.bryansmit.utils;

import me.bryansmit.Command;
import me.bryansmit.CommandType;

import java.util.Optional;

public final class CommandParser {
    public static Optional<Command> parse(String input) {
        int firstSpace = input.indexOf(" ");
        String type = firstSpace > -1 ? input.substring(0, firstSpace) : input;
        String argument = input.substring(firstSpace + 1).trim();

        int secondSpace = argument.indexOf(" ");
        String name = argument.substring(0, secondSpace + 1).trim();
        argument = argument.substring(secondSpace + 1).replace('"', ' ').trim();

        CommandType commandType;

        try {
            commandType = CommandType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        return Optional.of(new Command(commandType, name, argument));
    }
}
