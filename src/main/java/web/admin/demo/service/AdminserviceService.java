package web.admin.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import web.admin.demo.dto.Attached_fileVo;
import web.admin.demo.dto.BoardDto;
import web.admin.demo.dto.ResultDto;
import web.admin.demo.repository.AdminserviceRepository;
import web.admin.demo.util.ImageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminserviceService {

    private final AdminserviceRepository adminRepository;
    private final int SUCCESS = 1;


    public List<Attached_fileVo> uploadFiles(MultipartFile[] files) throws Exception {
        List<Attached_fileVo> resultFile = new ArrayList();
        int cnt = 0;
        if(files.length != 0) { //첨부파일 존재
            for(MultipartFile file: files) {
                Attached_fileVo attachedFile = new Attached_fileVo();
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
         dto.getAddressVo().setBoardSeq( dto.getBoardSeq());
         adminRepository.insAddress(dto.getAddressVo());
         //첨부파일 insert
         int result = 0;
         if(dto.getAttachedFiles() != null) {
             for(Attached_fileVo attachedfile : dto.getAttachedFiles()) {
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

        return result;
    }

    @Transactional
    public int changeBoard(BoardDto dto) {
        //메인 게시판 insert
        int boardSeq = adminRepository.changeBoard(dto);
        //주소 insert
        dto.getAddressVo().setBoardSeq( dto.getBoardSeq());
        adminRepository.changeAddress(dto.getAddressVo());
        //첨부파일 insert
        int result = 0;
        if(dto.getAttachedFiles() != null) {
            for(Attached_fileVo attachedfile : dto.getAttachedFiles()) {
                attachedfile.setBoardSeq( dto.getBoardSeq());
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


    public List<ResultDto> findAll(Map<String,Object> map) {

        return adminRepository.findAll(map);
    }

    public List<ResultDto> search(Map<String,Object> map) {
        return adminRepository.search(map);
    }

    public Map<String,Object> findbyId(int boardseq) {
        return adminRepository.findbyId(boardseq);
    }

    @Transactional
    public void delete( List<String> boardArr) {

        for(String boardseq : boardArr) {
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("boardSeq",Integer.parseInt(boardseq));
            adminRepository.deleteBoard(mapParam);
            adminRepository.deleteAddress(mapParam);
            adminRepository.deleteAF(mapParam);
        }
    }

    public String findfile(Map<String,Object> map) {
        return adminRepository.findfile(map);
    }

    public void deletefiles(Map<String,Object> map) throws IOException {
        adminRepository.deletefiles(map);
        ImageUtil.remove((String)map.get("savedName"));
    }
    public int listCnt(Map<String,Object> map) { return adminRepository.listCnt(map); }
}
