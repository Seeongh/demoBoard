package web.admin.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.admin.demo.dto.Address;
import web.admin.demo.dto.Attached_file;
import web.admin.demo.dto.BoardDto;
import web.admin.demo.dto.ResultDto;
import web.admin.demo.mapper.AdminserviceMapper;

import java.util.List;
import java.util.Map;

@Repository
public class AdminserviceRepository {

    @Autowired
    AdminserviceMapper adminserviceMapper;

    public int insBoard(BoardDto dto) {
        return adminserviceMapper.insBoard(dto);
    }
    public void insAddress(Address address) { adminserviceMapper.insAddress(address);}
    public int insAttachedFile(Attached_file attachedFile) { return adminserviceMapper.insAttachedFile(attachedFile);}
    public int changeBoard(BoardDto dto) {
        return adminserviceMapper.changeBoard(dto);
    }
    public void changeAddress(Address address) { adminserviceMapper.changeAddress(address);}
    public int changeAttachedFile(Attached_file attachedFile) { return adminserviceMapper.changeAttachedFile(attachedFile);}

    public List<ResultDto> findAll() { return adminserviceMapper.findall();}
    public List<ResultDto> search(Map<String,Object> map) { return adminserviceMapper.search(map);}
    public Map<String,Object> findbyId(int boardSeq) { return adminserviceMapper.findbyId(boardSeq);}

    public void deleteBoard(Map<String,Object> mapParam) {  adminserviceMapper.deleteBoard(mapParam);}
    public void deleteAddress(Map<String,Object> mapParam) {  adminserviceMapper.deleteAddress(mapParam);}
    public void deleteAF(Map<String,Object> mapParam) {  adminserviceMapper.deleteAF(mapParam);}
}
