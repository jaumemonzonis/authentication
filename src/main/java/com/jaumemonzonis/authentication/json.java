/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaumemonzonis.authentication;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import static java.lang.System.out;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;

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
        String strOp = request.getParameter("op");
        String strUser = request.getParameter("user");
        String strPass = request.getParameter("pass");
        String strJson = "";

        try {
            if (strOp != null) {

                if (strOp.equalsIgnoreCase("connect")) {

                    ComboPooledDataSource cpds = new ComboPooledDataSource();
                    cpds = new ComboPooledDataSource();
                    Connection connection = null;

                    try {
                        cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
                    } catch (Exception ex) {
                        response.setStatus(500);
                        strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
                        out.print(strJson);
                    }

                    cpds.setJdbcUrl("jdbc:mysql://localhost/trolleyes");
                    cpds.setUser("root2");
                    cpds.setPassword("bitnami");

                    // the settings below are optional -- c3p0 can work with defaults
                    cpds.setMinPoolSize(5);
                    cpds.setAcquireIncrement(5);
                    cpds.setMaxPoolSize(20);
                    cpds.setMaxStatements(180);

                    try {
                        ComboPooledDataSource dataSource = cpds;
                        connection = dataSource.getConnection();
                        strJson = "{\"status\":200,\"msg\":\"c3p0 Connection OK\"}";
                        out.print(strJson);

                    } catch (Exception ex) {
                        strJson = "{\"status\":500,\"msg\":\"Bad c3p0 Connection\"}";
                        out.print(strJson);
                    }
                }
                
                if (strOp.equalsIgnoreCase("login")) {

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
            
            response.getWriter().append(strJson).close();
                //response.getWriter().append(gSon.toJson(strJson));
            
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
