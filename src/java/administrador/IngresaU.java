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

public class IngresaU extends HttpServlet {
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
            
            int ban=0;                                                            //Para detectar si se ingreso el grupo admin a otro tipo de usuario
            String valores[] = new String[6];                                    //Para almacenar cada uno de los valores 
            String datos[]={"user","name","password","tipo","correo","grupo"};  //Datos que se van a obtener del formulario
            for(int i=0;i<valores.length;i++)
                valores[i] = request.getParameter(datos[i]);                    //Recuperación de parámetros del formulario
            if((!"Administrador".equals(valores[3]) && "administradores".equals(valores[5])) || ("Administrador".equals(valores[3]) && !"administradores".equals(valores[5])))
            {
    
                    ban=1;
            }
            BaseXML BD = new BaseXML(path);
            int numeroUsuarios=BD.NumeroUsuarios();         //Obenemos el numero de usuarios
            String Usuarios[]=new String[numeroUsuarios];   //Creamos el areglo del tamaño de los usuarios
            for(int i=0;i<numeroUsuarios;i++)
                Usuarios[i]=BD.ObtenerUser(i);              //Obtenemos los nombres de los usuarios
            //VERICAR SI EXISTE EL USUARIO
            boolean encontrado = false;                     //Para verificar la existencia del usuario
            for (int i = 0; i < numeroUsuarios; i++) 
                if (valores[0].equals(Usuarios[i]))         //Si el dato ingresado coincide con un usuario existente
                {
                    encontrado = true;
                    break;
                }
                String mensaje;
                if (encontrado) 
                {
                    mensaje = "Usuario Existente, debe registrar un Usuario distinto";
                } 
                else 
                {
                    //Si el usuario no existe, se agrega al grupo
                    if(ban==1)
                        mensaje="No puedes agregar un usuario "+valores[3]+" al grupo "+valores[5];
                    else
                    {
                        BD.AgregaUsuarioGrupo(valores[1], valores[5], valores[3]);      //Agregar el usuario al grupo que corresponde
                        mensaje = BD.AgregarUsuario(valores);        
                    }
                }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Agregar Usuario</title>");            
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
            out.println("<h1>" + mensaje + "</h1>");   //Imprime si hubo éxito o no en la acción de agregar al usuario
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>");
        
    }

}