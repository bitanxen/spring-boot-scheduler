package in.bitanxen.poc.config;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

public class ScheduleInfo {
    private String id;
    private LocalDateTime localDateTime;
    private ScheduledFuture<?> scheduledFuture;

    public ScheduleInfo(String id, LocalDateTime localDateTime, ScheduledFuture<?> scheduledFuture) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.scheduledFuture = scheduledFuture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}
