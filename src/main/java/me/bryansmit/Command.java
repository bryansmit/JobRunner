package me.bryansmit;

public record Command(CommandType type, String name, String argument) { }
