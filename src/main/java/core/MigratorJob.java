package core;

import com.google.inject.Inject;
import migrator.Migrator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MigratorJob implements Job {

    @Inject
    private Migrator migrator;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.migrator.run();
    }
}
