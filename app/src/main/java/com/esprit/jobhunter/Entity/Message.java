package com.esprit.jobhunter.Entity;

public class Message {

    private int id;
    private int sender;
    private int receiver;
    private String content;
    private String sent_at;
    private String sender_name;
    private String receiver_name;
    private int test;
    private String nickname;
    private String message ;

    public  Message(){
    }

    public Message(int id, int sender, int receiver, String content, String sent_at, String sender_name, String receiver_name, String nickname, String message) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sent_at = sent_at;
        this.sender_name = sender_name;
        this.receiver_name = receiver_name;
        this.nickname = nickname;
        this.message = message;
    }

    public Message(String nickname, String message) {
        this.nickname = nickname;
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSent_at() {
        return sent_at;
    }

    public void setSent_at(String sent_at) {
        this.sent_at = sent_at;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", sent_at=" + sent_at +
                ", sender_name='" + sender_name + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", test=" + test +
                ", nickname='" + nickname + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
