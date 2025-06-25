

import com.complaint.backend.entities.User;
import com.complaint.backend.entities.Attendance;
import com.complaint.backend.repositories.UserRepository;
import com.complaint.backend.repositories.AttendanceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceScheduler {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceScheduler(UserRepository userRepository,
                               AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
    }

    // Runs daily at 11:59 PM
    @Scheduled(cron = "0 59 23 * * ?")
    public void markAbsentForDay() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            boolean hasAttendance = attendanceRepository.existsByEmailAndTimeBetween(
                    user.getEmail(), // Using email as unique identifier
                    startOfDay,
                    endOfDay
            );

            if (!hasAttendance) {
                Attendance absentRecord = new Attendance();
                absentRecord.setName(user.getName());
                absentRecord.setEmail(user.getEmail());
                absentRecord.setTime(endOfDay);
                absentRecord.setStatus("ABSENT");
                absentRecord.setLat(0.0); // Default values
                absentRecord.setLng(0.0);
                absentRecord.setPhoto("ABSENT");
                attendanceRepository.save(absentRecord);
            }
        }
    }
}