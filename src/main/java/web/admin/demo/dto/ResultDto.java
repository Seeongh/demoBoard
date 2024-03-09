package web.admin.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ResultDto {
    private String category;
    private String startDate;
    private String endDate;
    private String title;
    private AddressVo addressVo;
    private String password;
    private String content;
    private String writer;
    private int boardSeq;
    private Date regTime;
    private Integer[] checklist;
    private int attachedFileCount;

    private String typeaddr;
    private String mainaddr;
    private String detailaddr;
    private String postalcode;
    private String[] files;
    private String[] savefiles;

}
