package web.admin.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.admin.demo.dto.*;
import web.admin.demo.service.AdminserviceService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class AdminserviceController {

    @Autowired
    AdminserviceService adminService;

    @GetMapping("/addForm")
    public String addForm() {
        return "/addForm";
    }

    @PostMapping("/saveForm")
    public String saveForm(@ModelAttribute BoardDto boardDto, @ModelAttribute AddressVo saveAddressVo, @RequestParam("attached_file") MultipartFile[] files) throws Exception {
//    public String saveForm(@RequestBody Map<String,Object> map) throws Exception {
//        log.info("ash result : + " + map.toString());
        boardDto.setAddressVo(saveAddressVo);

        if(!files.equals("") && files != null) {
            List<Attached_fileVo> attachedfiles = adminService.uploadFiles(files);
            boardDto.setAttachedFiles(attachedfiles);
        }

        adminService.insBoard(boardDto);



        return "redirect:/list";
    }

    @PostMapping("/list")
    public String list(Model model, @RequestParam(value= "currentPage", defaultValue = "1" ) int currentPage) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type" , "all");

        PageVo page = new PageVo();
        int totalCnt = page.getTotalPage(adminService.listCnt(paramMap)); //전체 컨텐츠 수를 넣으면 페이지 수를 RETURN 함
        paramMap.put("pageCiteria", page.getPageCriteria()); // SQL - LIMIT 뒤에 올 한 페이지의 개수

        int startContent = ((currentPage)-1) * page.getPageCriteria() ;
        paramMap.put("StartContent", startContent); //SQL - OFFSET 뒤에 올 시작 컨텐츠 위치
        List<ResultDto> resultDto = adminService.findAll(paramMap);

        model.addAttribute("list", resultDto);
        model.addAttribute("totalPage" , totalCnt);
        model.addAttribute("currentPageInfo" , currentPage);
        return "/listView";
    }

    @PostMapping("/search")
    public Object search(Model model, @RequestParam Map<String,Object> map, @RequestParam(value= "currentPage", defaultValue = "1" ) int currentPage) throws Exception {
        map.put("type","search");
        PageVo page = new PageVo();
        int totalCnt = page.getTotalPage(adminService.listCnt(map)); //전체 컨텐츠 수를 넣으면 페이지 수를 RETURN 함
        map.put("pageCiteria", page.getPageCriteria()); // SQL - LIMIT 뒤에 올 한 페이지의 개수

        int startContent = ((currentPage)-1) * page.getPageCriteria() ;
        map.put("StartContent", startContent); //SQL - OFFSET 뒤에 올 시작 컨텐츠 위치

        List<ResultDto> resultDto = adminService.findAll(map);

        model.addAttribute("list", resultDto);
        return "/listView";
    }

    @GetMapping("/findById")
    public String readBoard(HttpServletRequest req, @RequestParam(defaultValue="") String boardSeq, Model model) {
        int seq = Integer.parseInt(boardSeq);
        Map<String,Object> map = adminService.findbyId(seq);

        model.addAttribute("board" , map);
        model.addAttribute("files" , map.get("files"));
        model.addAttribute("checklist" , map.get("checklist"));


        return "/readForm";

    }

    @GetMapping("/edit")
    public String editBoard(HttpServletRequest req, @RequestParam(defaultValue="") String boardSeq, Model model) {
        int seq = Integer.parseInt(boardSeq);
        Map<String,Object> map = adminService.findbyId(seq);

        model.addAttribute("board" , map);
        model.addAttribute("files" , map.get("files"));
        model.addAttribute("savefiles" , map.get("savefiles"));

        log.info("ash files " + map.get("files").toString());
        model.addAttribute("checklist" , map.get("checklist"));

        return "/editForm"; //수정 페이지로 이동
    }

    @PostMapping("/changeForm")
    public String changeForm(@ModelAttribute BoardDto boardDto, @ModelAttribute AddressVo saveAddressVo,
                             @RequestParam(name="attached_file" , required=false) MultipartFile[] files, @RequestParam(name="deleted_file" ,required=false) String[] deletedfiles) throws Exception {

        if(deletedfiles != null) { //삭제 첨부파일 존재시
            Map<String, Object> paramMap = new HashMap<>();
            for(String deletefile:deletedfiles ) {
                log.info("ash deleted: " + deletefile);
                paramMap.put("boardSeq",boardDto.getBoardSeq() );
                paramMap.put("originName",deletefile);
                String savedfile = adminService.findfile(paramMap);
                paramMap.put("savedName", savedfile);
                adminService.deletefiles(paramMap);
                paramMap.clear();
            }
        }


        boardDto.setAddressVo(saveAddressVo);

        if(files != null && !files.equals("") ) {
            List<Attached_fileVo> attachedfiles = adminService.uploadFiles(files);
            boardDto.setAttachedFiles(attachedfiles);
        }

        adminService.changeBoard(boardDto);
        return "redirect:/list";
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam(value="boardSeq[]") List<String> boardArr){
        adminService.delete(boardArr);

        return 1;
    }
}
