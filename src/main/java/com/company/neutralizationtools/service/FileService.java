package com.company.neutralizationtools.service;

import com.company.neutralizationtools.config.UploadConfig;
import com.company.neutralizationtools.dao.FileDao;
import com.company.neutralizationtools.model.File;
import com.company.neutralizationtools.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

import static com.company.neutralizationtools.utils.FileUtils.generateFileName;
import static com.company.neutralizationtools.utils.UploadUtils.*;

/**
 * 文件上传服务
 */
@Service
public class FileService {
    @Autowired
    private FileDao fileDao;


    /**
     * 上传文件
     *
     * @param md5
     * @param file
     */
    public void upload(String name,
                       String md5,
                       MultipartFile file) throws IOException {
        String path = UploadConfig.path + generateFileName();
        FileUtils.write(path, file.getInputStream());
        fileDao.save(new File(name, md5, path, new Date()));
    }

    /**
     * 分块上传文件
     *
     * @param md5
     * @param size
     * @param chunks
     * @param chunk
     * @param file
     * @throws IOException
     */
    public void uploadWithBlock(String name,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws IOException {
        String fileName = getFileName(md5, chunks);
        FileUtils.writeWithBlok(UploadConfig.path + fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
        addChunk(md5, chunk);
        if (isUploaded(md5)) {
            fileDao.save(new File(name, md5, UploadConfig.path + fileName, new Date()));
            removeKey(md5);
        }
    }

    /**
     * 检查Md5判断文件是否已上传
     *
     * @param md5
     * @return
     */
    public boolean checkMd5(String md5) {
        File file = new File();
        file.setMd5(md5);
        //当该文件正在上传，则秒传等待
        while (isExist(md5)) {
        }
        return fileDao.getByFile(file) == null;
    }
}
