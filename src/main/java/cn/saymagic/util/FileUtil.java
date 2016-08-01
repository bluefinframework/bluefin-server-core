package cn.saymagic.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by saymagic on 16/5/21.
 */
public final class FileUtil {

    public static String getExtension(String uri) {
        if (uri == null) {
            return "";
        }
        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            return "";
        }
    }

    public static void writeFileToHttpServletResponse(File file, HttpServletResponse response) {
        try (InputStream inputStream = new FileInputStream(file)) {
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (IOException e) {
            response.setStatus(HttpStatus.SC_NOT_FOUND);
        }
    }

}
