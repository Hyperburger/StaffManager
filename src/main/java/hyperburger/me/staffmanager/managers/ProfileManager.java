package hyperburger.me.staffmanager.managers;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class ProfileManager {


    @Getter
    private final HashMap<UUID, Profile> profileHashMap;
    public ProfileManager(){
        profileHashMap = new HashMap<>();
    }

    public boolean exists(UUID uuid){
        return profileHashMap.containsKey(uuid);
    }
}
