package me.bryansmit;

public record Job (int id, String name, String command, JobStatus status, String output, int exitCode) { }
