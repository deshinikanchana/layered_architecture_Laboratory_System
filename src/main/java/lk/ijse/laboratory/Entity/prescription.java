package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class prescription {
    private String presId;
    private String ptId;
    private String refferedBy;
    private float totalAmount;
    private float duePayment;
}
