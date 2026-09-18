package me.bryansmit.utils;

import me.bryansmit.Command;

public sealed interface ParseResult {
    record Ok(Command command) implements ParseResult {
    }

    record Error(String message) implements ParseResult {
    }
}
