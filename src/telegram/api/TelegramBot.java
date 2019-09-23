// Kelvin  I. Seibt - 20/09/2019 :: Criação

package telegram.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class TelegramBot {
    
    final private String BASEURL = "https://api.telegram.org/bot";
    
    private String apiKey;
    private String fullURL;
    
    /**
     * Constrói um <code>TelegramBot</code> que intermedeia
     * a comunicação entre a aplicação e a API Telegram Bot.
     * @version 1.0
    */
    public TelegramBot(){
        this.apiKey = "";
        this.fullURL = "";
    }
    
    /** Retorna o token de acesso aos métodos da API
     * Telegram Bot.
     * @return O token de acesso registrado neste objeto.
     * @version 1.0
    */
    public String getApiKey() {
        
        return apiKey;
        
    }

    /** Designar ou editar o token de acesso aos métodos
     * da API Telegram Bot.
     * @param apiKey token de acesso.
     * @version 1.0
    */
    public void setApiKey(String apiKey) {
        
        this.apiKey = apiKey;
        
        this.fullURL = BASEURL + this.apiKey;
        
    }
    
    
    public int Send(int user, String message, TelegramAPIResult response) {
        
        String lineBuffer, bufferJSON = "", formatedMessage = "";
        JSONObject responseJSON, messageJSON, chatJSON, userJSON;
        
        TelegramAPIUser t_bot;
        TelegramAPIChat t_chat;
        TelegramAPIMessage t_message;
        
        if (fullURL.equals("")) {
            
            return 2;
            
        }
       
        try {
            formatedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String requestURL = String.format("%s/sendMessage?chat_id=%d&text=%s", fullURL, user, formatedMessage);
        
        try {
            URL request = new URL(requestURL); //instancia uma url
            URLConnection requestReturn = request.openConnection(); //abre a URL que é a mensagem a ser enviada ao usuário
            BufferedReader requestReader = new BufferedReader (new InputStreamReader(requestReturn.getInputStream()));
            
            while ((lineBuffer = requestReader.readLine()) != null) {
             
                bufferJSON += lineBuffer;
               
            }
            
            responseJSON = new JSONObject(bufferJSON);
            
            if (!responseJSON.getBoolean("ok")) {
  
                return 4;
                
            }
            
            if (responseJSON.getJSONObject("result") != null) {
            
                return 2;
                
            }
        } 
        catch (Exception e){
            
            return 3;
            
        }
        
        messageJSON = responseJSON.getJSONObject("result");
        userJSON    = messageJSON.getJSONObject("from");
        chatJSON    = messageJSON.getJSONObject("chat");
        
        t_bot = new TelegramAPIUser(userJSON.getInt("id"), 
                                    userJSON.getBoolean("is_bot"), 
                                    userJSON.getString("first_name"), 
                                    userJSON.getString("username"));
        
        t_chat = new TelegramAPIChat(chatJSON.getInt("id"), 
                                     chatJSON.getString("first_name"), 
                                     chatJSON.getString("last_name"), 
                                     chatJSON.getString("type"));
        
        t_message = new TelegramAPIMessage(messageJSON.getInt("message_id"),
                                           t_bot, 
                                           t_chat, 
                                           messageJSON.getInt("date"),
                                           messageJSON.getString("text"));
        
        response.setUpdate_id(responseJSON.getJSONArray("result").getJSONObject(0).getInt("update_id"));
        response.setResult(t_message);
        
        return 1;
        
    }
    
    /** Receber próximo pedido de atualização.
     * @param updateId id da ultima atualização.
     * @return Valor inteiro:
     * <ul>
     *   <li>1: Conluído.</li>
     *   <li>2: Conluído (sem resultados).</li>
     *   <li>3: Cancelado (não há nenhum token definido).</li>
     *   <li>4: Erro ao realizar a requisição.</li>
     * </ul>
     * @version 1.0
    */
    public int Receive(int updateId, TelegramAPIResult response) {
        
        String lineBuffer, bufferJSON = "";
        JSONObject responseJSON, messageJSON, chatJSON, userJSON;
        
        TelegramAPIUser t_user;
        TelegramAPIChat t_chat;
        TelegramAPIMessage t_message;
        
        if (fullURL.equals("")) {
            
            return 3;
            
        }
    
        String requestURL = String.format("%s/getUpdates?offset=%d", fullURL, updateId + 1);
        
        try{
            
            URL request = new URL(requestURL);
            
            URLConnection requestReturn = request.openConnection();
            
            BufferedReader requestReader = new BufferedReader(new InputStreamReader(requestReturn.getInputStream()));

            while ((lineBuffer = requestReader.readLine()) != null) {
             
                bufferJSON += lineBuffer;
               
            }
            
            responseJSON = new JSONObject(bufferJSON);
            
            if (!responseJSON.getBoolean("ok")) {
  
                return 4;
                
            }
            
            if (responseJSON.getJSONArray("result").length() < 1) {
            
                return 2;
                
            }
            
        }catch (Exception e){
            
            return 4;
            
        }
        
        messageJSON = responseJSON.getJSONArray("result").getJSONObject(0).getJSONObject("message");
        userJSON    = messageJSON.getJSONObject("from");
        chatJSON    = messageJSON.getJSONObject("chat");
        
        t_user = new TelegramAPIUser(userJSON.getInt("id"), 
                                     userJSON.getBoolean("is_bot"), 
                                     userJSON.getString("first_name"), 
                                     userJSON.getString("last_name"), 
                                     userJSON.getString("language_code"));
        
        t_chat = new TelegramAPIChat(chatJSON.getInt("id"), 
                                     chatJSON.getString("first_name"), 
                                     chatJSON.getString("last_name"), 
                                     chatJSON.getString("type"));
        
        t_message = new TelegramAPIMessage(messageJSON.getInt("message_id"),
                                           t_user, 
                                           t_chat, 
                                           messageJSON.getInt("date"),
                                           messageJSON.getString("text"));
        
        response.setUpdate_id(responseJSON.getJSONArray("result").getJSONObject(0).getInt("update_id"));
        response.setResult(t_message);
        
        return 1;
        
    }
    
    public int interpretaTexto(String texto, TelegramAPIResult response) {
        
        String text;
        
        if (texto.equals("/start")) {
        
            text = TelegramAPIConst.START;
            
        }else {
        
            text = TelegramAPIConst.START_AGAIN;
            
        }
        
        return Send(response.getResult().getFrom().getId(), text, response);
        
    }
    
}
