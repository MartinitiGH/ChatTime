package com.martiniti.chattime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraft.util.text.ChatType;

@Mod("chattime")
@Mod.EventBusSubscriber
public class Chattime {

    private static ForgeConfigSpec.IntValue timeColor;

    public Chattime() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        timeColor = builder.comment("Colors of the time in chat")
                .defineInRange("timeColor", 4, 0, 15); // 4 is red
        ModLoadingContext.get().registerConfig(Type.CLIENT, builder.build(), "chattime.toml");
    }

    @SubscribeEvent
    public static void onChatReceived(ClientChatReceivedEvent event) {
        ChatType chatType = event.getType();
        if (chatType == ChatType.CHAT || chatType == ChatType.SYSTEM) {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String timeString = "[" + TextFormatting.values()[timeColor.get()] + currentTime.format(formatter) + TextFormatting.RESET + "] ";

            ITextComponent message = event.getMessage();
            StringTextComponent newMessage = new StringTextComponent(timeString);
            newMessage.appendSibling(message);

            event.setMessage(newMessage);
        }
    }
}