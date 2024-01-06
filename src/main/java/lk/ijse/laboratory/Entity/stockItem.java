package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class stockItem {
    private String itemCode;
    private String userId;
    private String description;
    private int qtyOnHand;
    private String category;
    private int warningLevel;
}
