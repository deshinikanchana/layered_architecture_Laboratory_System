package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class attendance {
    private String empId;
    private Date date;
    private Time inTime;
    private Time outTime;
    private String status;
}
