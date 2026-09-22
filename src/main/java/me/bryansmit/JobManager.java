package me.bryansmit;

import java.util.*;

public class JobManager {

    private final ArrayList<Job> jobs = new ArrayList<>();

    public Job create(String name, String shellCommand) {
        Job newJob = new Job(UUID.randomUUID(), name, shellCommand, JobStatus.PENDING, null, -1);
        jobs.add(newJob);

        return newJob;
    }

    public Optional<Job> find(int id) {
        return Optional.ofNullable(this.jobs.get(id));
    }

    public HashMap<Integer, Job> findAll() {
        HashMap<Integer, Job> map = new HashMap<>();

        for (Job job : jobs) {
            map.put(this.jobs.indexOf(job), job);
        }

        return map;
    }

    public void delete(int id) {
        this.jobs.remove(id);
    }
}
