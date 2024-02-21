/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import lapr.project.utils.Constantes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joaoo
 */
public class EmailsTest {

    public EmailsTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of sendEmail method, of class Emails.
     */
    @org.junit.jupiter.api.Test
    public void testSendEmail() {
        System.out.println("sendEmail");
        String sender = "TesteAPI";
        String alvo = Constantes.EMAIL;
        String assunto = "SelfEmail";
        String texto = "TesteAPI-TESTETESTE";
        boolean result = Emails.sendEmail(sender, alvo, assunto, texto);

        // se nao houve erro a enviar email
        assertTrue(result);

        //try-catch para verificar se o email foi recebido intacto e com todos os atributos
        try {
            String host = "pop.gmail.com";
            String user = Constantes.EMAIL;
            String password = "Lapr3g20";

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message message = emailFolder.getMessage(emailFolder.getMessageCount());

            System.out.println("---------------------------------");
            Assertions.assertEquals(assunto,message.getSubject());
            Assertions.assertEquals(sender+" <"+user+">",message.getFrom()[0].toString());
            Assertions.assertEquals(texto.trim(),message.getContent().toString().trim());

            emailFolder.open(Folder.READ_WRITE);
            message.setFlag(Flags.Flag.DELETED, true);
            //close the store and folder objects
            emailFolder.close(true);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of sendEmail method, of class Emails.
     */
    @Test
    public void testSendEmailCompleto() {
        System.out.println("sendEmail todos parametros");
        String emailEnvio = "random@email.com";
        String chaveEnvio = "wrongPassword";
        String sender = "RANDOM";
        String alvo = "random@email.com";
        String assunto = "TESTE";
        String texto = "TESTE";
        boolean result = Emails.sendEmail(emailEnvio, chaveEnvio, sender, alvo, assunto, texto);
        assertFalse(result);
    }

    /**
     * Test of sendEmail method, of class Emails.
     */
    @org.junit.jupiter.api.Test
    public void testSendEmailFail() {
        System.out.println("sendEmail");
        String sender = "TesteAPI";
        String alvo = ".";
        String assunto = "SelfEmail";
        String texto = "TesteAPI-TESTETESTE";
        boolean result = Emails.sendEmail(sender, alvo, assunto, texto);

        // se nao houve erro a enviar email
        assertFalse(result);
    }

}
