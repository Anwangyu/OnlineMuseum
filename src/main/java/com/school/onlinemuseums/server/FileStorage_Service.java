package com.school.onlinemuseums.server;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage_Service {

    String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);
}
