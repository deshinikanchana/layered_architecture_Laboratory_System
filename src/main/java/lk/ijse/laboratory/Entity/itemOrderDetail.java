package lk.ijse.laboratory.Entity;

import lk.ijse.laboratory.Dto.Tm.ordersTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class itemOrderDetail {
    private String orderId;
    private String SupplierId;
    private List<ordersTm> tmList = new ArrayList<>();
}
