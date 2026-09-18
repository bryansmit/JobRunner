package me.bryansmit;

public class JobRunner implements Runnable {

    private final Job job;

    public JobRunner (Job job) {
        this.job = job;
    }

    @Override
    public void run() {
        ProcessBuilder processBuilder = new ProcessBuilder();
    }
}
