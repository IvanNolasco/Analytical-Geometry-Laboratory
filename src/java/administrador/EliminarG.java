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

public class EliminarG extends HttpServlet {
    String path;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            //Recuperamos nombre y usuario de la sesión para los datos de la cuenta activa y el usuario buscado
            HttpSession session=request.getSession();
            String nombre=(String)session.getAttribute("nombre");  
            String user=(String)session.getAttribute("user");
            String grupob=(String)session.getAttribute("grupob");
            
            response.setContentType("text/html;charset=UTF-8");     
            PrintWriter out = response.getWriter();                 
            
            //Para obtener la ruta relativa 
            ServletContext context = request.getServletContext();          
            path = context.getRealPath("/");
            path=path.replace("\\build", "");
            System.out.println(path);
            
            BaseXML BD = new BaseXML(path);
            int numg=BD.BuscarGrupo(grupob);        //Busca la posición del grupo a partir del nombre
            int nump=10,numa=10;
            String profes = "";         //Para guardar los datos del grupo y poder eliminarlos
            String alum[] = new String[numa];
            String valores="";
            if(numg!=-1)
            {
                nump=BD.NumeroProfesoresGrupo(numg); //Para poder inicializar el arreglo de profes
                numa=BD.NumeroAlumnosGrupo(numg);    //Para poder inicializar el arreglo de alumnos
                valores=BD.ObtenerNombreGrupo(numg);    //Se obtienen los datos del grupo
                profes=BD.ObtenerProfesorGrupo(numg);
                alum=BD.ObtenerAlumnoGrupo(numg);
            }
//            for(int i=0;i<nump;i++)
//            {
//                BD.EliminarUsuario(BD.ObtenerUser(profes[i]));
//            }
//            for(int i=0;i<numa;i++)
//            {
//                BD.EliminarUsuario(BD.ObtenerUser(alum[i]));
//            }
            String mensaje=BD.EliminarGrupo(numg); //Elimina al grupo de Grupos.xml 
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Eliminar Grupo</title>");
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
            out.println("<h1>" + mensaje + ": " + grupob + "</h1>");      //Imprime si se elimino o no el grupo
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>"); 
    }
}
