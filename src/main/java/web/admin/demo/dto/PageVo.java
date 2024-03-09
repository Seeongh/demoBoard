package web.admin.demo.dto;

import lombok.Data;
import lombok.Getter;

@Data @Getter
public class PageVo {

    private int pageCriteria = 3 ; //한 페이지에 나올 데이터 개수
    private int totalPage; //총 페이지 수
    private int currentPage; //현재 페이지
    private int previewPage ; //이전 페이지
    private int nextViewPage; //다음 페이지

    public int getTotalPage(int listCnt) {
        //페이지 수 찾기
        if(listCnt > pageCriteria) { //paging?
            totalPage = listCnt/pageCriteria ; //페이지 수
            if(listCnt%pageCriteria != 0) {
                totalPage += 1;
            }
        }
        else {
            totalPage = 1;
        }


        return totalPage;
    }

}
