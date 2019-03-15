package administrador;

import inicio.BaseXML;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EliminarU extends HttpServlet {
    String path;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            //Recuperamos nombre y usuario de la sesión para los datos de la cuenta activa y el usuario buscado
            HttpSession session=request.getSession();
            String nombre=(String)session.getAttribute("nombre");  
            String user=(String)session.getAttribute("user");
            String userb=(String)session.getAttribute("userb");
            
            response.setContentType("text/html;charset=UTF-8");     
            PrintWriter out = response.getWriter();                 
            
            //Para obtener la ruta relativa 
            ServletContext context = request.getServletContext();          
            path = context.getRealPath("/");
            path=path.replace("\\build", "");
            System.out.println(path);
            
            BaseXML BD = new BaseXML(path);
            int pos=BD.BuscarUsuario(userb);        //Busca la posición del usuario a partir de su boleta/RFC
            String mensaje=BD.EliminarUsuario(pos); //Elimina al usuario de Usuarios.xml y de Grupos.xml
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Eliminar Usuario</title>");
            out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Estilos/formularios.css' />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            out.println("<ul>");
            out.println("<li><a href='Admin'>Inicio Administrador</a></li>");
            out.println("<li id='activo'><a href='AgregaU'>Agregar Usuario</a></li>");
            out.println("<li><a href='BuscarU'>Administrar Usuarios</a></li>");
            out.println("<li><a href='AgregaG'>Agregar Grupo</a></li>");
            out.println("<li><a href='BuscarG'>Administrar Grupos</a></li>");
            out.println("<li><a href='Usuarios'>Ver usuarios</a></li>");
            out.println("<li><a href='Grupos'>Ver grupos</a></li>");
            out.println("<li><a href=''>Cuenta activa: "+user+".</a>");
            out.println("<ul>");
            out.println("<li><a href='login.html'>Cerrar Sesión</a></li>");
            out.println("</ul></li>");
            out.println("</div>");
            out.println("<div class='bienvenida'>");
            out.println("<h1>" + mensaje + ": " + userb + "</h1>");      //Imprime si se elimino o no el usuario
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>"); 
    }
}