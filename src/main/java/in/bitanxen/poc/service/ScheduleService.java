package in.bitanxen.poc.service;

public interface ScheduleService {
    void updateSchedule(String cron);
    void cancelSchedule(String scheduleId);
}
