package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {

    WhatsappRepository wr=new WhatsappRepository();
    public String createUser(String name, String mobile) throws Exception {

        return wr.newUSer(name,mobile);
    }

    public Group createGroup(List<User> users) {
        return wr.newGroup(users);
    }

    public int createMessage(String content) {
        return wr.newMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        return wr.sendMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        return wr.changeAdmin(approver,user,group);
    }

    public int removeUser(User user) {
        return wr.removeUser(user);
    }

    public String findMessage(Date start, Date end, int k) {
        return "SUCCESS";
    }
}
