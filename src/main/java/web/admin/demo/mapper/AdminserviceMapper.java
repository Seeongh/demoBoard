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
    public List<ResultDto> findall();

    public Map<String,Object> findbyId(int boardSeq);
}
