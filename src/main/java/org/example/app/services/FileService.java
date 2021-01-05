package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.DownloadFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class FileService {
  private final Logger logger = Logger.getLogger(this.getClass());
  private final String rootPath = System.getProperty("catalina.home");
  private final File filesDir = new File(rootPath + File.separator + "external_uploads");
  private final HashSet<String> allowedFiles = new HashSet<>();

  public FileService() {
    loadFileSet();
  }

  private void loadFileSet() {
    if (!filesDir.exists()) {
      filesDir.mkdirs();
    }

    allowedFiles.clear();
    for (String currFile : filesDir.list()) {
      allowedFiles.add(currFile);
    }
  }

  public File getServerFile(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return null;
    }

    if (!filesDir.exists()) {
      filesDir.mkdirs();
    }

    return new File(filesDir.getAbsolutePath() + File.separator + fileName.replace(File.separator, ""));
  }

  public boolean isFileExists(String fileName) {
    return allowedFiles.contains(fileName);
  }

  public List<DownloadFile> getFiles() {
    ArrayList<DownloadFile> result = new ArrayList<>();

    if (allowedFiles.isEmpty()) {
      return result;
    }
    for (String file : allowedFiles) {
      result.add(new DownloadFile(file));
    }
    return result;
  }

  public void WriteFile(MultipartFile file) throws IOException {
    String normalFileName = file.getOriginalFilename().replace(File.separator, "");
    File serverFile = getServerFile(normalFileName);
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
    try {
      stream.write(file.getBytes());
      if (!allowedFiles.contains(normalFileName)) {
        allowedFiles.add(normalFileName);
      }
    } finally {
      stream.close();
    }
  }
}
