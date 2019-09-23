package telegram.api;

import java.text.SimpleDateFormat;

public class TelegramAPI {
        
    public static void main(String[] args) throws InterruptedException {
        final String apiKey = "958067215:AAE615rc_IJptY4AaOM9f-TnMVNGYukPskE"; // COLOCAR EM UM JSON E PUXAR DELE
        int updateId;
        String date;
        
        SimpleDateFormat formatoData;
        
        TelegramAPIResult result = new TelegramAPIResult();
        
        TelegramBot bot = new TelegramBot();
        bot.setApiKey(apiKey);
        
        updateId = 1;
        
        while (true) {
                
            if (bot.Receive(updateId, result) == 1) {
                updateId = result.getUpdate_id();

                formatoData = new SimpleDateFormat("dd-MM-yyyy");
                date = formatoData.format(result.getResult().getDate());

                System.out.println("Update id: " + String.valueOf(result.getUpdate_id()));
                System.out.println("From: " + result.getResult().getFrom().getFirst_name() + " " + result.getResult().getFrom().getLast_name());
                System.out.println("Chat: " + result.getResult().getChat().getType());
                System.out.println("Date: " + date);
                System.out.println("Text: " + result.getResult().getText());
                System.out.println("\n ---------- * ---------- \n");
                
                System.out.println(bot.interpretaTexto(result.getResult().getText(), result));
                
                /*if (bot.interpretaTexto(result.getResult().getText(), result) == 1) {
                
                    date = formatoData.format(result.getResult().getDate());

                    System.out.println("Update id: " + String.valueOf(result.getUpdate_id()));
                    System.out.println("From: " + result.getResult().getFrom().getFirst_name());
                    System.out.println("Chat: " + result.getResult().getChat().getType());
                    System.out.println("Date: " + date);
                    System.out.println("Text: " + result.getResult().getText());
                    System.out.println("\n ---------- * ---------- \n");
                    
                }*/

                Thread.sleep(1000);
            }
            
        }
        
    }
    
}
