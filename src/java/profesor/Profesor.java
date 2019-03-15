package profesor;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Profesor extends HttpServlet {

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
            out.println("<title>Profesor</title>");
            out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Estilos/formularios.css' />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            out.println("<ul>");
            out.println("<li id=activo><a href='Profesor'>Inicio Profesor</a></li>");
            out.println("<li><a href='VerGrupo'>Ver Grupo</a></li>");
            out.println("<li><a href='VerE'>Ver Ejercicios</a></li>");
            out.println("<li><a href='AgregaE'>Agregar Ejercicio</a></li>");
            out.println("<li><a href='BuscarE'>Buscar/Modificar Ejercicio</a></li>");
            out.println("<li><a href='BuscarE'>Calificar Ejercicio</a></li>");
            out.println("<li><a href=''>Cuenta activa: "+user+".</a>");
            out.println("<ul>");
            out.println("<li><a href='login.html'>Cerrar Sesión</a></li>");
            out.println("</ul></li>");
            out.println("</div>");
            out.println("<div class='bienvenida'>");
            out.println("<h2>Bienvenido Profesor: </h2>");
            out.println("<h1>" + nombre + "</h1>");
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>");   
    }

}