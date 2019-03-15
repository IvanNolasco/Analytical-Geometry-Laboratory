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

public class AgregaE extends HttpServlet {
    String path="";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //Recuperamos nombre y usuario de la sesión para los datos de la cuenta activa  
            HttpSession session=request.getSession();
            String nombre=(String)session.getAttribute("nombre");  
            String user=(String)session.getAttribute("user");
            
            //Para obtener la ruta relativa
            ServletContext context = request.getServletContext();           
            path = context.getRealPath("/");
            path=path.replace("\\build", "");
            System.out.println(path);
            
            BaseXML BD = new BaseXML(path);
            String auxi[]=BD.ObtenerDatosUsuario(BD.BuscarUsuario(user));
            session.setAttribute("grupo", auxi[5]);
            
            response.setContentType("text/html;charset=UTF-8");     
            PrintWriter out = response.getWriter();                 
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Agregar ejercicio</title>");            
            out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Estilos/formularios.css' />");
            out.println("<script src=\"http://cdnjs.cloudflare.com/ajax/libs/mathjs/3.17.0/math.min.js\"></script>\n" +
                        "  <!-- load http://maurizzzio.github.io/function-plot/ -->\n" +
                        "  <script src=\"http://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js\"></script>\n" +
                        "  <script src=\"https://wzrd.in/standalone/function-plot@1.18.1\"></script>\n" +
                        "  <script src=\"bower_components/d3/d3.min.js\"></script>\n" +
                        "  <script src=\"bower_components/function-plot/dist/function-plot.js\"></script>");
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
            out.println("<li><a href='login.html'>Cerrar jSesión</a></li>");
            out.println("</ul></li>");
            out.println("</div>");
            out.println("<div class='title'><h1>Agregar grupo</h1></div>");
            out.println("<div class='form IngresaE'>");
            out.println("<form method='get'>");            
            out.println("<label for='ide'>ID de ejercicio</label>");
            out.println("<input type='text' name='id' ide='ide' required title='Campo Requerido' place holder='Ej.Ejercicio1' />");            
            out.println("<label for='asignacion'>Fecha de asignacion</label>");
            out.println("<input type='date' name='asignacion'/>");                       
            out.println("<label for='entrega'>Fecha de entrega</label>");
            out.println("<input type='date' name='entrega'/>");            
            out.println("<label for='desc'>Descripcion</label><br>");
            out.println("<textarea rows='4' columns='100' name='desc' id='desc' required title='Campo Requerido'></textarea><br />");
            //SELECT
            out.println("<label for='tipo'>Tipo</label>");
            out.println("<select name='tipo' id='tipo' onchange='generate()'>");
            out.println("<option >Linea </option>");
            out.println("<option >Circunferencia</option>");
            out.println("<option >Elipse</option>");
            out.println("<option >Hiperbola</option>");
            out.println("</select>");
            
            out.println("<div id='carac'>");
            out.println("<label for='x1'>x1:</label><input type='text' id='x1' name='x1'/>");
            out.println("<label for='y1'>y1:</label><input type='text' id='y1' name='y1'/>");
            out.println("<label for='x2'>x2:</label><input type='text' id='x2' name='x2'/>");
            out.println("<label for='y2'>y2:</label><input type='text' id='y2' name='y2'/>");
            out.println("</div>");
            out.println("<input type='button' value='Graficar' onclick='choose()'/>");
            out.println("<input type='submit' value='Agregar Ejercicio' onclick=\"this.form.action = 'IngresarE'\" />");
            out.println("</form>");
            out.println("</div>");
            out.println("<div id='plot-id'></div>");
            out.println("<script  src='"+request.getContextPath()+"/funciones.js'></script>");
            out.println("</body>");
            out.println("</html>");     
    }
}
