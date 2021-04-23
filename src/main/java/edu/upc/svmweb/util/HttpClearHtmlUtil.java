package edu.upc.svmweb.util;

import java.util.regex.Pattern;

public class HttpClearHtmlUtil {
    public String replaceHtml(String html) {
        String str_pass = html; // 含选定标签的字符串
        String str_text = "";
        java.util.regex.Pattern p_script;

        java.util.regex.Matcher m_script;

        java.util.regex.Pattern p_style;

        java.util.regex.Matcher m_style;

        java.util.regex.Pattern p_html;

        java.util.regex.Matcher m_html;
        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

            // 定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";

            // 过滤script标签
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(str_pass);
            str_pass = m_script.replaceAll("");

            // 过滤style标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(str_pass);
            str_pass = m_style.replaceAll("");

            // 过滤html标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(str_pass);
            str_pass = m_html.replaceAll("");

            //将过滤后的字符串赋值给str_text
            str_text = str_pass;

        } catch (Exception e) {
            System.err.println("FiltratePage: " + e.getMessage());

        }

        //剔除空格行
        str_text = str_text.replaceAll("[ ]+", " ");

        //剔除换行
        str_text = str_text.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        return str_text;// 返回文本字符串
    }
}
