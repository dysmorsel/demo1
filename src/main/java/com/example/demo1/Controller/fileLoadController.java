package com.example.demo1.Controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class fileLoadController {

    private Gson gson = new Gson();
    private List<String> lineList = new ArrayList<>();

    @RequestMapping(value = "/fileLoad",method = RequestMethod.POST)
    public @ResponseBody String fileLoad(@RequestParam(value = "file") MultipartFile file) throws IOException {
        long startTime = System.currentTimeMillis();
        lineList.clear();

        if(!file.isEmpty()){
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
            long endTime = System.currentTimeMillis();
            System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");

            return gson.toJson(lineList);
        }else {
            return "error";
        }
    }


}

