package lk.ijse.laboratory.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class patient {
    private String ptId;
    private String userId;
    private String ccId;
    private String name;
    private String gender;
    private String dob;
    private String telNo;
    private String email;

}
