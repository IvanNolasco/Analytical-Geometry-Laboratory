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

public class InfoU extends HttpServlet {
    String path;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            //Recuperamos nombre y usuario de la sesión para los datos de la cuenta activa 
            //Subimos a la sesión el usuario que se esta buscando
            HttpSession session=request.getSession();
            String userb=request.getParameter("userb");
            session.setAttribute("userb", userb); 
            String nombre=(String)session.getAttribute("nombre");  
            String user=(String)session.getAttribute("user");
            
            response.setContentType("text/html;charset=UTF-8");     
            PrintWriter out = response.getWriter();                 
            
            //Para obtener la ruta relativa 
            ServletContext context = request.getServletContext();          
            path = context.getRealPath("/");
            path=path.replace("\\build", "");
            System.out.println(path);
            
            BaseXML BD = new BaseXML(path);
            int numuser=BD.BuscarUsuario(userb);    //Se busca la posición del usuario
            String valores[] = new String[6];       //Para guardar los datos del usuario y poder desplegarlos en las cajas de texto
            String datos[]={"userb","name","password","tipo","correo","grupo"}; //Los datos que se van a obtener del formulario
            if(numuser!=-1)
            {
                valores=BD.ObtenerDatosUsuario(numuser);//Se obtienen los datos del usuario si este existe
                for(int i=0;i<6;i++)
                    session.setAttribute(datos[i], valores[i]); //Se suben los datos a sesión
            }          
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Información del usuario</title>");
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
           if(numuser==-1){
               out.println("<div class='bienvenida'>"); 
               out.println("<h1>Usuario inexistente</h1>");
               out.println("</div>");
           }     //Se despliega un mensaje si el usuario no existe
           else                                                 //De lo contrario, aparecen sus datos
           {
            out.println("<div class='title'><h1>Agregar Usuario</h1></div>");
            out.println("<div class='form IngresaR'>");
            out.println("<form method='get'>");
            out.println("<label for='user'>Usuario</label><br />");
            out.println("<input type='text' name='user' required title='Campo Requerido' value='"+valores[0]+"' size='10' disabled/>");
            out.println("<label for='name'>Nombre</label><br />");
            out.println("<input type='text' name='name' required title='Campo Requerido' value='"+valores[1]+"' size='30' disabled/>");
            out.println("<label for='password'>Contraseña</label><br />");
            out.println("<input type='text' name='password' required title='Campo Requerido' value='"+valores[2]+"' size='10' disabled/>");
            out.println("<label for='correo'>Correo</label><br />");
            out.println("<input type='text' name='correo' required title='Campo Requerido' value='"+valores[4]+"' size='23' disabled/>");
            out.println("</form>");
           }
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>"); 
    }

}
