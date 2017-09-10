package com.example.demo1.Controller;

import com.google.gson.Gson;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class fileLoadController {

    private Gson gson = new Gson();
    private List<String> lineList = new ArrayList<>();

    @RequestMapping(value = "/fileLoad",method = RequestMethod.POST)
    public @ResponseBody String fileLoad(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        long startTime = System.currentTimeMillis();
        String fileName = file.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(fileSuffix);
        lineList.clear();
        File dir = new File("");
//        String path = dir.getCanonicalPath()+"/src/main/resources/static/images/upLoadFile/";

        String path = this.getClass().getResource("/").getPath()+"/static/images/upLoadFile/";
        //解决项目路径中文乱码问题
        path = URLDecoder.decode(path,"UTF-8");
        System.out.println(path);

        if(!file.isEmpty()){
            //检查文件格式
            //如果后缀为txt，执行txt文件操作
            if (fileSuffix.equals(".txt")) {
                txtFileUpLoad(file);
            }else if (fileSuffix.equals(".docx")){
                docxFileUpLoad(file);
            }else if (fileSuffix.equals(".doc")){
                docFileUpLoad(file);
            }else if (fileSuffix.equals(".png")||fileSuffix.equals(".jpg")||fileSuffix.equals(".bmp")||fileSuffix.equals(".tiff")||fileSuffix.equals(".gif")){
                picFileUpLoad(file,fileName,path);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
            return gson.toJson(lineList);

        }else {
            return "error";
        }

    }

    private void txtFileUpLoad(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine())!=null){
            lineList.add(line);
        }
        br.close();
        reader.close();
        inputStream.close();
    }

    private void docxFileUpLoad(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XWPFDocument xdoc = new XWPFDocument(inputStream);

        List<XWPFParagraph> paragraphs = xdoc.getParagraphs();
        for (XWPFParagraph paragraph:paragraphs){
            if(!paragraph.getText().equals("")) {
                lineList.add(paragraph.getText());
            }
        }

        inputStream.close();

    }

    private void docFileUpLoad(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        WordExtractor extractor = new WordExtractor(inputStream);

        String paraLine[] = extractor.getParagraphText();
        for(int i=0;i<paraLine.length;i++){
            if(paraLine[i]!=null){
                System.out.println(paraLine[i]);
                lineList.add(paraLine[i]);
            }
        }

//        lineList.addAll(Arrays.asList(paraLine));
        inputStream.close();
    }

    private void picFileUpLoad(MultipartFile file,String fileName,String path) throws IOException {

        File filePath = new File(path);

        if (!filePath.exists()){
            filePath.mkdirs();
        //将文件夹中内容删除，以防止空间占用
        }else {
            File[] children = filePath.listFiles();
            if (children != null && children.length != 0) {
                for (File child : children) {
                    child.delete();
                }
            }
        }
        //转存文件格式
        file.transferTo(new File(path+fileName));
        lineList.add(fileName);
    }


}

