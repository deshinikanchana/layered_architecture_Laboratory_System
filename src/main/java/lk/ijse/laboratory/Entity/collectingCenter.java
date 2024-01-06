package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class collectingCenter {
    private String ccId;
    private String centerName;
    private String address;
    private String telNo;
    private String  email;
}
