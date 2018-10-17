/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaumemonzonis.authentication;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jaume monzonis
 */
public class json extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        HttpSession oSession = request.getSession();
        String strJson = "";
        String strOp = request.getParameter("op");
        Gson gSon = new Gson();
        try {
            if (strOp != null) {
                
                
                if (strOp.equalsIgnoreCase("login")) {
                    String strUser = request.getParameter("user");
                    String strPass = request.getParameter("pass");
                    if (strUser.equalsIgnoreCase("jaume") && strPass.equalsIgnoreCase("jam")) {
                        oSession.setAttribute("sessionvar", strUser);
                        strJson = "{\"status\":200,\"msg\":\"" + strUser + "\"}";
                    } else {
                        strJson = "{\"status\":401,\"msg\":\"Authentication error login.\"}";
                    }

                }
                if (strOp.equalsIgnoreCase("logout")) {
                    oSession.invalidate();
                    strJson = "{\"status\":200,\"msg\":\"Session is closed\"}";
                }

                if (strOp.equalsIgnoreCase("getsecret")) {
                    String strUserName = (String) oSession.getAttribute("sessionvar");
                    if (strUserName != null) {
                        strJson = "{\"status\":200,\"msg\":\"0214\"}";
                    } else {
                        strJson = "{\"status\":401,\"msg\":\"Authentication error getsecret.\"}";
                    }
                }
                if (strOp.equalsIgnoreCase("check")) {
                    String strUserName = (String) oSession.getAttribute("sessionvar");
                    if (strUserName != null) {
                        strJson = "{\"status\":200,\"msg\":\"" + strUserName + "\"}";
                    } else {
                        strJson = "{\"status\":401,\"msg\":\"Authentication error check.\"}";
                    }
                }

            } else {
                strJson = "{\"status\":200,\"msg\":\"OK\"}";
            }

//        response.getWriter().append(strJson).close();
            response.getWriter().append(gSon.toJson(strJson));

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
