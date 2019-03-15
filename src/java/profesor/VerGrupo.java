package profesor;

import inicio.BaseXML;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VerGrupo extends HttpServlet {
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
            String auxi[]=BD.ObtenerDatosUsuario(BD.BuscarUsuario(user));
            int grupo=BD.BuscarGrupo(auxi[5]);             //Se asigna el grupo al que corresponde el profesor
            int numalumnos=BD.NumeroAlumnosGrupo(grupo);   //Se recupera la cantidad de alumnos en el grupo
            String alumnos[] = new String[numalumnos];     //Para guardar a todos los alumnos 
            alumnos=BD.ObtenerAlumnoGrupo(grupo);
            for(int i=0;i<numalumnos;i++)
                System.out.println(alumnos[i]);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ver Grupo</title>");
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
            out.println("<div class='title'><h1>Grupo "+auxi[5]+"</h1></div>");
            out.println("<div class='form IngresaR'>");
            out.println("<form method='get'>");
            out.println("<ul>");
            for(int i=0;i<numalumnos;i++)  //Manda al Servlet que imprime los datos del usuario dado su user
            {  
                String datosAlumno[]= new String[6];
                int pos=BD.BuscarUsuario(BD.ObtenerUser(alumnos[i]));
                datosAlumno=BD.ObtenerDatosUsuario(pos);
                out.println("<li><a href=InfoU?userb="+BD.ObtenerUser(i)+">Boleta: "+datosAlumno[0]+" Nombre:"+alumnos[i]+"</a></li>");
            }
            out.println("</ul>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>"); 
    }

}
