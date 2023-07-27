import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            int customerId = Integer.parseInt(request.getParameter("id"));
            try {
                Customer customer = CustomerDAO.getCustomerById(customerId);

                request.setAttribute("customer", customer);

                request.getRequestDispatcher("customer-details.jsp").forward(request, response);
            } catch (SQLException e) {
                // Handle any database-related errors
                e.printStackTrace();
                response.getWriter().println("An error occurred while fetching customer data.");
            }
        } else {
            response.getWriter().println("Invalid action specified.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("POST requests are not supported in this example.");
    }
}