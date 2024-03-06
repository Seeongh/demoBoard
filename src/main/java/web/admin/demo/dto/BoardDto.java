package web.admin.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@Component
public class BoardDto {
    private String category;
    private String startDate;
    private String endDate;
    private String title;
    private Address address;
    private int[] checklist;
    private String password;
    private String content;
    private List<Attached_file> attachedFiles;
    private String writer = "temp";
    private int boardSeq;

}
