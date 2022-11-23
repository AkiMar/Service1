/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints1;

import entiteti.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author Goran REST upiti 10, 11, 12
 */
@Path("podSistem1/dohvatanje")
public class DohvatanjeRes {
    
    @Resource( lookup = "IS_CentralniFac1", name = "IS_CentralniFac1Res")
    private ConnectionFactory connectionFactory;
    
    @Resource ( lookup = "IS_PodSistemTopic1", name = "IS_PodSistemTopic1")
    private Topic myTopic;
    
    @Resource ( lookup = "IS_CSQueue1", name = "IS_CSQueue1Res")
    private Queue myQueue;
    
    
    @GET
    @Path("mesta")
    public Response dohvatiMesta() throws JMSException{
        try{
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=10");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            TextMessage txtMsg = context.createTextMessage("Dohvati Mesta");
            txtMsg.setIntProperty("Sistem", 1);
            txtMsg.setIntProperty("Upit", 10);
            producer.send(myTopic, txtMsg);
            System.out.println("Dohvatanje mesta poslato");
            
            Message msg = consumer.receive(5000);
            System.out.println("Dosla poruka");
            if (msg instanceof ObjectMessage && msg != null) {
                System.out.println("Dobra poruka");
                Object object = ((ObjectMessage) msg).getObject();
                List<Mesto> listaMesta = (List<Mesto>) object;
                for (Mesto mesto : listaMesta) {
                    System.out.println(mesto.getNaziv());
                }
                //return Response.status(Response.Status.OK).entity("Dosli smo").build();
                context.close();
                return Response.status(Response.Status.OK).entity( new GenericEntity<List<Mesto>>(listaMesta){}).build(); 
            }
            context.close();
        } catch (JMSException ex) {
            System.out.println("GRESKAAAA dohvatanjeMesta");
            
        }
        return Response.status(Response.Status.OK).entity("Doslo do greske").build();
        
    }
    
    @GET
    @Path("filijal")
    public Response dohvatiFilijale(){
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=11");
            for (int i = 0; i < 100; i++) {
               consumer.receiveNoWait();
            }
            TextMessage txtMsg = context.createTextMessage("Dohvati Filijale");
            txtMsg.setIntProperty("Sistem", 1);
            txtMsg.setIntProperty("Upit", 11);
            producer.send(myTopic, txtMsg);
            
            Message msg = consumer.receive(5000);
            if (msg instanceof ObjectMessage && msg != null) {
                Object object = ((ObjectMessage) msg).getObject();
                List<Filijala> listaFilijala = (ArrayList<Filijala>) object;
                for (Filijala fil : listaFilijala) {
                    System.out.println(fil.getNaziv());
                }
                //return Response.status(Response.Status.OK).entity("Dosli smo").build();
                context.close();
                return Response.status(Response.Status.OK).entity( new GenericEntity<List<Filijala>>(listaFilijala){}).build(); 
            }
            context.close();
        } catch (JMSException ex) {
         
        }
        return Response.status(Response.Status.OK).entity("Doslo do greske").build();
        
    }
    
    @GET
    @Path("komitent")
    public Response dohvatiKomitente(){
        
       try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=12");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            TextMessage txtMsg = context.createTextMessage("Dohvati Komitente");
            txtMsg.setIntProperty("Sistem", 1);
            txtMsg.setIntProperty("Upit", 12);
            producer.send(myTopic, txtMsg);
            
            Message msg = consumer.receive(5000);
            if (msg instanceof ObjectMessage && msg != null) {
                Object object = ((ObjectMessage) msg).getObject();
                List<Komitent> listaKomitent = (ArrayList<Komitent>) object;
                context.close();
                return Response.status(Response.Status.OK).entity( new GenericEntity<List<Komitent>>(listaKomitent){}).build(); 
            }
            context.close();
        } catch (JMSException ex) {
          
        }
        return Response.status(Response.Status.OK).entity("Doslo do greske").build(); 
        
    }
    
}
