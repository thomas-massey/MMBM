package com.Leftmostchain21.MMBM.core.events;

import com.Leftmostchain21.MMBM.MMBM;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = MMBM.MODID, value = Side.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        // Check if it is a player
        if (event.getEntity().world.isRemote) {
            // Send a chat message
            TextComponentString message = new TextComponentString("You jumped!");
            event.getEntity().sendMessage(message);
        }
    }

    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event){
        // Check if it is a player
        TextComponentString message = new TextComponentString("I just broke a "+event.getState().getBlock().getLocalizedName()+" block!");
        event.getPlayer().sendMessage(message);
    }
}
