/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints1;

import entiteti.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Goran 1,2,3,4 REST upiti
 */
@Path("podSistem1/kreiranje")
@RequestScoped
public class KreiranjeRes {
    
    @Resource( lookup = "IS_CentralniFac1", name = "IS_CentralniFac1Res")
    private ConnectionFactory connectionFactory;
    
    @Resource ( lookup = "IS_PodSistemTopic1", name = "IS_PodSistemTopic1")
    private Topic myTopic;
    
    @Resource ( lookup = "IS_CSQueue1", name = "IS_CSQueue1Res")
    private Queue myQueue;
    
    @POST
    @Path("mesto/{Naziv}/{PostanskiBroj}")
    public Response kreirajMesto( @PathParam("Naziv") String naziv, @PathParam("PostanskiBroj") int pBr ){
    
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=1");
            
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            Mesto mesto = new Mesto();
            mesto.setNaziv(naziv);
            mesto.setPostanskiBroj(pBr);
            
            ObjectMessage objMsg = context.createObjectMessage(mesto);
            objMsg.setIntProperty("Sistem", 1);
            objMsg.setIntProperty("Upit", 1);
            producer.send(myTopic, objMsg);
            System.out.println("Poslata poruka");
            Message msg = consumer.receive(5000);
            System.out.println("Primljena poruka");
            
            if (msg instanceof TextMessage && msg != null) {
               TextMessage txtMsg = (TextMessage)msg;
               System.out.println(txtMsg.getText());
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
    @Path("filijala/{FilIme}/{adresa}/{mesto}")
    public Response kreirajFilijalu(@PathParam("FilIme") String FilIme, @PathParam("adresa") String adresa, @PathParam("mesto") String mesto ){
    
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=2");
            
            for (int i = 0; i < 100; i++) {
                consumer.receiveNoWait();
            }
            Filijala filijala = new Filijala();
            filijala.setAdresa(adresa);
            filijala.setMestoF(mesto);
            filijala.setNaziv(FilIme);
            ObjectMessage objMsg = context.createObjectMessage(filijala);
            objMsg.setIntProperty("Sistem", 1);
            objMsg.setIntProperty("Upit", 2);
            producer.send(myTopic, objMsg);
            
            Message msg = consumer.receive(5000);
            if (msg instanceof TextMessage && msg != null) {
                TextMessage txtMsg = (TextMessage)msg;
                System.out.println(txtMsg.getText());
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
    @Path("komitent/{KomitentIme}/{adresa}/{mesto}")
    public Response kreirajKomitenta(@PathParam("KomitentIme") String KomitentIme, @PathParam("adresa") String adresa, @PathParam("mesto") String mesto){
    
         try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=3");
            
            Komitent komitent = new Komitent();
            komitent.setAdresa(adresa);
            komitent.setMesto(mesto);
            komitent.setNaziv(KomitentIme);
            ObjectMessage objMsg = context.createObjectMessage(komitent);
            objMsg.setIntProperty("Sistem", 1);
            objMsg.setIntProperty("Upit", 3);
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
    @Path("sediste/{KomitentIme}/{Sediste}")
    public Response promenaSedista( @PathParam("KomitentIme") String KomitentIme, @PathParam("Sediste") String Sediste ){
    
         try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Upit=4");
            
            TextMessage objMsg = context.createTextMessage(KomitentIme);
            objMsg.setIntProperty("Sistem", 1);
            objMsg.setIntProperty("Upit", 4);
            objMsg.setStringProperty("Sediste", Sediste);
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
