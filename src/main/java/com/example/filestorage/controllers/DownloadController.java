package com.example.filestorage.controllers;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.*;

@RestController
@RequestMapping("/get")
public class DownloadController {

    private final String ROOT = "/home/konstantin/Work/testFolder";

    @GetMapping("/**")
    private void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = ROOT + request.getServletPath().replace("/get", "");
        File fileToDownload = new File(filePath);
        if (fileToDownload.exists() && fileToDownload.isFile()) {
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileToDownload.getName() + "\""));
            response.setContentLength((int) fileToDownload.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(fileToDownload));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            System.out.println("File downloaded successfully from " + filePath);
        }
    }

}
