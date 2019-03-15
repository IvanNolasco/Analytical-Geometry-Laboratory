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

public class ModificarU extends HttpServlet {
    String path;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            //Recuperamos nombre y usuario de la sesión para los datos de la cuenta activa y el nombre de usuario buscado 
            HttpSession session=request.getSession();
            String nombre=(String)session.getAttribute("nombre");  
            String user=(String)session.getAttribute("user");
            String userb=(String)session.getAttribute("userb");
            String name=(String)session.getAttribute("name");
            String password=(String)session.getAttribute("password");
            String tipo=(String)session.getAttribute("tipo");
            String correo=(String)session.getAttribute("correo");
            String grupo=(String)session.getAttribute("grupo");
            
            response.setContentType("text/html;charset=UTF-8");     
            PrintWriter out = response.getWriter();                 
            
           //Para obtener la ruta relativa 
            ServletContext context = request.getServletContext();          
            path = context.getRealPath("/");
            path=path.replace("\\build", "");
            System.out.println(path);
            //elimina al usuario
            BaseXML BD = new BaseXML(path);
            int pos=BD.BuscarUsuario(userb);        //Busca la posición del usuario a partir de su boleta/RFC
            String mensaje=BD.EliminarUsuario(pos); //Elimina al usuario de Usuarios.xml y de Grupos.xml
            
           out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Modificar/Eliminar usuario</title>");
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
            out.println("<div class='title'><h1>Agregar Usuario</h1></div>");
            out.println("<div class='form IngresaR'>");
            out.println("<form method='get'>");
            out.println("<label for='user'>Usuario</label><br />");
            out.println("<input type='text' name='user' required title='Campo Requerido' value='"+userb+"' size='10' disabled/>");
            out.println("<label for='name'>Nombre</label><br />");
            out.println("<input type='text' name='name' required title='Campo Requerido' value='"+name+"' size='30' />");
            out.println("<label for='password'>Contraseña</label><br />");
            out.println("<input type='text' name='password' required title='Campo Requerido' value='"+password+"' size='10'/>");
            out.println("<label for='tipo'>Tipo</label><br />");
            out.println("<select name='tipo' required>");
            out.println("<option value='Administrador'");
            //Compara el tipo del usuario con los tres tipos distintos para marcar el seleccionado en el combobox
            if(tipo.equals("Administrador"))
                out.println("selected>Administrador</option>");
            else
                out.println(">Administrador</option>");
            out.println("<option value='Profesor'");
            if(tipo.equals("Profesor"))
                out.println("selected>Profesor</option>");
            else
                out.println(">Profesor</option>");
            out.println("<option value='Alumno'");
            if(tipo.equals("Alumno"))
                out.println("selected>Alumno</option>");
            else
                out.println(">Alumno</option>");
            out.println("</select>");
            out.println("<label for='correo'>Correo</label><br />");
            out.println("<input type='text' name='correo' required title='Campo Requerido' value='"+correo+"' size='23'/>");
            out.println("<label for='grupo'>Grupo</label><br />");
            out.println("<select name='grupo' required>");
            int numeroGrupo=BD.NumeroGrupos();                           //Obtenemos los numeros de grupos
            String grupoe;
            for(int i=0;i<numeroGrupo;i++)
            {
                grupoe=BD.ObtenerNombreGrupo(i);                         //Se obtiene el grupo del usuario buscado
                out.println("<option value="+grupoe);
                    if(grupo.equals(grupoe))                        //Busca el grupo del usuario
                        out.println("selected>"+grupoe+"</option>");     //Para marcarlo como la opcion seleccionada en el combobox
                    else
                        out.println(">"+grupoe+"</option>");
            }
            if(grupo.equals("Sin asignar"))
            {
                out.println("<option value="+grupo+"selected>"+grupo+"</option>");
            }
                
            out.println(" </select>");
            out.println("<input type='submit' onclick = \"this.form.action = 'ModificarAgregar'\" value='Modificar Usuario'/>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>");
    }
}