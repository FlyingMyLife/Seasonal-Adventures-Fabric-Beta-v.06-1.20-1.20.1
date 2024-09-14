package net.packages.flying_machines.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.packages.flying_machines.gui.LockpickScreen;
import net.packages.flying_machines.gui.LockpickScreenHandler;
import net.packages.flying_machines.item.Items;

public class DebugLockCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("debuglock")
                        .then(CommandManager.argument("lockLevel", IntegerArgumentType.integer(0, 4))
                                .executes(context -> executeCommand(context, IntegerArgumentType.getInteger(context, "lockLevel"))))
        );
    }

    private static int executeCommand(CommandContext<ServerCommandSource> context, int lockLevel) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> {
            if (client.player.getInventory().contains(new ItemStack(Items.LOCKPICK))) {
                PlayerInventory inventory = client.player.getInventory();
                Text title = Text.literal("Lockpick Screen");


                client.setScreen(new LockpickScreen(new LockpickScreenHandler(0, inventory), inventory, title, lockLevel));
            } else {
                client.player.sendMessage(Text.translatable("message.lock.fail.not_enough_lockpicks").formatted(Formatting.DARK_RED), true);
            }
        });
        return Command.SINGLE_SUCCESS;
    }
}

