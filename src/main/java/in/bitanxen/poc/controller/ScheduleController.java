package in.bitanxen.poc.controller;

import in.bitanxen.poc.config.ScheduleContextHolder;
import in.bitanxen.poc.config.ScheduleInfo;
import in.bitanxen.poc.dto.CreateScheduleDTO;
import in.bitanxen.poc.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ScheduleInfo>> getCurrentSchedule() {
        return ResponseEntity.ok(new ArrayList<>(ScheduleContextHolder.getSchedule().values()));
    }

    @PostMapping("/")
    public ResponseEntity<String> updateSchedule(@RequestBody CreateScheduleDTO createSchedule) {
        scheduleService.updateSchedule(createSchedule.getCronExpression());
        return ResponseEntity.ok("Schedule Updated");
    }

    @PostMapping("/cancel/{scheduleId}")
    public ResponseEntity<String> cancelSchedule(@PathVariable String scheduleId) {
        scheduleService.cancelSchedule(scheduleId);
        return ResponseEntity.ok("Schedule Cancelled");
    }
}
