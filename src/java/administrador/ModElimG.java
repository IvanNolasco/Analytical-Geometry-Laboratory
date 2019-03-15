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

public class ModElimG extends HttpServlet {
    String path;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            //Recuperamos nombre y usuario de la sesión para los datos de la cuenta activa 
            //Subimos a la sesión el grupo que se esta buscando
            HttpSession session=request.getSession();
            String grupob=request.getParameter("grupob");
            session.setAttribute("grupob", grupob); 
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
            int numg=BD.BuscarGrupo(grupob);         //Se busca la posición del grupo
            int numa=1,numad=1;
            String profes="";                         //Para guardar al profesor
            String valores="";
            if(numg!=-1)        //Encontro el grupo
            {
                numa=BD.NumeroAlumnosGrupo(numg);    //Para poder inicializar el arreglo de alumnos
                numad=BD.NumeroAdmins(numg);
                System.out.println(numg);
                System.out.println(numad);
                valores=BD.ObtenerNombreGrupo(numg);    //Se obtienen los datos del grupo
                if(BD.NumeroProfesoresGrupo(numg)!=0)
                    profes=BD.ObtenerProfesorGrupo(numg);
            } 
            String alum[];           //Para guardar a los alumnos
            String admin[];          //Para guardar a los administradores
            admin=BD.ObtenerAdmins(numg);
            alum=BD.ObtenerAlumnoGrupo(numg);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Modificar/Eliminar Grupo</title>");            
            out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Estilos/formularios.css' />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            out.println("<ul>");
            out.println("<li><a href='Admin'>Inicio Administrador</a></li>");
            out.println("<li id='activo'><a href='AgregaU'>Agregar Usuario</a></li>");
            out.println("<li><a href='BuscarU'>Administar Usuarios</a></li>");
            out.println("<li><a href='AgregaG'>Agregar Grupo</a></li>");
            out.println("<li><a href='BuscarG'>Administrar Grupos</a></li>");
            out.println("<li><a href='Usuarios'>Ver usuarios</a></li>");
            out.println("<li><a href='Grupos'>Ver grupos</a></li>");
            out.println("<li><a href='login.html'>Cerrar Sesión</a></li>");
            out.println("</ul></li>");
            out.println("</div>");
             if(numg==-1){
               out.println("<div class='bienvenida'>"); 
               out.println("<h1>Grupo inexistente</h1>");
               out.println("</div>");
           }    
            else
            {
            out.println("<div class='title'><h1>Administrar Grupo</h1></div>");
            out.println("<div class='form IngresaR'>");    
            out.println("<form method='get'>");
            out.println("<label for='user'>Nombre</label>");
            out.println("<input type='text' name='nombreg' required title='Campo Requerido' value='"+valores+"' size='10' disabled/>");
            out.println("<br/><br/>");
            for(int i=0;i<numad;i++)
            {
                out.println("<label for='admin"+i+"'>Administrador "+(i+1)+":</label> <input type='text' name='admin"+i+"' required title='Campo Requerido' value='"+admin[i]+"' size='15' disabled/>");
                out.println("<br/><br/>");
            }
            if(BD.NumeroProfesoresGrupo(numg)!=0){
            out.println("<label for='profesor'>Profesor:</label><input type='text' name='profe' required title='Campo Requerido' value='"+profes+"' size='15' disabled/>");
            out.println("<br/><br/>");}
            for(int i=0;i<numa;i++)
            {
                out.println("<label for='alumno"+i+"'>Alumno "+(i+1)+":</label> <input type='text' name='alumno"+i+"' required title='Campo Requerido' value='"+alum[i]+"' size='15' disabled/>");
                out.println("<br/><br/>");
            }
            out.println("<input type='submit' onclick = \"this.form.action = 'EliminarG'\" value='Eliminar Grupo Completo' />");
            out.println("</form>");
            }
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>"); 
    }
}