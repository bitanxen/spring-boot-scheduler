package in.bitanxen.poc.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class ScheduleContextHolder {

    private static final Map<String, ScheduleInfo> scheduledMap = new HashMap<>();

    private ScheduleContextHolder() {

    }

    public static Map<String, ScheduleInfo> getSchedule() {
        return scheduledMap;
    }

    public static void updateSchedule(String id, ScheduleInfo scheduleInfo) {
        scheduledMap.put(id, scheduleInfo);
    }
}
