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

public class IngresaG extends HttpServlet {
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
            
            String group = request.getParameter("group");  //Recuperación de parámetros del formulario
            group=group.toUpperCase();
            BaseXML BD = new BaseXML(path);
            int numeroGrupos=BD.NumeroGrupos();             //Obenemos el numero de grupos
            String Grupos[]=new String[numeroGrupos];       //Creamos el areglo del tamaño de los grupos
            for(int i=0;i<numeroGrupos;i++)
                Grupos[i]=BD.ObtenerNombreGrupo(i);         //Obtenemos los nombres de los grupos
            //VERICAR SI EXISTE EL USUARIO
            boolean encontrado = false;                     //Para verificar la existencia del grupo
            for (int i = 0; i < numeroGrupos; i++) 
                if (group.equals(Grupos[i]))                //Si el dato ingresado coincide con un grupo existente
                {
                    encontrado = true;
                    break;
                }
                String mensaje;
                if (encontrado)                             //El grupo existe
                {
                    mensaje = "Grupo Existente, debe registrar un grupo distinto";
                } 
                else 
                {
                    mensaje= BD.AgregarGrupo(group);          //Si el grupo no existe, se agrega al xml                   
                }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Agregar Grupo</title>");            
            out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Estilos/formularios.css' />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            out.println("<ul>");
            out.println("<li id=activo><a href='Admin'>Inicio Administrador</a></li>");
            out.println("<li><a href='AgregaU'>Agregar Usuario</a></li>");
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
            out.println("<h1>" + mensaje + "</h1>");   //Imprime si hubo éxito o no en la acción de agregar el grupo
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>");
    }
}