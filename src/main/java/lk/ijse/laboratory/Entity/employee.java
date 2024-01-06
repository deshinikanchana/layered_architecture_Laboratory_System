package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class employee {
    private String empId;
    private String jobId;
    private String userId;
    private String name;
    private String nic;
    private String address;
    private String email;
    private String telNo;

}
