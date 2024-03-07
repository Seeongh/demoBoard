package web.admin.demo.mapper;


import org.apache.ibatis.annotations.Mapper;
import web.admin.demo.dto.Address;
import web.admin.demo.dto.Attached_file;
import web.admin.demo.dto.BoardDto;
import web.admin.demo.dto.ResultDto;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminserviceMapper {
    public int insBoard(BoardDto dto);
    public void insAddress(Address address);
    public int insAttachedFile(Attached_file attachedFile);
    public int changeBoard(BoardDto dto);
    public void changeAddress(Address address);
    public int changeAttachedFile(Attached_file attachedFile);
    public List<ResultDto> findall();
    public List<ResultDto> search(Map<String,Object> map);

    public Map<String,Object> findbyId(int boardSeq);
    public void deleteBoard(Map<String,Object> map);
    public void deleteAddress(Map<String,Object> map);
    public void deleteAF(Map<String,Object> map);
}
