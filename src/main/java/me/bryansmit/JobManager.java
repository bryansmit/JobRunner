package me.bryansmit;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class JobManager {

    private final HashMap<Integer, Job> jobs;

    public JobManager() {
        this.jobs = new HashMap<>();
    }

    public Job create(Command command) {
        int id = jobs.size() + 1;

        Job newJob = new Job(id, command.name(), command.argument(), JobStatus.PENDING, null, -1);
        jobs.put(id, newJob);

        return newJob;
    }

    public Optional<Job> find(int id) {
        return Optional.ofNullable(this.jobs.get(id));
    }

    public List<Job> findAll() {
        return List.copyOf(this.jobs.values());
    }

    public void delete(int id) {
        this.jobs.remove(id);
    }
}
