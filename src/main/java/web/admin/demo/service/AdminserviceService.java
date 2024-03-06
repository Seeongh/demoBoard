package web.admin.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import web.admin.demo.dto.Attached_file;
import web.admin.demo.dto.BoardDto;
import web.admin.demo.dto.ResultDto;
import web.admin.demo.repository.AdminserviceRepository;
import web.admin.demo.util.ImageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminserviceService {

    private final AdminserviceRepository adminRepository;
    private final int SUCCESS = 1;


    public List<Attached_file> uploadFiles(MultipartFile[] files) throws Exception {
        List<Attached_file> resultFile = new ArrayList();
        int cnt = 0;
        if(files.length != 0) { //첨부파일 존재
            for(MultipartFile file: files) {
                Attached_file attachedFile = new Attached_file();
                attachedFile.setOriginal_name(file.getOriginalFilename());
                attachedFile.setSaved_name(ImageUtil.write(file));
                attachedFile.setSize(file.getSize());

                resultFile.add(attachedFile);
            }
        }

        return resultFile;
    }

    @Transactional
    public int insBoard(BoardDto dto) {
         //메인 게시판 insert
         int boardSeq = adminRepository.insBoard(dto);
         log.info("ash boardSeq = "  + dto.getBoardSeq());

         //주소 insert
         dto.getAddress().setBoardSeq( dto.getBoardSeq());
         adminRepository.insAddress(dto.getAddress());
         //첨부파일 insert
         int result = 0;
         if(dto.getAttachedFiles() != null) {
             for(Attached_file attachedfile : dto.getAttachedFiles()) {
                 attachedfile.setBoardSeq( dto.getBoardSeq());
                 log.info("ash img:" + attachedfile.getOriginal_name());
                 result = adminRepository.insAttachedFile(attachedfile);

                 if(result == SUCCESS) {
                     try {
                         ImageUtil.successCopy(attachedfile.getSaved_name());
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 }
             }
         }

        return 1;
    }

    public List<ResultDto> findAll() {
        return adminRepository.findAll();
    }

    public Map<String,Object> findbyId(int boardseq) {
        return adminRepository.findbyId(boardseq);
    }
}
