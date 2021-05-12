package edu.upc.svmweb.controller;

import edu.upc.svmweb.util.FileOperationUtil;
import edu.upc.svmweb.util.HttpClearHtmlUtil;
import edu.upc.svmweb.util.HttpGetHtmlUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/getSource")
public class GetSourceController {
    private static final String SOURCE_CODE_PATH = "data/项目文本/SourceCode.txt";
    private static final String CLEAR_DATA_PATH = "data/项目文本/ClearData.txt";

    @ResponseBody
    @RequestMapping(value = "/getSourceCode", method = RequestMethod.POST)
    public String getSourceCode(@RequestParam(value = "url") String url) {
        HttpGetHtmlUtil hgh = new HttpGetHtmlUtil();
        String html = hgh.getHtml(url);
        FileOperationUtil.writeFile(SOURCE_CODE_PATH, html);
        return html;
    }

    @ResponseBody
    @RequestMapping(value = "/getClearData", method = RequestMethod.POST)
    public String getClearData(@RequestParam(value = "url") String url) {
        HttpClearHtmlUtil hch = new HttpClearHtmlUtil();
        HttpGetHtmlUtil hgh = new HttpGetHtmlUtil();
        String html = hch.replaceHtml(hgh.getHtml(url));
        char[] c = html.toCharArray();

        int len = c.length;
        char[] tmp = new char[len];
        int k = 0;
        /*一行显示所有中文字符
        for (char c1 : c) {
            if ((c1 >= 0x4E00 && c1 <= 0x9FA5))
                System.out.print(c1);
        }*/
        //根据换行符，汉字，符号分行表示的文本解析之后的字符串
        for (int i = 0; i < len - 1; i++) {
            if (((c[i] >= 0x4E00 && c[i] <= 0x9FA5)) && ((c[i + 1] >= 0x4E00 && c[i + 1] <= 0x9FA5))) {
                tmp[k++] = c[i];
            } else if ((c[i] >= 0x4E00 && c[i] <= 0x9FA5)) {
                tmp[k++] = c[i];
                tmp[k++] = 0x000A;
            }
        }
        if (c[len - 1] >= 0x4E00 && c[len - 1] <= 0x9FA5) {
            tmp[k] = c[len - 1];
        }
        //网页正文无中文字符时返回
        if (k == 0) {
            return "当前解析网页正文无中文字符";
        }
        String clearData = String.valueOf(tmp).substring(0, k - 1);

        FileOperationUtil.writeFile(CLEAR_DATA_PATH, clearData);
        return clearData;
    }

}
