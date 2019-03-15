package inicio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class BaseXML {
    URL url;
    SAXBuilder builder = new SAXBuilder();
    String datos []={"user","nombre","password","tipo", "correo", "grupo"};//Los datos del usuario
    String path;
    
    //CONSTRUCTOR
    public BaseXML(String pathR) throws MalformedURLException, IOException {
        this.url = new URL("http://localhost:8080/Proyecto/Usuarios.xml"); //ubicacion de xml
        path=pathR;
    }

    public BaseXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //VERIFICA QUE EXISTA EL USUARIO EN LA BD Y REGRESA EL TIPO 
    public String VerificarUsuario(String user, String password){
        String tipo="Error al iniciar sesión ";
        try{
            //Elementos para acceder al XML
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("usuario");
            Element usuarios,usuario,pass; //usuarios=nodos (usuario) del xml     usuario=user de usario
    
            for(int i=0;i<list.size();i++){ //Recorremos cada nodo usuario
                 usuarios= (Element)list.get(i);
                 List Luser = usuarios.getChildren("user");                  
                 usuario = (Element)Luser.get(0); //Obtenemos la boleta   

                 List Lpassword = usuarios.getChildren("password");
                 pass = (Element)Lpassword.get(0); //Obtenemos el pass

                 if(user.equals(usuario.getTextTrim()))
                 {
                     if(password.equals(pass.getTextTrim())){
                     List Ltipo = usuarios.getChildren("tipo"); //Obtenemos el tipo
                     Element type = (Element)Ltipo.get(0);
                     tipo=type.getTextTrim();
                     break;
                     }
                     else       //Se ingreso un usuario existente con una contraseña incorrecta
                        tipo="Contraseña incorrecta ";
                 }
             }
       
        }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
        return tipo;
    }
    
    //OBTIENE EL NOMBRE DE UN USUARIO (BUSCANDOLO POR USER)
    public String ObtenerNombre(String user, String password){
        String nombre="Sin nombre";
        try{
           //Elementos para acceder al XML
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("usuario");
            Element usuarios,usuario,pass;
    
            for(int i=0;i<list.size();i++){ //Recorremos cada nodo usuario
                 usuarios= (Element)list.get(i);
                 List Luser = usuarios.getChildren("user"); //Obtenemos la boleta                   
                 usuario = (Element)Luser.get(0);   

                 List Lpassword = usuarios.getChildren("password"); //Obtenemos el pass
                 pass = (Element)Lpassword.get(0);

                 if(user.equals(usuario.getTextTrim()) && password.equals(pass.getTextTrim())){
                     List Lnom = usuarios.getChildren("nombre"); //Obtenemos el nombre
                     Element name = (Element)Lnom.get(0);
                     nombre=name.getTextTrim();
                     break;
                 }
             }
       
        }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
        return nombre;
    }
    
    //OBTIENE EL USER DE UN USUARIO (BUSCANDOLO POR NOMBRE)
    public String ObtenerUser(String nombre){
        String user="Sin user";
        try{
           //Elementos para acceder al XML
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("usuario");
            Element usuarios,usuario,pass;
    
            for(int i=0;i<list.size();i++){ //Recorremos cada nodo usuario
                 usuarios= (Element)list.get(i);
                 List Lnom = usuarios.getChildren("nombre"); //Obtenemos el nombre
                 Element name = (Element)Lnom.get(0);

                 if(nombre.equals(name.getTextTrim()) ){
                     List Luser = usuarios.getChildren("user"); //Obtenemos la boleta                   
                     usuario = (Element)Luser.get(0);   
                     user=usuario.getTextTrim();
                     break;
                 }
             }
       
        }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
        return user;
    }
    
    //OBTIENE EL USER DE UN USUARIO POR SU INDICE EN EL XML 
    public String ObtenerUser(int posicion){
        String user = "";
        try
        {
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("usuario");
                   Element usuario = (Element)list.get(posicion);                    
                   List lista_user = usuario.getChildren("user");                  
                   Element nodo_user = (Element)lista_user.get(0);
                   String nombre_usuario = nodo_user.getTextTrim();
                   user = nombre_usuario;
        }catch(IOException io){
            io.printStackTrace();
        }catch(JDOMException jdomex){
            jdomex.printStackTrace();
        }
        return user;
    }
    
    //AGREGA UN USUARIO (SIN ASIGNARLO A UN GRUPO)
     public String AgregarUsuario(String valores[]){
        String cadena = "No se puedo registrar al usuario ";
        try
        {
            Document document = builder.build(url);
            Element raiz = document.getRootElement();
            Element e_usuario = new Element("usuario");
            //AGREGA LAS ETIQUETAS (user, nombre, password, tipo, correo, grupo) CON LOS VALORES DE USUARIO
            for(int i=0;i<datos.length;i++){
                Element e_nombre = new Element(datos[i]); //crea el nodo 
                e_nombre.setText(valores[i]); //agrega el valor del nodo
                e_usuario.addContent(e_nombre); //agrega el nodo a usuario
            }
            
            //AGREGA EL USUARIO AL ELEMENTO RAIZ
            raiz.addContent(e_usuario);
            Format formato = Format.getPrettyFormat();
            XMLOutputter xmloutputter = new XMLOutputter(formato);
            FileWriter writer= new FileWriter(path+"Usuarios.xml");
            xmloutputter.output(document, writer);
            writer.close();  
            cadena = "Usuario registrado correctamente ";
            
        }catch(IOException io){
            io.printStackTrace();
        }catch(JDOMException jdomex){
            jdomex.printStackTrace();
        }
        return cadena;
    }
     
    //OBTENER NUMERO DE USUARIOS EN XML (Usuarios.xml)
    public int NumeroUsuarios(){
        int usuarios = 0; //variable para almacenar el numero de ususarios   
        try
        {
            //Elementos para acceder al XML
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("usuario");
            usuarios = list.size();//Optenemos los usuarios
        }catch(IOException io){
            io.printStackTrace();
        }catch(JDOMException jdomex){
            jdomex.printStackTrace();
        }
        return usuarios;
    }
    
    //OBTIENE EL NUMERO DE GRUPOS EN EL XML (Grupos.xml)
     public int NumeroGrupos(){
        int grupos = 0;   
        try
        {
            URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");
            grupos = list.size();
        }catch(IOException io){
            io.printStackTrace();
        }catch(JDOMException jdomex){
            jdomex.printStackTrace();
        }
        return grupos;
    }
    
    //OBTIENE EL NOMBRE DE UN GRUPO (buscandolo por indice)  
    public String ObtenerNombreGrupo(int posicion){
        String nombre = ""; //Para almacenar el nombre de cada grupo
        try
        {
            //Elementos para acceder al XML del Grupo
            URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");    
                //Obtenemos nombre del grupo
                Element grupo = (Element)list.get(posicion);                    
                List lista_nombre = grupo.getChildren("nombre");                  
                Element nodo_nombre = (Element)lista_nombre.get(0);
                String nombre_grupo = nodo_nombre.getTextTrim();
                nombre = nombre_grupo;
        }catch(IOException io){
            io.printStackTrace();
        }catch(JDOMException jdomex){
            jdomex.printStackTrace();
        }
        return nombre;
    }
    
   //AGREGA UN USUARIO A UN GRUPO
   public void AgregaUsuarioGrupo(String nombreUsuario, String nombreGrupo, String tipo){
       int grupoActual=-1; //por si no se encuentra
       try {
               URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
               Document document = builder.build(url);
               Element raiz = document.getRootElement();
               List list=raiz.getChildren("grupo");
               Element grupo, nombre;
               //buscar la posicion del usuario
               for(int i=0; i<list.size();i++){
                   grupo = (Element)list.get(i);
                   List Lnombre = grupo.getChildren("nombre");
                   nombre = (Element)Lnombre.get(0);
                   //Compara el nombre ingresado con el del xml
                   if(nombreGrupo.equals(nombre.getTextTrim())){ //verificamos si es el grupo que buscamos
                       grupoActual=i;
                       break;
                   }
               }           
               
            //SI SE ENCONTRÓ EL INDICE DEL GRUPO
            if(grupoActual!=-1){
               Element e_grupo = (Element)list.get(grupoActual);
               Element e_usuario = new Element(tipo.toLowerCase()); //se crea un usuario con el rol de (tipo)
               e_usuario.setText(nombreUsuario); //se agrega el nombre al valor del nodo
               e_grupo.addContent(e_usuario);  //se agrega el usuario al nodo de grupo
               Format formato = Format.getPrettyFormat();
                XMLOutputter xmloutputter = new XMLOutputter(formato);
                FileWriter writer = new FileWriter(path+"Grupos.xml");
                xmloutputter.output(document, writer);
           }
            } catch (IOException io) {
                io.printStackTrace();
            } catch (JDOMException jdomex) {
               jdomex.printStackTrace();
            }
    }     
   
   //BUSCA A UN USUARIO (por user) Y REGRESA EL INDICE DE DONDE SE ENCUENTRA EN EL XML
   public int BuscarUsuario(String user){
       int indice=-1; //por si no se encuentra
       try{
           Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("usuario");
            Element usuarios, usuario; //usuarios=nodos (usuario) del xml     usuario=user de usario
    
            for(int i=0;i<list.size();i++){ //Recorremos cada nodo usuario
                 usuarios= (Element)list.get(i);
                 List Luser = usuarios.getChildren("user");                  
                 usuario = (Element)Luser.get(0); //Obtenemos la boleta   

                 if(user.equals(usuario.getTextTrim())){ //verificamos si es el usuario que buscamos
                     indice = i;
                     break;
                 }
            }
       }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
       return indice;
   }
   
   //RECUPERA LOS DATOS DE UN USUARIO (BUSCANDOLO POR INDICE)
   public String[] ObtenerDatosUsuario(int Nuser){
        String datosUser[]=new String[datos.length];
        try{
            Document document = builder.build(url);
            Element raiz = document.getRootElement();
            List list = raiz.getChildren("usuario");
            Element usuario=(Element)list.get(Nuser); //se obtiene el usuario requerido
            //se recuperan cada uno de los datos del usuario
            for(int i=0;i<datos.length;i++){
                List ln=usuario.getChildren(datos[i]);
                Element value=(Element)ln.get(0);
                datosUser[i]=value.getTextTrim();
            }
            }catch(IOException io){
                io.printStackTrace();
            }catch(JDOMException jdomex){
                jdomex.printStackTrace();
            } 
           
           return datosUser;
    }
   
    //ELIMINA A USUARIO POR USER
   public String EliminarUsuario(String user){
       String cad = "No se puede eliminar al usuario";
       try{
           Document document = builder.build(url);
           Element raiz = document.getRootElement();
           List list = raiz.getChildren("usuario");
           for(int i=0; i<list.size(); i++){
               Element usuario = (Element)list.get(i);  //recorremos los nodos usuario
               List listU = usuario.getChildren("user");   
               Element userN = (Element) listU.get(0);    //obtenemos el nodo user
               String userV = userN.getTextTrim();      //recuperamos su valor
               if(user.equals(userV)){          //verificamos que sea el que buscamos
                   list.remove(i);  //eliminamos al usuario
                   Format formato = Format.getPrettyFormat();
                   XMLOutputter xmloutputter = new XMLOutputter(formato);
                   FileWriter writer = new FileWriter(path+"Usuarios.xml");
                   xmloutputter.output(document, writer);
                   cad = "Se eliminó correctamente al usuario";
                   break;
               }
           }
           
       } catch(IOException io){
                io.printStackTrace();
       }catch(JDOMException jdomex){
                jdomex.printStackTrace();
       } 
       return cad;
   }

//ELIMINA A UN USUARIO (por indice)
   public String EliminarUsuario(int indice){
       String cad = "No se pudo eliminar al usuario ";
       try{
           Document document = builder.build(url);
            Element raiz = document.getRootElement();
            List list = raiz.getChildren("usuario");
            String nombre, grupo, tipo;
            Element usuario, Unombre, Ugrupo, Utipo;
            usuario = (Element) list.get(indice); //obtenemos al usuario que vamos a eliminar
            System.out.println("Indice: "+indice);
            //recuperamos su nombre, grupo y tipo
            List Lnombre = usuario.getChildren("nombre");
            Unombre = (Element) Lnombre.get(0);
            nombre = Unombre.getTextTrim();
            List Lgrupo = usuario.getChildren("grupo");
            Ugrupo = (Element) Lgrupo.get(0);
            grupo = Ugrupo.getTextTrim();
            List Ltipo = usuario.getChildren("tipo");
            Utipo = (Element) Ltipo.get(0);
            tipo = Utipo.getTextTrim().toLowerCase();
            System.out.println("Se quiere eliminar a: "+nombre + " " + tipo + " " + grupo);
            //tratamos de eliminar primero al usuario del grupo
            if(EliminarUsuarioGrupo(nombre, grupo, tipo) == 1){
                System.out.println("Se puedo borrarlos del xml grupo");
                list.remove(indice);//se elimina el usuario del xml de usuarios
                Format formato = Format.getPrettyFormat();
                XMLOutputter xmloutputter = new XMLOutputter(formato);
                FileWriter writer = new FileWriter(path+"Usuarios.xml");
                xmloutputter.output(document, writer);
                cad = "Se elimino correctamente al usuario ";
            }
       }
       catch(IOException io){
                io.printStackTrace();
            }catch(JDOMException jdomex){
                jdomex.printStackTrace();
            } 
       return cad;
   }

   //CAMBIA EL GRUPO DE UN USER A "sin asignar"
   public int CambiarGrupo(String user){
       int indice=-1; //por si no se encuentra
       try{
           Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("usuario");
            Element usuarios, usuario; //usuarios=nodos (usuario) del xml     usuario=user de usario
    
            for(int i=0;i<list.size();i++){ //Recorremos cada nodo usuario
                 usuarios= (Element)list.get(i);
                 List Luser = usuarios.getChildren("user");                  
                 usuario = (Element)Luser.get(0); //Obtenemos la boleta   

                 if(user.equals(usuario.getTextTrim())){ //verificamos si es el usuario que buscamos
                     List Lgrupo = usuarios.getChildren("grupo");
                     Element grupo = (Element) Lgrupo.get(i);
                     grupo.setText("Sin asignar");
                     Format formato = Format.getPrettyFormat();
                    XMLOutputter xmloutputter = new XMLOutputter(formato);
                    FileWriter writer = new FileWriter(path+"Usuarios.xml");
                    xmloutputter.output(document, writer);
                    indice = 1;
                     break;
                 }
            }
       }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
       return indice;
   }
   
//ELIMINA A UN USUARIO DE UN GRUPO
   public int EliminarUsuarioGrupo(String nombreUsuario, String nombreGrupo, String tipo){
       int i = -1;
       try{
           URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
           Document document = builder.build(url);
            Element raiz = document.getRootElement();
            List list = raiz.getChildren("grupo");
            Element grupo, usuario, nombre;
            int posicion = BuscarGrupo(nombreGrupo); //obtenemos la posicion del grupo
            System.out.println("Grupo: "+posicion);
            grupo = (Element) list.get(posicion);    
            List Lusuario = grupo.getChildren(tipo);
            //buscamos al usuario
            for(int j=0; j<Lusuario.size(); j++){
                usuario = (Element) Lusuario.get(j);
                if(nombreUsuario.equals(usuario.getTextTrim())){
                    Lusuario.remove(j); //lo eliminamos
                    Format formato = Format.getPrettyFormat();
                    XMLOutputter xmloutputter = new XMLOutputter(formato);
                    FileWriter writer = new FileWriter(path+"Grupos.xml");
                    xmloutputter.output(document, writer);
                    i = 1; //se elimino correctamnte
                    break;
                }
            }
       }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
       return i;
   }
   
   //AGREGA GRUPO CON NOMBRE
   public String AgregarGrupo(String nombreG){
       String cad = "No se pudo agregar el grupo ";
       try{
           URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element raiz = document.getRootElement();
            Element grupo = new Element("grupo"); //se crea un nodo grupo
            Element nombre = new Element("nombre"); //se crea un nodo nombre
            nombre.setText(nombreG); //se agrega el nombre del grupo
            grupo.addContent(nombre); //se agrega el nodo nombre al nodo grupo
            raiz.addContent(grupo); //se agrega el nodo grupo al nodo raiz 
            cad = "Se agrego correctamente el grupo ";
           Format formato = Format.getPrettyFormat();
            XMLOutputter xmloutputter = new XMLOutputter(formato);
            FileWriter writer = new FileWriter(path+"Grupos.xml");
            xmloutputter.output(document, writer);
       }
       catch(IOException io){
            io.printStackTrace();
        }catch(JDOMException jdomex){
            jdomex.printStackTrace();
        } 
       return cad;
   }
   
   //BUSCA UN GRUPO POR NOMBRE Y RETORNA EL INDICE DEL XML
   public int BuscarGrupo(String nombre){
       int indice = -1; //por si no se encuentra
       try{
           URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
           Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");
            Element grupo, nombreGrupo;
    
            for(int i=0;i<list.size();i++){ //Recorremos cada nodo grupo
                 grupo = (Element)list.get(i);
                 List Lnombre = grupo.getChildren("nombre");                  
                 nombreGrupo = (Element)Lnombre.get(0); //Obtenemos el nombre   

                 if(nombre.equals(nombreGrupo.getTextTrim())){ //verificamos si es el grupo que buscamos
                     indice = i;
                     break;
                 }
            }
       }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
       return indice;
   }
   
   //ELIMINA UN GRUPO (por indicie)
   public String EliminarGrupo(int indice){
       String cad = "No se pudo eliminar grupo ";
       try{ 
           //eliminamos nodo grupo
           URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
           Document document = builder.build(url);
            Element raiz = document.getRootElement();
            List list = raiz.getChildren("grupo");
            list.remove(indice);
            Format formato = Format.getPrettyFormat();
            XMLOutputter xmloutputter = new XMLOutputter(formato);
            FileWriter writer= new FileWriter(path+"Grupos.xml");
            xmloutputter.output(document, writer);
            writer.close();
            cad = "Se elimino correctamente el grupo ";
       }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
       return cad;
   }
   
   //MODIFICA UN USUARIO
   public String ModificarUsuario(int indice, String valores[]){
       String cad = "Se modifico al usuario ";
       EliminarUsuario(indice);
       //AgregarUsuario(valores);
       //AgregaUsuarioGrupo(valores[1],valores[5],valores[3]);
       return cad;
   }
   
   //OBTENER NUMERO USUARIOS EN UN GRUPO (por posicion de grupo)
   public int NumeroProfesoresGrupo(int posicion){
       int numU = 0;
       try{
           //Elementos para acceder al XML del Grupo
            URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");    
            Element grupo = (Element)list.get(posicion);   //obtenemos el grupo deseado                 
            List Lprofesor = grupo.getChildren("profesor"); //obtenemos los nodos de profesor
            numU = Lprofesor.size();
       }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
       return numU;
   }
   
   //OBTENER NUMERO USUARIOS EN UN GRUPO (por posicion de grupo)
   public int NumeroAlumnosGrupo(int posicion){
       int numU = 0;
       try{
           //Elementos para acceder al XML del Grupo
            URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");    
            Element grupo = (Element)list.get(posicion);   //obtenemos el grupo deseado                 
            List Lalumno = grupo.getChildren("alumno"); //obtenemos los nodos de profesor
            numU = Lalumno.size();
       }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
       return numU;
   }
   
   //OBTENER NUMERO ADMINISTRADORES EN UN GRUPO
   public int NumeroAdmins(int posicion){
       int numU = 0;
       try{
           //Elementos para acceder al XML del Grupo
            URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");    
            Element grupo = (Element)list.get(posicion);   //obtenemos el grupo deseado                 
            List Lalumno = grupo.getChildren("administrador"); //obtenemos los nodos de profesor
            numU = Lalumno.size();
       }
       catch(JDOMException E){
           E.printStackTrace();
       }
       catch(IOException E){
           E.printStackTrace();
       }
       return numU;
   }
   
   //OBTENER NUMERO USUARIOS EN UN GRUPO (por posicion de grupo)
   public int NumeroUsuariosGrupo(int posicion){
       return NumeroProfesoresGrupo(posicion) + NumeroAlumnosGrupo(posicion);
   }
   
   //OBTENER LOS PROFESORES DE UN GRUPO
   public String ObtenerProfesorGrupo(int posicion){
       String profes=" ";
       try{
           //Elementos para acceder al XML del Grupo
            URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element  rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");    
            Element grupo = (Element)list.get(posicion);   //obtenemos el grupo deseado
            List Lprofesor = grupo.getChildren("profesor");  //obtenemos a todos los profesores
            Element profesor = (Element) Lprofesor.get(0);
            profes = profesor.getTextTrim();
            
       }
       catch(IOException io){
                io.printStackTrace();
       }catch(JDOMException jdomex){
                jdomex.printStackTrace();
       }
       return profes;
   }
   
   //OBTENER LOS ALUMNOS DE UN GRUPO
   public String[] ObtenerAlumnoGrupo(int posicion){
       String alumnos[] = new String[NumeroAlumnosGrupo(posicion)];
       try{
           //Elementos para acceder al XML del Grupo
            URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");    
            Element grupo = (Element)list.get(posicion);   //obtenemos el grupo deseado
            List Lalumno = grupo.getChildren("alumno");  //obtenemos a todos los alumnos
            for(int i=0;i<NumeroAlumnosGrupo(posicion);i++){
                Element alumno = (Element) Lalumno.get(i); //obtenemos al profesor en indice i
                alumnos[i]=alumno.getTextTrim();
            }
       }
       catch(IOException io){
                io.printStackTrace();
       }catch(JDOMException jdomex){
                jdomex.printStackTrace();
       }
       return alumnos;
   }
   
   //OBTENER LOS ADMINISTRADORES DE UN GRUPO
   public String[] ObtenerAdmins(int posicion){
       String admins[] = new String[NumeroAdmins(posicion)];
       try{
           //Elementos para acceder al XML del Grupo
            URL url = new URL("http://localhost:8080/Proyecto/Grupos.xml");
            Document document = builder.build(url);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("grupo");    
            Element grupo = (Element)list.get(posicion);   //obtenemos el grupo deseado
            List Lprofesor = grupo.getChildren("administrador");  //obtenemos a todos los profesores
            for(int i=0;i<NumeroAdmins(posicion);i++){
                Element profesor = (Element) Lprofesor.get(i); //obtenemos al profesor en indice i
                admins[i]=profesor.getTextTrim();
            }
       }
       catch(IOException io){
                io.printStackTrace();
       }catch(JDOMException jdomex){
                jdomex.printStackTrace();
       }
       return admins;
   }
  
   //GUARDA UN EJERCICIO DEJADO POR EL PROFESOR
   public String AsignarEjercicio(String id, String grupo, String asignacion, String entrega, String profesor, String tipo, String descripcion, String ecuacion, String datos[]){
       String cad = "No pudo asignarse el ejercicio";
       try{
           //Elementos para acceder al xml
           URL url = new URL("http://localhost:8080/Proyecto/Ejercicios.xml");
           Document document = builder.build(url);
           Element rootNode = document.getRootElement(); //nodo raiz
           Element ejercicio = new Element("ejercicio");  //nodo ejercicio
           Element nodo;
           //nodo id
           nodo = new Element("id"); //se declara el nodo
           nodo.setText(id); //se asigna el valor al nodo
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           //nodo grupo
           nodo = new Element("grupo"); //se declara el nodo
           nodo.setText(grupo); //se asigna el valor al nodo
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           //nodo asignacion (feche)
           nodo = new Element("asignacion"); //se declara el nodo
           nodo.setText(asignacion); //se asigna el valor al nodo
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           //nodo fecha (feche)
           nodo = new Element("entrega"); //se declara el nodo
           nodo.setText(entrega); //se asigna el valor al nodo
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           //nodo profesor
           nodo = new Element("profesor"); //se declara el nodo
           nodo.setText(profesor); //se asigna el valor al nodo
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           //nodo tipo
           nodo = new Element("tipo"); //se declara el nodo
           nodo.setText(tipo); //se asigna el valor al nodo
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           //nodo descripcion
           nodo = new Element("descripcion"); //se declara el nodo
           nodo.setText(descripcion); //se asigna el valor al nodo
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           //nodo ecuacion
           nodo = new Element("ecuacion"); //se declara el nodo
           nodo.setText(ecuacion); //se asigna el valor al nodo
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           //nodo datos
           nodo = new Element("datos"); //se declara el nodo
           for(int i=0; i<datos.length; i++){
               Element n_dato = new Element("dato"); //creamos un nodo dato
               n_dato.setText(datos[i]);    //se le asigna un valor
               nodo.addContent(n_dato); //anidamos el dato al nodo "datos"
           }
           ejercicio.addContent(nodo); //se anida el nodo dentro de "ejercicio"
           rootNode.addContent(ejercicio);
           
           Format formato = Format.getPrettyFormat();
            XMLOutputter xmloutputter = new XMLOutputter(formato);
            FileWriter writer = new FileWriter(path+"Ejercicios.xml");
            xmloutputter.output(document, writer);
           cad = "Se asignó el ejercicio con éxito";
           
       } catch(IOException io){
                io.printStackTrace();
       }catch(JDOMException jdomex){
                jdomex.printStackTrace();
       }
       return cad;
   }
   
   //NUMERO DE EJERCICIOS QUE HA ASIGNADO UN PROFE
   public int NumeroEjercicios(String nombreP){
       int num = 0;
       try{
           //Elementos para acceder al xml
           URL url = new URL("http://localhost:8080/Proyecto/Ejercicios.xml");
           Document document = builder.build(url);
           Element rootNode = document.getRootElement(); //nodo raiz
           List Lejer = rootNode.getChildren("ejercicio");
           for(int i=0; i<Lejer.size(); i++){
               Element ejercicio = (Element)Lejer.get(i);  //se recorren los nodos ejercicio
               List Lprofe = ejercicio.getChildren("profesor");
               Element profe = (Element)Lprofe.get(0); //se obtiene el nodo de profesor
               if(nombreP.equals(profe.getTextTrim())){   //se compara con el nombre ingresado por el usuario
                   num++;
               }
           }
           
       } catch(IOException io){
                io.printStackTrace();
       }catch(JDOMException jdomex){
                jdomex.printStackTrace();
       }
       return num;
   }
   
   //DEVUELVE EL NOMBRE DE LOS EJERCICIOS QUE HA ASIGNADO UN PROFE
   public String[] EjerciciosProfe(String nombreP){
       String nombres[] = new String[NumeroEjercicios(nombreP)];
       try{
           //Elementos para acceder al xml
           URL url = new URL("http://localhost:8080/Proyecto/Ejercicios.xml");
           Document document = builder.build(url);
           Element rootNode = document.getRootElement(); //nodo raiz
           List Lejer = rootNode.getChildren("ejercicio");
           int num=0;
           for(int i=0; i<Lejer.size(); i++){
               Element ejercicio = (Element)Lejer.get(i);  //se recorren los nodos ejercicio
               List Lprofe = ejercicio.getChildren("profesor");
               Element profe = (Element)Lprofe.get(0); //se obtiene el nodo de profesor
               if(nombreP.equals(profe.getTextTrim())){   //se compara con el nombre ingresado por el usuario
                   List Lnombre = ejercicio.getChildren("id");  //si es igual al buscado, se recupera el id
                   Element nombre = (Element) Lnombre.get(0);
                   nombres[num] = nombre.getTextTrim();
                   num++;
               }
           }
           
       } catch(IOException io){
                io.printStackTrace();
       }catch(JDOMException jdomex){
                jdomex.printStackTrace();
       }
       return nombres;
   }
   
   //VISUALIZAR INFO (basica) DE UN EJERCICIO SEGUN EL ID (de ejercicio)
   public String[] InfoEjercicio(String id){
       String info[] = new String[6];
       try{
           //Elementos para acceder al xml
           URL url = new URL("http://localhost:8080/Proyecto/Ejercicios.xml");
           Document document = builder.build(url);
           Element rootNode = document.getRootElement(); //nodo raiz
           List Lejer = rootNode.getChildren("ejercicio");
           
       } catch(IOException io){
                io.printStackTrace();
       } catch(JDOMException jdomex){
                jdomex.printStackTrace();
       }
       
       return info;
   }
   
}
