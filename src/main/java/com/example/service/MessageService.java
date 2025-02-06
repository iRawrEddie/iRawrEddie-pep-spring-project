package com.example.service;

import java.util.List;
import com.example.entity.Message;
import org.springframework.stereotype.Service;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    
    /**
     * Retrieve all messages  
     * @return Empty or Non-empty list of Messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    /**
     * Get message by id
     * @param messageId Id of the message to retrieve from database.
     * @return The found Message or null.
     */
    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }
    
    /**
     * Retrieve all messages by user id.
     * @param postedBy accountId to search Message postedBy.
     * @return Empty or Non-empty list of Messages.
     */
    public List<Message> getMessagesByAccount(int postedBy) {
        return messageRepository.findMessagesByPostedBy(postedBy);
    }
    
    /**
     * Delete a Message by its id.
     * @param messageId id of message to delete.
     */
    public void deleteMessageById(int messageId){
        messageRepository.deleteById(messageId);
    }
    
    /**
     * Persist a Message to database.
     * @param message Message to save to database.
     * @return Newly saved message or null if invalid message text.
     */
    public Message persistMessage(Message message) {
        if (message.getMessageText().equals("") ||
            message.getMessageText().length() > 255)
            return null;
        return messageRepository.save(message);
    }
    
    /**
     * Update a message by its id.
     * @param messageId Id of the message to update.
     * @param messageText New text value to update the message to.
     * @return Rows updated 1 or 0.
     */
    public Message updateMessageTextById(int messageId, String messageText) {
        Message exMessage = getMessageById(messageId);
        //perform update
        exMessage.setMessageText(messageText);
        //persist update
        return persistMessage(exMessage);
    }
}

