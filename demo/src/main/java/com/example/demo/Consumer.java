package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class Consumer {
    private Map<String, Integer> securities = new HashMap<>();

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "INQ")
    public void receiveMessage(String message) {
        String[] messages = message.split(" ");
        String action = messages[0];
        String out;

        switch (action) {
            case "BUY":
                out = buy(messages[1], Integer.parseInt(messages[2]));
                System.out.println("BUY message received successfully with security " + messages[1] + " and amount " + Integer.parseInt(messages[2]));
                break;
            case "SELL":
                out = sell(messages[1], Integer.parseInt(messages[2]));
                System.out.println("SELL message received successfully with security " + messages[1] + " and amount " + Integer.parseInt(messages[2]));
                break;
            case "ADD":
                out = add(messages[1]);
                System.out.println("ADD message received successfully with security " + messages[1]);
                break;
            case "PORTFOLIO":
                out = portfolio();
                System.out.println("PORTFOLIO message received successfully");
                break;
            default:
                out = "1 Invalid action";
                System.out.println("Invalid action");
        }

        sendMessage("OUTQ", out);
    }

    public void sendMessage(String queue, String out) {
        jmsTemplate.convertAndSend(queue, out);
        System.out.println("Message sent successfully");
    }

    public String buy(String security, int amount) {
        if (!securityExists(security))
            return "1 Unknown security";

        int old_amount = securities.get(security);

        securities.replace(security, old_amount + amount);

        return "0 Trade successful";
    }

    public String sell(String security, int amount) {
        if (!securityExists(security))
            return "1 Unknown security";

        int old_amount = securities.get(security);

        if (amount > old_amount)
            return "2 Not enough positions";

        securities.replace(security, old_amount - amount);

        return "0 Trade successful";
    }

    public String add(String security) {
        securities.put(security, 0);

        return "0 Success";
    }

    public String portfolio() {
        int start = 1;

        String ans = "0 ";
        for (Map.Entry<String, Integer> map : securities.entrySet()) {
            if (start == 0)
                ans += " | ";

            ans += map.getKey() + " " + map.getValue();
            start = 0;
        }

        return ans;
    }

    public boolean securityExists(String security) {
        for (Map.Entry<String, Integer> map : securities.entrySet())
            if (map.getKey().equals(security))
                return true;

        return false;
    }
}