package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;

    private HashMap<Integer,Message> message;
    private HashMap<String,User> users;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.users=new HashMap<>();
        this.message=new HashMap<>();

        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String newUSer(String name, String mobile) throws Exception {
        if(userMobile.contains(mobile))
            throw new Exception("User already exists");

        userMobile.add(mobile);
        users.put(name,new User(name,mobile));

        return "SUCCESS";
    }

    public Group newGroup(List<User> gUsers) {
        // The list contains at least 2 users where the first user is the admin. A group has exactly one admin.
        // If there are only 2 users, the group is a personal chat and the group name should be kept as the name of the second user(other than admin)
        // If there are 2+ users, the name of group should be "Group count". For example, the name of first group would be "Group 1", second would be "Group 2" and so on.
        // Note that a personal chat is not considered a group and the count is not updated for personal chats.
        // If group is successfully created, return group.

        //For example: Consider userList1 = {Alex, Bob, Charlie}, userList2 = {Dan, Evan}, userList3 = {Felix, Graham, Hugh}.
        //If createGroup is called for these userLists in the same order, their group names would be "Group 1", "Evan", and "Group 2" respectively.

        int size=gUsers.size();

        if(size == 2){
            Group personal=new Group(gUsers.get(1).getName(),size);
            groupUserMap.put(personal,gUsers);
            adminMap.put(personal,gUsers.get(0));
            return personal;
        }

        customGroupCount++;
        Group group=new Group("Group "+(customGroupCount),size);
        adminMap.put(group,gUsers.get(0));
        groupUserMap.put(group,gUsers);
        return group;


    }

    public int newMessage(String content) {
        // The 'i^th' created message has message id 'i'.
        // Return the message id.
        messageId++;


        message.put(messageId,new Message(messageId,content));

        return messageId;

    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.
        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        List<User> userList=groupUserMap.get(group);
        if(!userList.contains(sender)){
            throw new Exception("You are not allowed to send message");
        }
       else if(!groupMessageMap.containsKey(group)){
            List<Message> messageList=new LinkedList<>();
            messageList.add(message);
            groupMessageMap.put(group,messageList);
        }
       else{
            groupMessageMap.get(group).add(message);

        }

       senderMap.put(message,sender);


       return groupMessageMap.get(group).size();



    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        User admin=adminMap.get(group);
        if(admin!=approver){
            throw new Exception("Approver does not have rights");
        }
        if(!groupUserMap.get(group).contains(user)){
            throw new Exception("User is not a participant");
        }


        adminMap.put(group,user);

        return "SUCCESS";

    }

    public int removeUser(User user) {
        //This is a bonus problem and does not contains any marks
        //A user belongs to exactly one group
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)
        return 0;
    }
}
