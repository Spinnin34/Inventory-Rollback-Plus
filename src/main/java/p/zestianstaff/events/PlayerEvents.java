package p.zestianstaff.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import p.zestianstaff.ZestianStaff;
import p.zestianstaff.manager.StaffModeManager;
import com.google.inject.Inject;

public class PlayerEvents {
    private StaffModeManager staffModeManager;

    public PlayerEvents(ProxyServer proxy, ZestianStaff plugin,  StaffModeManager staffModeManager) {
        proxy.getEventManager().register(plugin, this);
        this.staffModeManager = staffModeManager;
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Player disconnectedPlayer = event.getPlayer();
        if(disconnectedPlayer.hasPermission("staffmode.commands")){
            staffModeManager.deactivateStaffMode(disconnectedPlayer);
        }
    }
}
