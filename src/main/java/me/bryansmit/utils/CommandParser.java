package me.bryansmit.utils;

import me.bryansmit.Command;
import me.bryansmit.CommandType;

import java.util.Optional;

public final class CommandParser {
    private String restInput = null;

    public ParseResult parse(String input) {
        int firstSpace = input.indexOf(" ");
        String cmd = firstSpace > -1 ? input.substring(0, firstSpace) : input;

        String firstArgument = "";

        if (firstSpace > -1) {
            this.restInput = input.substring(firstSpace + 1).trim();

            firstArgument = this.nextArgument().orElse("");
        }

        CommandType commandType;

        try {
            commandType = CommandType.valueOf(cmd.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ParseResult.Error("The command (" + cmd + ") is not a valid command.");
        }

        if (firstArgument.isEmpty() && commandType != CommandType.LIST && commandType != CommandType.EXIT) {
            return new ParseResult.Error("Invalid argument for command (" + cmd + ").");
        }

        String secondArgument = "";

        if (commandType == CommandType.CREATE) {
            secondArgument = nextArgument().orElse("");

            if (secondArgument.isEmpty()) {
                return new ParseResult.Error("Invalid second argument for command (" + cmd + ").");
            }
        }

        Command command;

        try {
            command = switch (commandType) {
                case CommandType.CREATE -> new Command.Create(firstArgument, secondArgument);
                case CommandType.LIST -> new Command.List();
                case CommandType.FIND -> new Command.Find(Integer.parseInt(firstArgument));
                case CommandType.RUN -> new Command.Run(Integer.parseInt(firstArgument));
                case CommandType.DELETE -> new Command.Delete(Integer.parseInt(firstArgument));
                case CommandType.EXIT -> new Command.Exit();
            };
        } catch (NumberFormatException e) {
            return new ParseResult.Error("The Job ID (" + firstArgument + ") is not valid.");
        }

        return new ParseResult.Ok(command);
    }

    private Optional<String> nextArgument() {
        if (this.restInput.isEmpty()) {
            return Optional.empty();
        }

        StringBuilder argument = new StringBuilder();

        if (this.restInput.startsWith("\"")) {
            if (!this.restInput.substring(1).contains("\"")) {
                return Optional.empty();
            }

            for (int i = 1; i < this.restInput.length(); i++) {
                if (this.restInput.charAt(i) == '"') {
                    this.restInput = this.restInput.substring(i + 1).trim();
                    break;
                }

                argument.append(this.restInput.charAt(i));
            }
        } else {
            for (int i = 0; i < this.restInput.length(); i++) {
                if (this.restInput.charAt(i) == ' ') {
                    this.restInput = this.restInput.substring(i + 1).trim();

                    return Optional.of(argument.toString());
                }

                argument.append(this.restInput.charAt(i));
            }
        }

        return Optional.of(argument.toString().trim());
    }
}
