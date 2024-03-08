package p.zestianstaff.Command;
import com.google.inject.Inject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;


import com.velocitypowered.api.proxy.Player;
import p.zestianstaff.database.SQLManager;
import p.zestianstaff.manager.StaffModeManager;




public class StaffModeCommand {

    private final SQLManager sqlManager;

    public StaffModeCommand(CommandManager commandManager, StaffModeManager staffModeManager, SQLManager sqlManager) {
        this.sqlManager = sqlManager;
        var command = LiteralArgumentBuilder.<CommandSource>literal("staffmode")
                .requires(src -> src.hasPermission("zestianstaff.command.staffmode"))
                .then(LiteralArgumentBuilder.<CommandSource>literal("on")
                        .executes(cmd -> {
                            staffModeManager.activateStaffMode((Player) cmd.getSource());
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(LiteralArgumentBuilder.<CommandSource>literal("off")
                        .executes(cmd -> {
                            staffModeManager.deactivateStaffMode((Player) cmd.getSource());
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();

        BrigadierCommand brigadier = new BrigadierCommand(command);
        commandManager.register(brigadier);
    }

}












