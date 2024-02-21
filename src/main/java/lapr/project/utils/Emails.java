package lapr.project.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Emails {

    /**
     * Construtor private para impedir a construção de objetos deste tipo
     */
    private Emails() {
    }

    /**
     * Constante do email da aplicacao
     */
    private static final String EMAIL = Constantes.EMAIL;
    /**
     * Constante da password da conta de email
     */
    private static final String CHAVE = "Lapr3g20";

    /**
     * Metodo para enviar email
     *
     * @param sender nome de quem envia
     * @param alvo email do destinatario
     * @param assunto assunto do email
     * @param texto corpo do email
     * @return boolean se enviou email
     */
    public static boolean sendEmail(String sender, String alvo, String assunto, String texto) {
        return sendEmail(EMAIL, CHAVE, sender, alvo, assunto, texto);
    }

    /**
     * Metodo para enviar email. Metodo mais completo do SendEmail
     *
     * @param emailEnvio email de quem envia
     * @param chaveEnvio palavra chave de quem envia
     * @param sender nome de quem envia
     * @param alvo email do destinario
     * @param assunto assunto do email
     * @param texto corpor do email
     * @return boolean se enviou email
     */
    protected static boolean sendEmail(String emailEnvio, String chaveEnvio, String sender, String alvo, String assunto, String texto) {
        try {
            Properties props = carregarPropriedades();
            Session ses = loginEmail(emailEnvio, chaveEnvio, props);
            Message msg = prepararMensagem(emailEnvio, ses, sender, alvo, assunto, texto);
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo para carregar as propriedades para aceder ao servidor da google
     * responsável pelo gmail e envio de email
     *
     * @return ficheiro Properties com as propriedades
     */
    private static Properties carregarPropriedades() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return props;
    }

    /**
     * Metodo para fazer login na conta de email
     *
     * @param emailEnvio email de quem envia
     * @param passwordEnvio password de quem envia
     * @param props propriedades do servidor para mandar email
     * @return sessao
     */
    private static Session loginEmail(String emailEnvio, String passwordEnvio, Properties props) {
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailEnvio, passwordEnvio);
            }
        });
        return session;
    }

    /**
     * Metodo para preparar a mensagem para enviar
     *
     * @param emailEnvio email de quem envia
     * @param ses sessao login
     * @param sender nome de quem envia
     * @param alvo email do destinatario
     * @param assunto assunto do email
     * @param texto corpo do email
     * @return Message
     * @throws Exception se acontecer algum erro a escrever a mensagem
     */
    private static Message prepararMensagem(String emailEnvio, Session ses, String sender, String alvo, String assunto, String texto) throws Exception {
        Message mensagem = new MimeMessage(ses);
        mensagem.setFrom(new InternetAddress(emailEnvio, sender));
        mensagem.setRecipient(Message.RecipientType.TO, new InternetAddress(alvo));
        mensagem.setSubject(assunto);
        mensagem.setText(texto);
        return mensagem;
    }

}
