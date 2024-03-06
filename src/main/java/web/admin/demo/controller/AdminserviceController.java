package web.admin.demo.controller;

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
    public String saveForm(@ModelAttribute BoardDto boardDto, @ModelAttribute Address saveAddress, @RequestParam("attached_file") MultipartFile[] files) throws Exception {
//    public String saveForm(@RequestBody Map<String,Object> map) throws Exception {
//        log.info("ash result : + " + map.toString());
        boardDto.setAddress(saveAddress);

        if(!files.equals("") && files != null) {
            List<Attached_file> attachedfiles = adminService.uploadFiles(files);
            boardDto.setAttachedFiles(attachedfiles);
        }

        adminService.insBoard(boardDto);



        return "redirect: /list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<ResultDto> resultDto = adminService.findAll();
        model.addAttribute("list", resultDto);
        log.info("ash+ mapList" + resultDto.toString());
        return "/listView";
    }

    @GetMapping("/findById")
    public String readBoard(@RequestParam(defaultValue="") String boardSeq, Model model) {
        int seq = Integer.parseInt(boardSeq);
        Map<String,Object> map = adminService.findbyId(seq);

        model.addAttribute("board" , map);
        model.addAttribute("files" , map.get("files"));

//        Integer[] iCheck = (Integer[]) map.get("checklist");
//        int[] checkList = null;
//        if (iCheck != null) {
//            checkList = new int[iCheck.length];
//            for (int i = 0; i < iCheck.length; i++) {
//                if (iCheck[i] != null) {
//                    checkList[i] = iCheck[i].intValue();
//                }
//            }
//        }
        model.addAttribute("checklist" , map.get("checklist"));

        return "/readForm";

    }
}
