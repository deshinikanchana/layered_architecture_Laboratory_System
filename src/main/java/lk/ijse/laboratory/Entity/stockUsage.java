package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class stockUsage {
    private String testId;
    private String itemCode;
    private int qtyPerTest;
}
