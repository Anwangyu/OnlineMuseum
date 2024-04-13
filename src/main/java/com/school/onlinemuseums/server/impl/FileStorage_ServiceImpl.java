package com.school.onlinemuseums.server.impl;

import com.school.onlinemuseums.server.FileStorage_Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class FileStorage_ServiceImpl implements FileStorage_Service {

    private final Path fileStorageLocation;

    // 支持的文件类型列表
    private final List<String> supportedFileTypes = Arrays.asList("mp4", "pdf","txt","docx","pptx","jpg","png","zip","doc");

    public FileStorage_ServiceImpl() {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("无法创建存储上传文件的目录", ex);
        }
    }

    public FileStorage_ServiceImpl(Path fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("文件名包含无效路径序列" + fileName);
            }

            // 获取文件扩展名
            String fileExtension = getFileExtension(fileName);

            // 检查文件类型是否受支持
            if (!supportedFileTypes.contains(fileExtension.toLowerCase())) {
                throw new RuntimeException("不支持的文件类型: " + fileExtension);
            }

            // 生成新的文件名，确保唯一性
            String newFileName = generateUniqueFileName(fileName);

            // 将文件保存到存储位置
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new RuntimeException("无法存储文件 " + fileName + "请重试", ex);
        }
    }



    // 生成唯一的文件名，以避免重复
    private String generateUniqueFileName(String fileName) {
        String baseName = StringUtils.stripFilenameExtension(fileName);
        String extension = getFileExtension(fileName);
        String uniqueName = baseName + "_" + System.currentTimeMillis() + "." + extension;
        return uniqueName;
    }


    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("未找到文件" + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("未找到文件 " + fileName, ex);
        }
    }

    // 获取文件扩展名
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
