package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.consumer.ConsumerBinding;
import ro.tuc.ds2020.dto.ActivityDto;
import ro.tuc.ds2020.model.Activity;
import ro.tuc.ds2020.model.Patient;
import ro.tuc.ds2020.repository.ActivityRepo;
import ro.tuc.ds2020.repository.PatientRepo;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@EnableBinding(ConsumerBinding.class)
public class ConsumerServiceImpl {

    private final PatientRepo patientRepo;
    private final ActivityRepo activityRepo;

    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/notifications")
    public void send(@Payload String message) {
        this.messagingTemplate.convertAndSend("/topic/notifications", message);
    }

    @StreamListener(target = ConsumerBinding.channel)
    public void processMsg(ActivityDto activityDto) {
        if (checkSleepTime(activityDto)) {
            this.send("Patient is sleeping too much!");
            System.out.println("Patient is sleeping too much!");
        }
        if (checkLivingTime(activityDto)) {
            this.send("Patient is outdoor for more than 5 hours!");
            System.out.println("Patient is outdoor for more than 5 hours!");
        }
        if (checkBathroomTime(activityDto)) {
            this.send("Patient is in bathroom for more than 30 minutes!");
            System.out.println("Patient is in bathroom for more than 30 minutes!");
        }

        //Save the activity in the database
        Patient patient = patientRepo.findById(activityDto.getPatient_id()).orElseThrow(RuntimeException::new);
        Activity activity = Activity.builder().name(activityDto.getActivity()).startDate(activityDto.getStart())
                .endDate(activityDto.getEnd()).patient(patient).build();
        activityRepo.save(activity);
    }

    private boolean checkSleepTime(ActivityDto activityDto) {
        String activity = activityDto.getActivity();

        //If the activity is sleeping
        if (activity.equals("Sleeping")) {
            LocalDateTime startDate = activityDto.getStart();
            LocalDateTime endDate = activityDto.getEnd();

            //Get the duration of the activity
            Duration duration = Duration.between(endDate, startDate);
            Long diff = Math.abs(duration.toMinutes());

            if (diff > 420) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLivingTime(ActivityDto activityDto) {
        String activity = activityDto.getActivity();

        //If the activity is sleeping
        if (activity.equals("Leaving")) {
            LocalDateTime startDate = activityDto.getStart();
            LocalDateTime endDate = activityDto.getEnd();

            //Get the duration of the activity
            Duration duration = Duration.between(endDate, startDate);
            long diff = Math.abs(duration.toMinutes());

            return diff > 300;
        }

        return false;
    }

    private boolean checkBathroomTime(ActivityDto activityDto) {
        String activity = activityDto.getActivity();

        //If the activity is sleeping
        if (activity.equals("Toileting") || activity.equals("Showering") || activity.equals("Grooming")) {
            LocalDateTime startDate = activityDto.getStart();
            LocalDateTime endDate = activityDto.getEnd();

            //Get the duration of the activity
            Duration duration = Duration.between(endDate, startDate);
            Long diff = Math.abs(duration.toMinutes());

            if (diff > 30) {
                return true;
            }
        }
        return false;
    }
}
