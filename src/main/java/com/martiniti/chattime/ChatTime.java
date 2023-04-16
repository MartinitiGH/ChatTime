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

@Mod("chattime")
@Mod.EventBusSubscriber
public class ChatTime {

    private static ForgeConfigSpec.IntValue timeColor;

    public ChatTime() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        timeColor = builder.comment("Colors of the time in chat:\n"+"Black - 0;\n"+"nDark Blue - 1;\n"+"Dark Green - 2;\n"+"Dark Aqua - 3;\n"+"Dark Red - 4;\n"+"Dark Purple - 5;\n"+"Gold - 6;\n"+"Gray - 7;\n"+"Dark Gray - 8;\n"+"Blue - 9;\n"+"Green - 10;\n"+"Aqua - 11;\n"+"Red - 12;\n"+"Light Purple - 13;\n"+"Yellow - 14;\n"+"White - 15;")
                .defineInRange("timeColor", 4, 0, 15); // 4 is red
        ModLoadingContext.get().registerConfig(Type.CLIENT, builder.build(), "chattime.toml");
    }

    @SubscribeEvent
    public static void onChatReceived(ClientChatReceivedEvent event) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeString = "[" + TextFormatting.values()[timeColor.get()] + currentTime.format(formatter) + TextFormatting.RESET + "] ";

        ITextComponent message = event.getMessage();
        StringTextComponent newMessage = new StringTextComponent(timeString);
        newMessage.appendSibling(message);

        event.setMessage(newMessage);
    }
}