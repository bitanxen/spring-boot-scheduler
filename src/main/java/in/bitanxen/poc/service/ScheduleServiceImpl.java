package in.bitanxen.poc.service;

import in.bitanxen.poc.config.ScheduleContextHolder;
import in.bitanxen.poc.config.ScheduleInfo;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final TaskScheduler taskScheduler;

    public ScheduleServiceImpl(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void updateSchedule(String cron) {
        List<LocalDateTime> todaySchedule = getTodaySchedule(cron);

        Collection<ScheduleInfo> scheduledFutures = ScheduleContextHolder.getSchedule().values();
        for(ScheduleInfo existingSchedules : scheduledFutures) {
            ScheduledFuture<?> scheduledFuture = existingSchedules.getScheduledFuture();
            if(!scheduledFuture.isCancelled() && !scheduledFuture.isDone()) {
                scheduledFuture.cancel(true);
            }
        }

        for(LocalDateTime localDateTime : todaySchedule) {
            String taskId = UUID.randomUUID().toString();

            ScheduledFuture<?> scheduledTask = taskScheduler.schedule(() -> job(taskId, localDateTime), Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

            ScheduleInfo scheduleInfo = new ScheduleInfo(taskId, localDateTime, scheduledTask);
            ScheduleContextHolder.updateSchedule(taskId, scheduleInfo);
        }
    }

    @Override
    public void cancelSchedule(String scheduleId) {
        ScheduleInfo scheduleInfo = ScheduleContextHolder.getSchedule().get(scheduleId);
        if(scheduleInfo == null) {
            return;
        }

        ScheduledFuture<?> scheduledFuture = scheduleInfo.getScheduledFuture();
        if(!scheduledFuture.isCancelled() && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
    }

    public List<LocalDateTime> getTodaySchedule(String cron) {
        List<LocalDateTime> schedules = new ArrayList<>();

        SimpleTriggerContext simpleTriggerContext = new SimpleTriggerContext();
        CronTrigger cronTrigger = new CronTrigger(cron);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime eod = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        while (currentTime.isBefore(eod)) {
            Date from = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
            simpleTriggerContext.update(null, null, from);
            LocalDateTime nextFireAt = cronTrigger.nextExecutionTime(simpleTriggerContext)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            schedules.add(nextFireAt);
            currentTime = nextFireAt;
        }
        return schedules;
    }

    public void job(String taskId, LocalDateTime scheduledTime) {
        System.out.println("Task ["+taskId+", Scheduled Time : "+scheduledTime+"] started now at "+LocalDateTime.now());

        try{
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        System.out.println("Task "+taskId+" ended");
    }
}
