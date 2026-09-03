package me.bryansmit;

import java.util.Arrays;

public class Main {

    private static final String CREATE_COMMAND = "create";

    private final JobManager jobManager;

    public Main() {
        this.jobManager = new JobManager();
    }


    static void main(String[] args) {
        if (!hasEnoughArguments(args)) {
            System.out.println("Invalid amount of parameters.");

            return;
        }

        new Main().start(args);
    }

    private void start(String[] args) {
        String command = args[0];
        String subCommand = args[1];
        String thirdParam = args[2];

        if (!command.equalsIgnoreCase("job")) {
            System.out.println("Command is not supported.");
            return;
        }

        switch (subCommand) {
            case CREATE_COMMAND:
                String fourthParam;

                if (args.length >= 4) {
                    fourthParam = args[3];
                } else {
                    System.out.println("Invalid amount of parameters");
                    return;
                }

                Job job = this.jobManager.createNew(thirdParam, fourthParam);

                System.out.println("Created job: " + job.name());

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + subCommand);
        }
        ;
    }

    private static boolean hasEnoughArguments(String[] args) {
        if (args.length < 3) {
            return false;
        }

        return true;
    }
}
