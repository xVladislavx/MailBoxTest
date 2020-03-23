package Mail;

import com.sun.mail.pop3.POP3Store;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;

public class ReceiveMailJavaMailAPI_TEST {

    void readNewMessages(POP3Store emailStore) throws MessagingException, IOException {
        //receiveEmail();
        System.out.println("  Used <readNewMessages> method  ");
        //3) create the folder object and open it
        Folder emailFolder = emailStore.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        //4) retrieve the messages from the folder in an array and print it
        Message[] messages = emailFolder.getMessages();
        ArrayList<Message> newMessages = new ArrayList<Message>();

        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];

            //Получение всех сообщений с флагом НЕ ПРОЧИТАНО
            if(!message.isSet(Flags.Flag.SEEN)){
                newMessages.add(message);
            }
        }

/*        for(int i = 0; i < messages.length; i++){
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Text: " + message.getContent().toString());
            System.out.println("---------------------------------");
        }*/


        System.out.println(emailFolder.getUnreadMessageCount());
        //5) close the store and folder objects
        emailFolder.close(false);
        emailStore.close();
    }
    void cleanMail(){

    }
    void getRecentMessage(POP3Store emailStore) throws MessagingException{
        //3) create the folder object and open it
        Folder emailFolder = emailStore.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        Message[] messages = emailFolder.getMessages();
        ArrayList<Message> newMessages = new ArrayList<Message>();
        //Либо сообщение с флагом RECENT, либо самое последнее сообщение
        try{
            for(Message message : messages){
                if(!message.isSet(Flags.Flag.RECENT)){
                    System.out.println("Добавил в список сообщений самое последнее");
                    newMessages.add(message);
                } else {
                    System.out.println("Добавил в список сообщений письмо с флагом RECENT");
                    newMessages.add(message);
                }
            }
        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }
}
