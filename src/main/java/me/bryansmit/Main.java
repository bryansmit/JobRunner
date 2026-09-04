package me.bryansmit;

import me.bryansmit.utils.CommandParser;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String CREATE_COMMAND = "create";

    private final JobManager jobManager;

    public Main() {
        this.jobManager = new JobManager();
    }


    static void main(String[] args) {
        new Main().start(args);
    }

    private void start(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean notExit = true;

        while (notExit) {
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            Optional<Command> optionalCommand = CommandParser.parse(input);
            Command command = optionalCommand.orElse(null);

            if (command == null) {
                System.out.println("Invalid command.");
                continue;
            }

            switch (command.type()) {
                case CommandType.CREATE:
                    Job job = this.jobManager.create(command);

                    System.out.println("Created job: " + job.id());

                    break;
                case CommandType.LIST:
                    this.jobManager.findAll().forEach((j) -> System.out.printf("[%d] -> %s\n", j.id(), j.task()));

                    break;
                case CommandType.DELETE:
                    this.jobManager.delete(Integer.parseInt(command.argument()));

                    break;
                case CommandType.FIND:
                    Job foundJob;

                    try {
                        foundJob = this.jobManager.find(Integer.parseInt(command.argument())).orElseThrow();
                    } catch (NumberFormatException exception) {
                        System.out.println("Use the find with a valid job ID.");
                        continue;
                    } catch (NoSuchElementException exception) {
                        System.out.println("Job not found.");
                        continue;
                    }

                    System.out.printf("[%d] -> %s (%s)\n", foundJob.id(), foundJob.task(), foundJob.status());

                    break;
                case CommandType.EXIT:
                    System.out.println("You are leaving the application.");
                    notExit = false;
                    break;
            }
        }
    }
}
