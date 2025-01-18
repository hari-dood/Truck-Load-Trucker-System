package com.hariSolution.fileUploder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

// @WebServlet annotation configures this class as a Servlet. It is mapped to a specific URL pattern.
// When a request is made to /ServletModel, this servlet will handle the request.
@WebServlet(name = "ServletModel", value = "/ServletModel")
public class FileModel extends HttpServlet {
    // The serialVersionUID is used for serialization of the class. It is used to verify that the sender and receiver
    // of a serialized object have loaded the same class.
    @Serial
    private static final long serialVersionUID = 1L;

    // This method is called when a GET request is made to the /ServletModel URL. It handles the HTTP GET request.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // You can add logic here to handle GET requests.
        // For example, you can return a file or information to the client.
    }

    // This method is called when a POST request is made to the /ServletModel URL. It handles the HTTP POST request.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // This is where you would handle file upload logic.
        // You could get the file data from the request and process or store it.
    }
}
