package web.admin.demo.vo;

import lombok.Getter;
import web.admin.demo.dto.AddressVo;
import web.admin.demo.dto.Attached_fileVo;

import java.util.List;

@Getter
public class BoardVo {
    private String category;
    private String startDate;
    private String endDate;
    private String title;
    private AddressVo addressVo;
    private int[] checklist;
    private String password;
    private String content;
    private List<Attached_fileVo> AttachedFile;
    private String userName;

    public BoardVo(String category, String startDate, String endDate, String title, AddressVo addressVo, int[] checklist, String password, String content, List<Attached_fileVo> AttachedFile, String userName) {
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.addressVo = addressVo;
        this.checklist = checklist;
        this.password = password;
        this.content = content;
        this.AttachedFile = AttachedFile;
        this.userName = userName;
    }
}
