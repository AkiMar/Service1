/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints2;

import endpoints1.KreiranjeRes;
import entiteti.Isplata;
import entiteti.Mesto;
import entiteti.SaNa;
import entiteti.Uplata;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author Goran upiti 5, 6, 7, 8, 9
 */
@Path("podSistem2/obrada")
public class ObradaRes {
    
    @Resource( lookup = "IS_CentralniFac1", name = "IS_CentralniFac1Res")
    private ConnectionFactory connectionFactory;
    
    @Resource ( lookup = "IS_PodSistemTopic1", name = "IS_PodSistemTopic1")
    private Topic myTopic;
    
    @Resource ( lookup = "IS_CSQueue1", name = "IS_CSQueue1Res")
    private Queue myQueue;
    
    @POST
    @Path("otvaranjeRacuna") // RADI
    public Response otvaranjeRacuna(String KomitentNaziv){
    
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=5");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            
            TextMessage txtMsg = context.createTextMessage(KomitentNaziv);
            txtMsg.setIntProperty("Sistem", 2);
            txtMsg.setIntProperty("Upit", 5);
            producer.send(myTopic, txtMsg);
            
            Message msg = consumer.receive(5000);
            if( msg == null){
                context.close();
                return Response.status(Response.Status.OK).entity("Timeout ERROR").build();
            }
            
            TextMessage txtMsgRec = (TextMessage)msg;
            System.out.println("----------------------------------");
            System.out.println(txtMsg.getText());
            context.close();
            return Response.status(Response.Status.OK).entity(txtMsgRec.getText()).build(); 
            
        } catch (JMSException ex) {
            Logger.getLogger(KreiranjeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).entity("Doslo do greske").build();
    }
    
    @POST
    @Path("zatvaranjeRacuna/{IdRac}") // RADI
    public Response zatvaranjeRacuna(@PathParam("IdRac") int IdRac ){
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=6");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            
            TextMessage txtMsg = context.createTextMessage(Integer.toString(IdRac));
            txtMsg.setIntProperty("IdRac", IdRac);
            txtMsg.setIntProperty("Sistem", 2);
            txtMsg.setIntProperty("Upit", 6);
            producer.send(myTopic, txtMsg);
            
            Message msg = consumer.receive(5000);
            if (msg instanceof TextMessage && msg != null) {
                TextMessage txtMsgRec = (TextMessage)msg;
                context.close();
                return Response.status(Response.Status.OK).entity( txtMsgRec.getText() ).build(); 
            }   
            context.close();
        } catch (JMSException ex) {
            Logger.getLogger(KreiranjeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).entity("Doslo do greske TIMEOUT ERROR").build();
        
    }
    
    @POST
    @Path("transakcija") // RADI
    public Response transakcija(SaNa saNa){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=7");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            
            ObjectMessage objMsg = context.createObjectMessage(saNa);
            objMsg.setIntProperty("Sistem", 2);
            objMsg.setIntProperty("Upit", 7);
            producer.send(myTopic, objMsg);
            
            Message msg = consumer.receive(5000);
            if (msg instanceof TextMessage && msg != null) {
                TextMessage txtMsg = (TextMessage)msg;
                context.close();
                return Response.status(Response.Status.OK).entity(txtMsg.getText()).build();
            }
            context.close();
        } catch (JMSException ex) {
            Logger.getLogger(KreiranjeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.OK).entity("Doslo do greske TIMEOUT ERROR").build();
        
    }
    
    @POST
    @Path("uplata") // RADI
    public Response uplataNaRacun(Uplata uplata){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=8");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            
            ObjectMessage objMsg = context.createObjectMessage(uplata);
            objMsg.setIntProperty("Sistem", 2);
            objMsg.setIntProperty("Upit", 8);
            producer.send(myTopic, objMsg);
            
            Message msg = consumer.receive(5000);
            if (msg instanceof TextMessage && msg != null) {
                TextMessage txtMsg = (TextMessage)msg;
                context.close();
                return Response.status(Response.Status.OK).entity(txtMsg.getText()).build();
            }
            context.close();
        } catch (JMSException ex) {
            Logger.getLogger(KreiranjeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.OK).entity("Doslo do greske").build();
        
    }
    
    @POST
    @Path("isplata") // RADI
    public Response isplataSaRacun(Isplata isplata){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=9");
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            
            ObjectMessage objMsg = context.createObjectMessage(isplata);
            objMsg.setIntProperty("Sistem", 2);
            objMsg.setIntProperty("Upit", 9);
            producer.send(myTopic, objMsg);
            
            Message msg = consumer.receive(5000);
            if (msg instanceof TextMessage && msg != null) {
                TextMessage txtMsg = (TextMessage)msg;
                context.close();
                return Response.status(Response.Status.OK).entity(txtMsg.getText()).build();
            }
            context.close();
        } catch (JMSException ex) {
            Logger.getLogger(KreiranjeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.OK).entity("Doslo do greske").build();
        
    }
    
    
    
}
