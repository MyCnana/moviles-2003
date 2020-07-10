package com.unitec.mycnana;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//Representational State Transfer Controller
//Los estados mas comunes son: guardar, buscar, actualizar y borrar
@RestController
//api: Application Programming Interface - Interfaz de Programacion de la Aplicacion
@RequestMapping("/api")
public class ControladorPerfil {
    
    //Esta es la inversion de control o inyeccion de dependencias
    
    @Autowired RepoPerfil repoPerfil;
    
    //En los servicios Rest tiene un a URL Base: de la ip o host, seguida del puerto, despues
    //aip/hola. Es decir para esta caso mi api REST es:
    //http://localhost:8080/api/hola
    @GetMapping("/hola")
    public Saludo saludar(){
       Saludo s = new Saludo();
       s.setNombre("Diana Medina");
       s.setMensaje("Mi primer mensaje en spring rest");
       return s;
    }
    //El siguiente método va a servir para guardar en un back-end nuestros datos del perfil
    //Para guardar simepre debes usar el método POST
    @PostMapping("/perfil")
    public Estatus guardar(@RequestBody String json)throws Exception{
        //Paso 1 para recibir ese objeto json es leerlo y convertirlo en objeto JAVA
        //a eso se le llama des-serialización
        ObjectMapper maper = new ObjectMapper();
        Perfil perfil = maper.readValue(json, Perfil.class);
        //Por experiencia antes de guardar tenemos que checar que llego bien todo e objeto y se leyó bien
        System.out.println("Perfil leido"+perfil);
        
        //Aquí este objeto perfil después se guarda con una sola línea en MongoDB
        //Aquí va ir la línea para guardar
        
        repoPerfil.save(perfil);
        
        //Después enviamos un mensaje de estatus al cliente para que se informe si se guardo o no su perfil
        Estatus e = new Estatus();
        e.setSuccess(true);
        e.setMensaje("Perfil guardado con exito!!!");
        return e;
    }
    
    //Vamos a generar nuestro servicio para actualizar un perfil
    @PutMapping("/perfil")
    public Estatus actualizar(@RequestBody String json)throws Exception{
        ObjectMapper maper = new ObjectMapper();
        Perfil perfil = maper.readValue(json, Perfil.class);
        //Por experiencia antes de guardar tenemos que checar que llego bien todo e objeto y se leyó bien
        System.out.println("Perfil leido"+perfil);
        
        //Aquí este objeto perfil después se guarda con una sola línea en MongoDB
        //Aquí va ir la línea para guardar
        
        repoPerfil.save(perfil);
        
        //Después enviamos un mensaje de estatus al cliente para que se informe si se guardo o no su perfil
        Estatus e = new Estatus();
        e.setSuccess(true);
        e.setMensaje("Perfil actualizado con exito!!!");
        return e;
    }
            
    //Estado para borrar un perfil
    @DeleteMapping("/perfil/(id)")
    public Estatus borrar(@PathVariable String id){
        //Invocamos el repositorio
        repoPerfil.deleteById(id);
        //Generamos el mansaje para informar al cliente
        Estatus e = new Estatus();
        e.setSuccess(true);
        e.setMensaje("Perfil borrado con exito!!!");
        return e;
    }
    
   //El metodo para buscar todos
    @GetMapping("/perfil")
    public List<Perfil> buscarTodos(){
        return repoPerfil.findAll();
    }
    
    //El metodo de buscar por id
    @GetMapping("/perfil/{id}")
    public Perfil buscarPorId(@PathVariable String id){
        return repoPerfil.findById(id).get();
    }
}

//A este tipo de controlador estilo REST, es muy poderoso y se utiliza en todas 
//las arquitecturas estilo REST, y se le denomina CONSTRUCCION DE API's
//API = Application Programing Interface
//Aqui nuestra interfaz es cliente(andriod) y servidor (java)