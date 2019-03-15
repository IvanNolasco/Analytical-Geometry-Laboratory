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

public class Usuarios extends HttpServlet {
    String path;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            //Recuperamos nombre y usuario de la sesión para los datos de la cuenta activa 
            HttpSession session=request.getSession();
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
            int numusuarios=BD.NumeroUsuarios();
            String usuarios[] = new String[numusuarios];    //Para guardar a todos los usuarios existentes
            String auxi[]= new String[6];
            for(int i=0;i<numusuarios;i++)  
            {
                usuarios[i]=BD.ObtenerUser(i);  
                auxi=BD.ObtenerDatosUsuario(i);
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ver usuarios</title>");
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
            out.println("<div class='title'><h1>Ver usuarios</h1></div>");
            out.println("<div class='form IngresaR'>");
            out.println("<form method='get'>");
            out.println("<ul>");
            for(int i=0;i<numusuarios;i++)  //Mand al Servlet que imprime los datos del usuario dado su user
            {
                String datosUsuario[]= new String[6];
                int pos=BD.BuscarUsuario(usuarios[i]);
                datosUsuario=BD.ObtenerDatosUsuario(pos);
                
                out.println("<li><a href=ModElimU?userb="+BD.ObtenerUser(i)+">Usuario: "+usuarios[i]+" Nombre:"+datosUsuario[1]+" Tipo: "+datosUsuario[3]+"</a></li>");
            }
            out.println("</ul>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>"); 
            
    }

}