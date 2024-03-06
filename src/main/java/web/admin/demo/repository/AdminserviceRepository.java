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

    public List<ResultDto> findAll() { return adminserviceMapper.findall();}

    public Map<String,Object> findbyId(int boardSeq) { return adminserviceMapper.findbyId(boardSeq);}
}
