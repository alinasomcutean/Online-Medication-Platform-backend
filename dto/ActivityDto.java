package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.Activity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ActivityDto implements Serializable {

    String activity;
    LocalDateTime start;
    LocalDateTime end;
    UUID patient_id;
}
