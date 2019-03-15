package administrador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuscarU extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            //Recuperamos nombre y usuario de la sesión para los datos de la cuenta activa 
            HttpSession session=request.getSession();
            String nombre=(String)session.getAttribute("nombre");  
            String user=(String)session.getAttribute("user");
            
            response.setContentType("text/html;charset=UTF-8");     
            PrintWriter out = response.getWriter();                 
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Buscar usuario</title>");
            out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Estilos/formularios.css' />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            out.println("<ul>");
            out.println("<li><a href='Admin'>Inicio Administrador</a></li>");
            out.println("<li><a href='AgregaU'>Agregar Usuario</a></li>");
            out.println("<li id='activo'><a href='BuscarU'>Administrar Usuarios</a></li>");
            out.println("<li><a href='AgregaG'>Agregar Grupo</a></li>");
            out.println("<li><a href='BuscarG'>Administrar Grupos</a></li>");
            out.println("<li><a href='Usuarios'>Ver usuarios</a></li>");
            out.println("<li><a href='Grupos'>Ver grupos</a></li>");
            out.println("<li><a href=''>Cuenta activa: "+user+".</a>");
            out.println("<ul>");
            out.println("<li><a href='login.html'>Cerrar Sesión</a></li>");
            out.println("</ul></li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("<div class='title'><h1>Administrar Usuario</h1></div>");
            out.println("<div class='form Admin'>");
            out.println("<form method='get' action='ModElimU'>");
            out.println("<label for='userb'>Usuario a buscar</label><br />");
            out.println("<input type='search' name='userb'/>");
            out.println("<input type='submit' value='Buscar'>");
            out.println("</form>");
            out.println("</div >");
            out.println("</body>"); 
            out.println("</html>");   
    }
}