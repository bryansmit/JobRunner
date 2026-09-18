package me.bryansmit;

import me.bryansmit.utils.CommandParser;
import me.bryansmit.utils.ParseResult;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private final JobManager jobManager;

    public Main() {
        this.jobManager = new JobManager();
    }


    static void main() {
        new Main().start();
    }

    private void start() {
        Scanner scanner = new Scanner(System.in);
        boolean notExit = true;

        while (scanner.hasNextLine() && notExit) {
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            ParseResult parseResult = CommandParser.parse(input);

            if (parseResult instanceof ParseResult.Error(String message)) {
                System.out.println(message);
                continue;
            }

            Command command = ((ParseResult.Ok) parseResult).command();

            switch (command) {
                case Command.Create createCommand:
                    Job job = this.jobManager.create(createCommand.name(), createCommand.shellCommand());

                    System.out.println("Created job: " + job.id());

                    break;
                case Command.List _:
                    this.jobManager.findAll().forEach((j) -> System.out.printf("[%d] %s -> %s\n", j.id(), j.name(), j.command()));

                    break;
                case Command.Delete deleteCommand:
                    this.jobManager.delete(deleteCommand.jobId());

                    break;
                case Command.Find findCommand:
                    Job foundJob;

                    try {
                        foundJob = this.jobManager.find(findCommand.jobId()).orElseThrow();
                    } catch (NoSuchElementException exception) {
                        System.out.println("Job not found.");
                        continue;
                    }

                    System.out.printf("[%d] %s -> %s (%s)\n", foundJob.id(), foundJob.name(), foundJob.command(), foundJob.status());

                    break;
                case Command.Exit _:
                    System.out.println("You are leaving the application.");
                    notExit = false;
                    break;
                case Command.Run runCommand:
                    break;
            }
        }
    }
}
