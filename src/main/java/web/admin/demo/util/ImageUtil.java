package web.admin.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Component
public class ImageUtil {

    private static String imgPath;
    private static String imgTmp;
    public ImageUtil(@Value("${imgPath}") String IMGPATH,
                      @Value("${imgTmp}") String IMGTMP){
        imgPath = IMGPATH;
        imgTmp = IMGTMP;
    }
    public static String write(MultipartFile file) throws Exception {

        //저장 경로
        String projectPath = System.getProperty("user.dir") + imgTmp; //db에 저장하기 전까지 임시파일에 저장
        System.out.println("projectPath = " + projectPath);

        //식별자 생성
        UUID uuid = UUID.randomUUID();

        //식별자_원래 파일이름 = 저장될 파일이름
        String fileName = uuid + "_" + file.getOriginalFilename();

        //해당이름으로 빈 파일만듦
        File savedFile = new File(projectPath, fileName);

        //해당 파일을 원하는 경로로 이동
        file.transferTo(savedFile);
        return fileName;
    }

    public static void successCopy(String savedname) throws IOException {
        Path sourcePath = Path.of(imgTmp + "/" + savedname);
        Path targetPath = Path.of(imgPath + "/" + savedname);
        // 소스 파일에 대한 읽기 권한 확인
        if (!Files.isReadable(sourcePath)) {
            // 읽기 권한이 없으면 예외를 던집니다.
            log.info("권한없음");
            return ;
        }

        Files.move(sourcePath, targetPath);  // 파일 이동
        Files.delete(sourcePath); //기존 경로 삭제
    }
    public static void remove(String savedname) throws IOException {
        Path sourcePath = Path.of(imgTmp + "/" + savedname);
        Path targetPath = Path.of(imgPath + "/" + savedname);

        //Files.delete(sourcePath);
        // 소스 파일에 대한 읽기 권한 확인
        if (!Files.isReadable(sourcePath)) {
            // 읽기 권한이 없으면 예외를 던집니다.
            log.info("권한없음");
            return ;
        }
        Files.delete(sourcePath); //기존 경로 삭제
    }

}
