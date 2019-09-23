// Kelvin  I. Seibt - 20/09/2019 :: Criação

package telegram.api;

import java.text.SimpleDateFormat;


public class TelegramAPIResult {

    private int update_id;
    private TelegramAPIMessage result;

    public TelegramAPIResult() {
    }

    public int getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(int update_id) {
        this.update_id = update_id;
    }
    
    public TelegramAPIMessage getResult() {
        return result;
    }

    public void setResult(TelegramAPIMessage result) {
        this.result = result;
    }
    
}
