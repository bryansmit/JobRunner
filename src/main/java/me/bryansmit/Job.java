package me.bryansmit;

import java.util.UUID;

public record Job (UUID id, String name, String command, JobStatus status, String output, int exitCode) { }
