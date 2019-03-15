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

public class AgregaU extends HttpServlet {
    String path="";
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
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Agregar usuario</title>");
            out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Estilos/formularios.css' />");
            out.println("");
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
            out.println("<div class='title'><h1>Agregar Usuario</h1></div>");
            out.println("<div class='form Ingresa'>");
            out.println("<form method='get' action='IngresaU'>");
            out.println("<label for='user'>Usuario</label><br />");
            out.println("<input type='text' name='user' required title='Campo Requerido' placeholder='Ej. Boleta/RFC' size='10'/>");
            out.println("<br/>");
            out.println("<label for='name'>Nombre</label><br />");
            out.println("<input type='text' name='name' required title='Campo Requerido' placeholder='Nombre Completo' size='30'/>");
            out.println("<br/>");
            out.println("<label for='password'>Password</label><br />");
            out.println("<input type='text' name='password' required title='Campo Requerido' placeholder='******' size='10'/>");
            out.println("<br/>");
            out.println("<label for='tipo'>Tipo de Usuario</label><br />");
            out.println("<select name='tipo' required>");
            out.println("<option value='Administrador'>Administrador</option>");
            out.println("<option value='Profesor'>Profesor</option>");
            out.println("<option value='Alumno'>Alumno</option>");
            out.println("</select>");
            out.println("<label for='correo'>Email</label><br />");
            out.println("<input type='text' name='correo' required title='Campo Requerido' placeholder='Ej. ejemplo@correo.com' size='23'/>");
            out.println("<br/>");
            out.println("<label for='grupo'>Grupo</label><br />");
            out.println("<select name='grupo' required>");
            BaseXML BD = new BaseXML(path); //instancia de la clase java
            int numeroGrupo=BD.NumeroGrupos(); //obtenemos los numeros de grupos
            String num;
            for(int i=0;i<numeroGrupo;i++)
            {
                num=BD.ObtenerNombreGrupo(i);//Obtenemos el nombre del grupo a partir del índice
                out.println("<option value="+num+">"+num+"</option>");//Generamos el combobox de grupos existentes
            }
            out.println(" </select>");
            out.println("<input type='submit' value='Registrar Usuario'/>");
            out.println("</form>");
            out.println("</div >");
            out.println("</body>"); 
            out.println("</html>");     
    }
}