package Mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;


public class MailUtils {
    public class EmailAuthenticator extends javax.mail.Authenticator{
        private String login;
        private String password;

        public EmailAuthenticator (final String login, final String password){
            this.login = login;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(login, password);
        }
    }

    public Store connect() throws MessagingException {
        String IMAP_AUTH_EMAIL = "testloginitest@yandex.ru";
        String IMAP_AUTH_PWD = "testloginites";
        String IMAP_Server = "imap.yandex.ru";
        String IMAP_Port = "993";

        Properties properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", IMAP_Port);

        Authenticator auth = new EmailAuthenticator(IMAP_AUTH_EMAIL, IMAP_AUTH_PWD);
        Session session = Session.getDefaultInstance(properties, auth);
        session.setDebug(false);

        Store store = session.getStore();

        // Подключение к почтовому серверу
        store.connect(IMAP_Server, IMAP_AUTH_EMAIL, IMAP_AUTH_PWD);
        return store;
    }

    Message getFirstMessage(Store store) throws MessagingException, IOException {
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_WRITE);

        Message[] messages = emailFolder.getMessages();
        ArrayList<Message> recent = new ArrayList<>();
        Message firstMessage = null;

        /**
         * Проходим массив сообщений
         * Каждое сообщение с флагом RECENT
         * помещаем в лист recent
         */
        for (Message message : messages) {
            System.out.println();фыввasdfasdfasdf
            System.out.println("---------------------------------");
            if (message.isSet(Flags.Flag.RECENT)) {
                System.out.println("This one is RECENT");
                recent.add(message);
            }
        }

        /**
         * Проходим лист сообщений с флагами RECENT
         * Получаем только самое позднее
         */
        if (recent.size() != 0){
            System.out.println(recent.size());
            firstMessage = recent.get(recent.size() - 1);
            System.out.println("---------------------------------");
            System.out.println("Subject: " + firstMessage.getSubject());
            System.out.println("From: " + firstMessage.getFrom()[0]);
            System.out.println("Text: " + getTextFromMessage(firstMessage));
            System.out.println("---------------------------------");
        }else {
            System.out.println("There are no recent messages in the inbox folder.");
        }

        return firstMessage;
    }

    Message[] getAllMessages(Store store) throws MessagingException{
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_WRITE);

        Message[] allMessagesINBOX = emailFolder.getMessages();
        return allMessagesINBOX;
    }

    void clearMessages(Store store) throws MessagingException {
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_WRITE);
        Message[] messages = emailFolder.getMessages();

        for(Message message : messages){
            message.setFlag(Flags.Flag.DELETED, true);
            System.out.println("deleted a message");
        }

        System.out.println(emailFolder.getDeletedMessageCount());
        System.out.println("All messages have been deleted.");
        emailFolder.close(true);
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }
}


