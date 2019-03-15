package inicio;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jdom.input.SAXBuilder;

public class Inicio extends HttpServlet {
    
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
     response.setContentType("text/html;charset=UTF-8");
        //RECUPERAMOS PARAMETROS
        String user=request.getParameter("user");
        String password=request.getParameter("password");
        
        //Si hay texto en los formularios
         if (user.length() > 0 || password.length() > 0) 
         {
             //SUBIMOS A SESION LOS DATOS
            HttpSession sesion = request.getSession();
            sesion.setAttribute("password", password); 
            sesion.setAttribute("user", user);
            
            String tipo="";
            String path="";
            String nombre="";
            ServletContext context = request.getServletContext();           //para obtener la ruta relativa 
            path = context.getRealPath("/");
             System.out.println("path: "+path);
            path=path.replace("\\build", "");
             System.out.println("path: "+path);
            BaseXML BD = new BaseXML(path); //consultamos el xml
            tipo = BD.VerificarUsuario(user, password); //tipo de usuario
            nombre = BD.ObtenerNombre(user, password); // nombre
            sesion.setAttribute("nombre", nombre); //subimos el nombre
            System.out.println("nombre="+nombre);
                        
            if (tipo.equals("Alumno")) {
                response.sendRedirect("Alumno"); //Vamos a Alumno
            }

            if (tipo.equals("Administrador")) {
                response.sendRedirect("Admin"); //Vamos a Administrador
            }

            if (tipo.equals("Profesor")) {
                response.sendRedirect("Profesor"); //Vamos a Administrador
            }

            if (tipo.equals("Error al iniciar sesión ")) {
                response.sendRedirect("Error"); //Vamos a Error
            }
             if (tipo.equals("Contraseña incorrecta ")) {
                response.sendRedirect("Contra"); //Vamos a Contra
            }
        } else {
                response.sendRedirect("login.html"); //Regresamos a Login
        }
    }   
}

