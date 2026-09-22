package me.bryansmit.cli;

public sealed interface Command {
    record Create(String name, String shellCommand) implements Command {
    }

    record List() implements Command {
    }

    record Find(int jobId) implements Command {
    }

    record Delete(int jobId) implements Command {
    }

    record Run(int jobId) implements Command {
    }

    record Exit() implements Command {
    }
}