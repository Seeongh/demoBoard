package web.admin.demo.mapper;


import org.apache.ibatis.annotations.Mapper;
import web.admin.demo.dto.AddressVo;
import web.admin.demo.dto.Attached_fileVo;
import web.admin.demo.dto.BoardDto;
import web.admin.demo.dto.ResultDto;
import web.admin.demo.util.ImageUtil;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminserviceMapper {
    public int insBoard(BoardDto dto);
    public void insAddress(AddressVo addressVo);
    public int insAttachedFile(Attached_fileVo attachedFile);
    public int changeBoard(BoardDto dto);
    public void changeAddress(AddressVo addressVo);
    public int changeAttachedFile(Attached_fileVo attachedFile);
    public List<ResultDto> findall(Map<String,Object> map);
    public List<ResultDto> search(Map<String,Object> map);

    public Map<String,Object> findbyId(int boardSeq);
    public void deleteBoard(Map<String,Object> map);
    public void deleteAddress(Map<String,Object> map);
    public void deleteAF(Map<String,Object> map);
    public String findfile(Map<String,Object> map) ;
    public void deletefiles(Map<String,Object> map) ;

    public int listCnt(Map<String,Object>map);
}
