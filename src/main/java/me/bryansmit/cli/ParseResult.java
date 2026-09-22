package me.bryansmit.cli;

public sealed interface ParseResult {
    record Ok(Command command) implements ParseResult {
    }

    record Error(String message) implements ParseResult {
    }
}
