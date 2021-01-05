package org.example.app.services;

import org.example.web.dto.DownloadFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {
  private final String rootPath = System.getProperty("catalina.home");
  private final File filesDir = new File(rootPath + File.separator + "external_uploads");

  public File getServerFile(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return null;
    }

    if (!filesDir.exists()) {
      filesDir.mkdirs();
    }

    return new File(filesDir.getAbsolutePath() + File.separator + fileName.replace(File.separator, ""));
  }

  public List<DownloadFile> getFiles() {
    ArrayList<DownloadFile> result = new ArrayList<>();
    if (filesDir.list() == null) {
      return result;
    }
    for (String file : Objects.requireNonNull(filesDir.list())) {
      result.add(new DownloadFile(file));
    }
    return result;
  }

  public void WriteFile(MultipartFile file) throws IOException {
    File serverFile = getServerFile(file.getOriginalFilename());
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
    try {
      stream.write(file.getBytes());
    } finally {
      stream.close();
    }
  }
}
