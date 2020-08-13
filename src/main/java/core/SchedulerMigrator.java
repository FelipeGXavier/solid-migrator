package core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import config.Env;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerMigrator {

    private final Env env;
    private final GuiceJobFactory jobFactory;
    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static final Logger logger = LoggerFactory.getLogger(SchedulerMigrator.class);

    @Inject
    public SchedulerMigrator(Env env, GuiceJobFactory jobFactory) {
        this.env = env;
        this.jobFactory = jobFactory;
    }

    public void init() throws SchedulerException {
        logger.info("Scheduling the migrator job");
        String expression = this.env.getConfiguration().getCron();
        Scheduler scheduler = this.schedulerFactory.getScheduler();
        scheduler.setJobFactory(this.jobFactory);
        scheduler.start();
        JobDetail jobDetail = JobBuilder.newJob(MigratorJob.class).withIdentity("migrator job").build();
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(expression)).withIdentity("trigger migrator").build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
