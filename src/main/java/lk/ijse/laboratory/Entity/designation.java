package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class designation {
    private String jboId;
    private  String jobTitle;
    private int workingHoursPerMonth;
    private float basicSalary;
    private float otPaymentsPerHour;
}
