package web.admin.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class AddressVo {
    private String mainAddr;
    private String detailAddr;
    private String postalcode;
    private String typeAddr;
    private int boardSeq;

}
