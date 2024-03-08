package p.zestianstaff.Command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import p.zestianstaff.ZestianStaff;
import p.zestianstaff.manager.StaffModeManager;

import java.util.List;

public class StaffListCommand implements SimpleCommand {

    private final ZestianStaff plugin;
    private final ProxyServer proxy;

    private final StaffModeManager staffModeManager;

    public StaffListCommand(ZestianStaff plugin, ProxyServer proxy, StaffModeManager staffModeManager) {
        this.plugin = plugin;
        this.proxy = proxy;
        this.staffModeManager = staffModeManager;

        proxy.getCommandManager().register("stafflist", this);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        if (!commandSource.hasPermission("zestianstaff.list")) {
            commandSource.sendMessage(Component.text("§cNo tienes permisos para ejecutar este comando."));
            return;
        }

        List<String> messages = new java.util.ArrayList<>(List.of());

        for (Player onlinePlayer : this.proxy.getAllPlayers()) {
            if (onlinePlayer.hasPermission("zestian.staff.listed")) {
                String serverName = onlinePlayer.getCurrentServer().isPresent() ? onlinePlayer.getCurrentServer().get().getServerInfo().getName() : "null";
                String activeMessage = staffModeManager.isStaffModeActive(onlinePlayer.getUniqueId()) ? "&a✔" : "&c✖";
                String message = "&8»" +"&f " + staffModeManager.getPlayerPrefix(onlinePlayer.getUniqueId()) + "&f " + onlinePlayer.getUsername() + " &e(" + serverName + ") " + activeMessage;
                messages.add(MiniMessage.miniMessage().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(message)));
            }
        }

        commandSource.sendMessage(Component.text("§b§lSTAFFS:"));
        for (String message : messages) {
            commandSource.sendMessage(MiniMessage.miniMessage().deserialize(message));
        }

    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("zestianstaff.list");
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of();
    }
}


