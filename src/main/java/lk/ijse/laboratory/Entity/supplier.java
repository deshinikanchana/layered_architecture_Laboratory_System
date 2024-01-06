package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class supplier {
    private String supId;
    private String name;
    private String telNo;
    private String email;
    private String category;
}
