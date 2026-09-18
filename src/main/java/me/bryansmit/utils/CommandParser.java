package me.bryansmit.utils;

import me.bryansmit.Command;
import me.bryansmit.CommandType;

public final class CommandParser {
    public static ParseResult parse(String input) {
        int firstSpace = input.indexOf(" ");
        String type = firstSpace > -1 ? input.substring(0, firstSpace) : input;
        String argument = firstSpace > -1 ? input.substring(firstSpace + 1).trim() : null;

        CommandType commandType;

        try {
            commandType = CommandType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ParseResult.Error("The command (" + type + ") is not a valid command.");
        }

        if (argument == null && commandType != CommandType.LIST && commandType != CommandType.EXIT) {
            return new ParseResult.Error("Invalid arguments for command (" + type + ").");
        }

        String name = null;

        if (commandType == CommandType.CREATE) {
            int secondSpace = argument.indexOf(" ");
            name = argument.substring(0, secondSpace + 1).trim();
            argument = argument.substring(secondSpace + 1).trim();
        }

        Command command;

        try {
            command = switch (commandType) {
                case CommandType.CREATE -> new Command.Create(name, argument);
                case CommandType.LIST -> new Command.List();
                case CommandType.FIND -> new Command.Find(Integer.parseInt(argument));
                case CommandType.RUN -> new Command.Run(Integer.parseInt(argument));
                case CommandType.DELETE -> new Command.Delete(Integer.parseInt(argument));
                case CommandType.EXIT -> new Command.Exit();
            };
        } catch (NumberFormatException e) {
            return new ParseResult.Error("The Job ID (" + argument + ") is not valid.");
        }

        return new ParseResult.Ok(command);
    }
}
