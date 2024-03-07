package web.admin.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.admin.demo.dto.Address;
import web.admin.demo.dto.Attached_file;
import web.admin.demo.dto.BoardDto;
import web.admin.demo.dto.ResultDto;
import web.admin.demo.service.AdminserviceService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class AdminserviceController {

    @Autowired
    AdminserviceService adminService;

    private static int pagecriteria = 3;

    @GetMapping("/addForm")
    public String addForm() {
        return "/addForm";
    }

    @PostMapping("/saveForm")
    public String saveForm(@ModelAttribute BoardDto boardDto, @ModelAttribute Address saveAddress, @RequestParam("attached_file") MultipartFile[] files) throws Exception {
//    public String saveForm(@RequestBody Map<String,Object> map) throws Exception {
//        log.info("ash result : + " + map.toString());
        boardDto.setAddress(saveAddress);

        if(!files.equals("") && files != null) {
            List<Attached_file> attachedfiles = adminService.uploadFiles(files);
            boardDto.setAttachedFiles(attachedfiles);
        }

        adminService.insBoard(boardDto);



        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<ResultDto> resultDto = adminService.findAll();
        int pageCnt = 0;
        int listCnt = resultDto.size();

        //페이지 수 찾기
        if(listCnt > pagecriteria) { //paging?
            pageCnt = listCnt/pagecriteria ; //페이지 수
            if(listCnt%pagecriteria != 0) {
                pageCnt += 1;
            }
        }
        else {
            pageCnt = 1;
        }


//        List<ResultDto> paginatedResults = new ArrayList<>();
//        for (int i = 0; i < pagecriteria; i++) {
//            paginatedResults.add(resultDto.get(i));
//        }


        model.addAttribute("list", resultDto);
        model.addAttribute("pageCnt" ,pageCnt);
        log.info("ash+ mapList" + resultDto.toString());
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

        log.info("ash files " + map.get("files").toString());
        model.addAttribute("checklist" , map.get("checklist"));

        return "/editForm"; //수정 페이지로 이동
    }

    @PostMapping("/changeForm")
    public String changeForm(@ModelAttribute BoardDto boardDto, @ModelAttribute Address saveAddress, @RequestParam("attached_file") MultipartFile[] files) throws Exception {

        boardDto.setAddress(saveAddress);

        if(!files.equals("") && files != null) {
            List<Attached_file> attachedfiles = adminService.uploadFiles(files);
            boardDto.setAttachedFiles(attachedfiles);
        }

        adminService.changeBoard(boardDto);
        return "redirect:/list";
    }


    @PostMapping("/search")
    @ResponseBody
    public Object search(@RequestParam Map<String,Object> map) throws Exception {
        log.info("ash param "  + map.toString());
        List<ResultDto> resultDto = adminService.search(map);
        log.info("search result" + resultDto.toString());
        return resultDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam(value="boardSeq[]") List<String> boardArr){
        adminService.delete(boardArr);

        return 1;
    }
}
