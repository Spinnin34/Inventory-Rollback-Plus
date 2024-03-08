package p.zestianstaff.Command;
import com.google.inject.Inject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import p.zestianstaff.database.SQLManager;

import java.util.List;

public class StaffTopCommand {

    private final SQLManager sqlManager;

    public StaffTopCommand(SQLManager sqlManager, CommandManager commandManager) {
        this.sqlManager = sqlManager;
        final var command = LiteralArgumentBuilder.<CommandSource>literal("stafftop")
                .requires(src -> src.hasPermission("staffmode.commands"))
                .then(LiteralArgumentBuilder.<CommandSource>literal("time")
                        .executes(this::execute)
                )
                .build();

        final BrigadierCommand brigadier = new BrigadierCommand(command);
        commandManager.register(brigadier);

    }


    private int execute(CommandContext<CommandSource> context) {
        List<String> topStaffList = sqlManager.getTopStaff(10);

        if (topStaffList.isEmpty()) {
            context.getSource().sendMessage(Component.text("No hay datos disponibles para el top del personal.")
                    .color(NamedTextColor.RED));
            return Command.SINGLE_SUCCESS;
        }

        context.getSource().sendMessage(Component.text("Top del personal:")
                .color(NamedTextColor.GREEN));

        for (String entry : topStaffList) {
            context.getSource().sendMessage(Component.text(entry).color(NamedTextColor.YELLOW));
        }

        return Command.SINGLE_SUCCESS;
    }
}
