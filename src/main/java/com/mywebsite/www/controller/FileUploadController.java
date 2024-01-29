package com.mywebsite.www.controller;

import com.mywebsite.www.dao.FileUploadDao;
import com.mywebsite.www.domain.FileUploadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/upload")
public class FileUploadController {
    @Autowired
    FileUploadDao dao;

    @GetMapping("/multipartFile")
    public String init(){
        return "fileUpload";
    }

    @PostMapping("/multipartFile")
    public String upload(@RequestParam("f") MultipartFile multipartFile, @RequestParam("title") String title, Model m, RedirectAttributes rattr) throws Exception {
        if(!multipartFile.isEmpty()){
            byte[] file = multipartFile.getBytes();
            FileUploadDto dto = new FileUploadDto(file, title);

            if(dao.insert(dto)==1) {
                rattr.addFlashAttribute("msg","UPLOAD_OK");
                return "redirect:/upload/uploadList";
            }
        }
        m.addAttribute("msg", "UPLOAD_ERR");
        return "fileUpload";
    }

    @GetMapping("/uploadList")
    public String showList(Model m) throws Exception {
        List<FileUploadDto> list = dao.selectAll();
        m.addAttribute("list",list);
        return "uploadList";
    }
}
