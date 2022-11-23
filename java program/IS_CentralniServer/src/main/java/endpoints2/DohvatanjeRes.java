/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints2;

import endpoints1.KreiranjeRes;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author Goran REST upiti 13, 14
 */
@Path("podSistem2/dohvatanje")
public class DohvatanjeRes {
    
    @Resource( lookup = "IS_CentralniFac1", name = "IS_CentralniFac1Res")
    private ConnectionFactory connectionFactory;
    
    @Resource ( lookup = "IS_PodSistemTopic1", name = "IS_PodSistemTopic1")
    private Topic myTopic;
    
    @Resource ( lookup = "IS_CSQueue1", name = "IS_CSQueue1Res")
    private Queue myQueue;
    
    @GET
    @Path("racuna/{KNaziv}") // RADI
    public Response dohvatanjeRacunaKomitenta(@PathParam("KNaziv") String KomitentNaziv){
        System.out.println("GET racuna");
        System.out.println("Naziv: " + KomitentNaziv);
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=13");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            
            TextMessage txtMsg = context.createTextMessage(KomitentNaziv);
            txtMsg.setIntProperty("Sistem", 2);
            txtMsg.setIntProperty("Upit", 13);
            producer.send(myTopic, txtMsg);
            System.out.println("Poslao poruku----------");
            Message msg = consumer.receive(5000);
            if (msg instanceof ObjectMessage && msg != null) {
                System.out.println("-------- Primio poruku----------");
                Object object = ((ObjectMessage) msg).getObject();
                List<Racun> listaRacuna = (List<Racun>) object;
                for (Racun r : listaRacuna) {
                    System.out.println(r.getIdRac());
                }
                context.close();
                return Response.status(Response.Status.OK).entity( new GenericEntity<List<Racun>>(listaRacuna){}).build(); 
            }   
            context.close();
        } catch (JMSException ex) {
            Logger.getLogger(KreiranjeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.OK).entity("Doslo do greske").build();
    }
    
    
    @GET
    @Path("transakcije/{IdRac}")
    public Response dohvatanjeSvihTransakcija(@PathParam("IdRac") int IdRac ){
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=14");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            
            TextMessage txtMsg = context.createTextMessage(Integer.toString(IdRac));
            txtMsg.setIntProperty("IdRac", IdRac);
            txtMsg.setIntProperty("Sistem", 2);
            txtMsg.setIntProperty("Upit", 14);
            producer.send(myTopic, txtMsg);
            
            Message msg = consumer.receive(5000);
            if (msg instanceof ObjectMessage && msg != null) {
                Object object = ((ObjectMessage) msg).getObject();
                List<Object> listaKomitent = (ArrayList<Object>) object;
                context.close();
                return Response.status(Response.Status.OK).entity( new GenericEntity<List<Object>>(listaKomitent){}).build(); 
            }   
            context.close();
        } catch (JMSException ex) {
            Logger.getLogger(KreiranjeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.OK).entity("Doslo do greske").build(); 
        
        
    }
    
}
