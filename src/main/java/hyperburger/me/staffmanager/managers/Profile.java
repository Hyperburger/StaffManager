package hyperburger.me.staffmanager.managers;

import lombok.Getter;
import lombok.Setter;

public class Profile {


    @Getter
    @Setter
    private boolean staffChat;

    @Getter
    @Setter
    private boolean closeInventory;

    @Getter
    @Setter
    private boolean frozen;

    public Profile(){
        this.staffChat = false;
        this.closeInventory = false;
        this.frozen = false;
    }
}
