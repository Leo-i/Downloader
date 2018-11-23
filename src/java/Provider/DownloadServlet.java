package Provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lev
 */
@WebServlet("/download.jsp")
public class DownloadServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            String fileName = request.getParameter("filename");
            File fileSaveDir = FileListProvider.getFilepath(getServletContext());

            File file = new File(fileSaveDir, fileName);
            if (!fileSaveDir.equals(file.getParentFile())) {
                throw new IllegalArgumentException("Access rights violation");
            }

            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

            try (InputStream input = new FileInputStream(file);
                    OutputStream output = response.getOutputStream()) {

                //Даем подсказку браузеру о размере файла,
                //но сначала запираем дескриптор
                response.setContentLength((int) file.length());

                byte[] buf = new byte[4096];
                int length;
                while ((length = input.read(buf)) >= 0) {
                    output.write(buf, 0, length);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            getServletContext().getRequestDispatcher("/WEB-INF/templates/error.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
