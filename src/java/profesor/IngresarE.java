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

public class IngresarE extends HttpServlet {
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
            
            int tam=4;                                                            //Tamaño del arreglo de datos de la grafica, se declara en 4 porque es el valor más usado
            String valores[] = new String[8];                                    //Para almacenar cada uno de los valores 
            String datos[]={"id","grupo","asignacion","entrega","user","tipo","desc","ecuacion"};  //Datos que se van a obtener del formulario
            for(int i=0;i<valores.length;i++)
                valores[i] = request.getParameter(datos[i]);                    //Recuperación de parámetros del formulario
            if(valores[5].equals("Circunferencia"))        //Se inicializa la variable tam de acuerdo al número de parámetros para el tipo de gráfico
                tam=3;
            if(valores[5].equals("Hiperbole"))
                tam=5;
            valores[4]=user;      //Se asugna el RFC del profesor;
            String aux[]=new String[tam];   //Para guardar los datos recueprados de la grafica
            switch(valores[5])
            {
                case "Linea":
                  String grafica1[]={"x1","y1","x2","y2"}; 
                  for(int i=0;i<tam;i++)
                      aux[i]=request.getParameter(grafica1[i]);
                  break;
                 case "Circunferencia":
                  String grafica2[]={"x","y","radio"}; 
                  for(int i=0;i<tam;i++)
                      aux[i]=request.getParameter(grafica2[i]);
                  break;
                  case "Elipse":
                  String grafica3[]={"eje_1","eje_2","x","y"}; 
                  for(int i=0;i<tam;i++)
                      aux[i]=request.getParameter(grafica3[i]);
                  break;
                  case "Hiperbole":
                  String grafica4[]={"eje_1","eje_2","px","py","eje_focal"}; 
                  for(int i=0;i<tam;i++)
                      aux[i]=request.getParameter(grafica4[i]);
                  break;
                  
            }
            BaseXML BD = new BaseXML(path);
            int numeroEjercicios=BD.NumeroEjercicios(user);         //Obenemos el numero de ejercicos
            String Ejercicios[]=new String[numeroEjercicios];   //Creamos el arreglo del tamaño de los ejercicios
            Ejercicios=BD.EjerciciosProfe(user);
            for(int i=0;i<numeroEjercicios;i++)
                System.out.println(Ejercicios[i]);
           String auxi[]=BD.ObtenerDatosUsuario(BD.BuscarUsuario(user));
            valores[1]=auxi[5];         //Se asigna el grupo al que corresponde el profesor
            //VERICAR SI EXISTE EL USUARIO
            boolean encontrado = false;                     //Para verificar la existencia del ejercicio
            for (int i = 0; i < numeroEjercicios; i++) 
                if (valores[0].equals(Ejercicios[i]))         //Si el dato ingresado coincide con un ejercicio existente
                {
                    encontrado = true;
                    break;
                }
                String mensaje;
                if (encontrado) 
                {
                    mensaje = "Ejercicio Existente, debe registrar un Ejercicio distinto";
                } 
                else 
                {
                    //Si el ejercicio no existe, se agrega 
                    mensaje=BD.AsignarEjercicio(valores[0], valores[1], valores[2], valores[3], valores[4], valores[5], valores[6], valores[7],aux);
                }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ingresar Ejercicio</title>");            
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
            out.println("<div class='bienvenida'>");
            out.println("<h1>" + mensaje + "</h1>");   //Imprime si hubo éxito o no en la acción de agregar al usuario
            out.println("</div>");
            out.println("</body>"); 
            out.println("</html>");
        
    }

}