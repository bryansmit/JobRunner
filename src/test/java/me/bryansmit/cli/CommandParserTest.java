package me.bryansmit.cli;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CommandParserTest {
    private final CommandParser parser = new CommandParser();

    @Test
    void shouldParseListCommand() {
        ParseResult.Ok result = ((ParseResult.Ok) parser.parse("list"));

        Assertions.assertInstanceOf(Command.List.class, result.command());
    }

    @Test
    void shouldParseExitCommand() {
        ParseResult.Ok result = ((ParseResult.Ok) parser.parse("exit"));

        Assertions.assertInstanceOf(Command.Exit.class, result.command());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "create \"backup\" \"/scripts/backup.sh\"",
            "create backup \"     /scripts/backup.sh\"",
            "create backup /scripts/backup.sh"
    })
    void shouldParseCreateCommand(String input) {
        ParseResult.Ok result = ((ParseResult.Ok) parser.parse(input));

        Assertions.assertInstanceOf(Command.Create.class, result.command());

        Command.Create command = (Command.Create) result.command();

        Assertions.assertEquals("backup", command.name());
        Assertions.assertEquals("/scripts/backup.sh", command.shellCommand());
    }

    @Test
    void shouldParseFindCommand() {
        ParseResult.Ok result = ((ParseResult.Ok) parser.parse("find 1"));

        Assertions.assertInstanceOf(Command.Find.class, result.command());

        Command.Find command = (Command.Find) result.command();

        Assertions.assertEquals(1, command.jobId());
    }

    @Test
    void shouldRejectInvalidCommand() {
        ParseResult.Error result = ((ParseResult.Error) parser.parse("invalid"));

        Assertions.assertEquals("The command (invalid) is not a valid command.", result.message());
    }
}
