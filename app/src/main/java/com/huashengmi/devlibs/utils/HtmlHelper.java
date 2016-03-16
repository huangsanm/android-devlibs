package com.huashengmi.devlibs.utils;

/**
 * User: huangsanm@foxmail.com
 * Date: 2016-03-16
 * Time: 15:50
 */
public class HtmlHelper {


    /**
     *
     * @param bodyHTML
     * @return
     */
    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%;width:auto; height:auto;margin:5px auto;} body {font-size:16px; color:#656565;line-height:26px;text-align:justify;text-justify:inter-ideograph;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

}
