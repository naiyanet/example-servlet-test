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
@WebServlet(urlPatterns = "/my")
public class TestServlet extends HttpServlet {

    @Resource(lookup = "jdbc/javastone")
    private DataSource datasorce;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;
         List<Customer> customerList = new ArrayList<>();
        try {
            connection = datasorce.getConnection();
            Statement createStatement = connection.createStatement();
            ResultSet rs = createStatement.executeQuery("select * from customer");  
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setSex(rs.getString("sex"));
                customer.setBod(rs.getDate("bod"));
                customer.setCity(rs.getString("city"));
                customerList.add(customer);
            }
        } catch (Exception ex) {
            Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        req.setAttribute("customers", customerList);
        System.out.println(customerList.size());
        req.getRequestDispatcher("test-jsp.jspx").forward(req, resp);
    }

}
