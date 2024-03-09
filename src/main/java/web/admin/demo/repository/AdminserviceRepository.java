package web.admin.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.admin.demo.dto.AddressVo;
import web.admin.demo.dto.Attached_fileVo;
import web.admin.demo.dto.BoardDto;
import web.admin.demo.dto.ResultDto;
import web.admin.demo.mapper.AdminserviceMapper;
import web.admin.demo.util.ImageUtil;

import java.util.List;
import java.util.Map;

@Repository
public class AdminserviceRepository {

    @Autowired
    AdminserviceMapper adminserviceMapper;

    public int insBoard(BoardDto dto) {
        return adminserviceMapper.insBoard(dto);
    }
    public void insAddress(AddressVo addressVo) { adminserviceMapper.insAddress(addressVo);}
    public int insAttachedFile(Attached_fileVo attachedFile) { return adminserviceMapper.insAttachedFile(attachedFile);}
    public int changeBoard(BoardDto dto) {
        return adminserviceMapper.changeBoard(dto);
    }
    public void changeAddress(AddressVo addressVo) { adminserviceMapper.changeAddress(addressVo);}
    public int changeAttachedFile(Attached_fileVo attachedFile) { return adminserviceMapper.changeAttachedFile(attachedFile);}

    public List<ResultDto> findAll(Map<String, Object> map) { return adminserviceMapper.findall(map);}
    public List<ResultDto> search(Map<String,Object> map) { return adminserviceMapper.search(map);}
    public Map<String,Object> findbyId(int boardSeq) { return adminserviceMapper.findbyId(boardSeq);}

    public void deleteBoard(Map<String,Object> mapParam) {  adminserviceMapper.deleteBoard(mapParam);}
    public void deleteAddress(Map<String,Object> mapParam) {  adminserviceMapper.deleteAddress(mapParam);}
    public void deleteAF(Map<String,Object> mapParam) {  adminserviceMapper.deleteAF(mapParam);}

    public String findfile(Map<String,Object> map) {
        return adminserviceMapper.findfile(map);
    }
    public void deletefiles(Map<String,Object> map) {
        adminserviceMapper.deletefiles(map);
    }

    public int listCnt(Map<String,Object> map) { return adminserviceMapper.listCnt(map); }
}
