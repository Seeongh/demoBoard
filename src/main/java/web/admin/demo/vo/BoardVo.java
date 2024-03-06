package web.admin.demo.vo;

import lombok.Getter;
import web.admin.demo.dto.Address;
import web.admin.demo.dto.Attached_file;

import java.util.List;

@Getter
public class BoardVo {
    private String category;
    private String startDate;
    private String endDate;
    private String title;
    private Address address;
    private int[] checklist;
    private String password;
    private String content;
    private List<Attached_file> AttachedFile;
    private String userName;

    public BoardVo(String category, String startDate, String endDate, String title, Address address, int[] checklist, String password, String content, List<Attached_file> AttachedFile, String userName) {
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.address = address;
        this.checklist = checklist;
        this.password = password;
        this.content = content;
        this.AttachedFile = AttachedFile;
        this.userName = userName;
    }
}
