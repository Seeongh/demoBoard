package web.admin.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@Component
public class Attached_file {
    private String original_name;
    private String saved_name;
    private Long size;
    private MultipartFile file;
    private int boardSeq;

}
