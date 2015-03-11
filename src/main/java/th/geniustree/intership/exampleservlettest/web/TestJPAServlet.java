/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.geniustree.intership.exampleservlettest.web;

import th.geniustree.intership.exampleservlettest.model.Customer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author M6500
 */
@WebServlet(urlPatterns = "/myjpa")
public class TestJPAServlet extends HttpServlet {

    @PersistenceContext(unitName = "customer_pu")
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("name");
        Query query = em.createQuery("select c from Customer c where c.name = :name ");
        query = query.setParameter("name", parameter);
        List<Customer> resultList = query.getResultList();
        req.setAttribute("customers", resultList);
        req.getRequestDispatcher("test-jsp.jspx").forward(req, resp);
    }

}
