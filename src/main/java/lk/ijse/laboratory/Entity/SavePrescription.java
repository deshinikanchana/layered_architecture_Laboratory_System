package lk.ijse.laboratory.Entity;

import lk.ijse.laboratory.Dto.Tm.prescriptionTm;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class SavePrescription {
    private String presId;
    private String ptId;
    private String refBy;
    private float duePayment;
    private float total;
    private Date date;
    private List<prescriptionTm> tmList = new ArrayList<>();
}
