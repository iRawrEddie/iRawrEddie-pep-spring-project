package com.example.controller;

import java.util.List;
import com.example.entity.*;
import com.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;
    
    /**
     * Post a new account to /register.
     * @param account Account of the JSON body from request.
     * @return Status 200 and Account if successful, 400 if failure, 409 if duplicate user.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        try
        {
            account = accountService.persistAccount(account);
            if (account != null) return ResponseEntity.status(HttpStatus.OK).body(account);
        }
        catch (DataIntegrityViolationException ex) {
            System.out.println("[ERROR] Duplicate: User already exists. " + ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(account);
        }
        catch (Exception ex)
        {
            System.out.println("[ERROR] Exception thrown creating new account: "+ ex);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(account);
    }
    
    /**
     * POST an Account to /login.
     * @param account Account of the JSON body from request.
     * @return 200 if successful and the Account, 400 if login fails.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        account = accountService.getAccount(account);
        if (account == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
    
    /**
     * Post a message to /messages.
     * @param message Message of the JSON body from request.
     * @return 200 if successful, 400 if POST failed.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> postNewMessage(@RequestBody Message message) {
        try
        {
            message = messageService.persistMessage(message);
            if (message != null) return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        catch (Exception ex)
        {
            System.out.println("[ERROR] Exception thrown creating new message: "+ ex);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    
    /**
     * GET all messages at /messages.
     * @return 200 status and a list of messages if they exist.
     */
    @GetMapping("/messages") 
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }
    
    /**
     * GET a message at /messages/{messageId}
     * @param messageId Id of the message to retrieve.
     * @return 200 and the message if it exists.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageById(messageId));
    }
    
    /**
     * DELETE a message at /messages/{messageId}
     * @param messageId Id of the message to delete
     * @return 200 and 1 if any rows were updated.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        try
        {
            messageService.deleteMessageById(messageId);
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        catch (Exception ex)
        {
            System.out.println("[ERROR] Exception thrown deleting message with Id: " + messageId + "\n" + ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
    /**
     * PATCH a message at /messages/{messageId}
     * @param messageId Id of the Message to be updated.
     * @param body Message of the JSON body from request.
     * @return 200 if message updated successfully, 400 if update failed.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable int messageId, @RequestBody Message body) {
        try
        {
            if (messageService.updateMessageTextById(messageId, body.getMessageText()) != null)
                return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        catch (Exception ex)
        {
            System.out.println("[ERROR] Exception thrown updating message with Id: " + messageId + "\n" + ex);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
    /**
     * GET messages for an account at /accounts/{accountId}/messages
     * @param accountId Id of the account to retrieve messages for.
     * @return 200 and a list of messages if they exist.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable int accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesByAccount(accountId));
    }
}
