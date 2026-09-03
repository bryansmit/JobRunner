package me.bryansmit;

import java.util.HashMap;

public class JobManager {

    private final HashMap<Integer, Job> jobs;

    public JobManager() {
        this.jobs = new HashMap<>();
    }

    public Job createNew(String thirdParam, String fourthParam) {
        Job newJob = new Job(thirdParam, fourthParam, JobStatus.PENDING);
        jobs.put(jobs.size() + 1, newJob);

        return newJob;
    }

    public HashMap<Integer, Job> getJobs() {
        return jobs;
    }
}
