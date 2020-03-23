package Mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws MessagingException, IOException {
        MailUtils mailUtils = new MailUtils();

        Store store = mailUtils.connect();
        mailUtils.getFirstMessage(store);
        //Массив писем
        //Message[] allMessagesINBOX = mailUtils.getAllMessages(store);

/*        for(int i = 0; i < allMessagesINBOX.length; i++){
            Message message = allMessagesINBOX[i];
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Text: " + message.getContent().toString());
            System.out.println("---------------------------------");
        }*/


        //Самое последнее письмо
/*        mailUtils.getFirstMessage(email);

        Message message = mailUtils.newestMessage;

        System.out.println(mailUtils.newestMessage);

        System.out.println("---------------------------------");
        System.out.println("Subject: " + message.getSubject());
        System.out.println("From: " + message.getFrom()[0]);
        System.out.println("Text: " + message.toString());
        System.out.println("---------------------------------");*/

        //mailUtils.clearMessages(store);

        //System.out.println("I DELETED EVERYTHING");
    }

}
