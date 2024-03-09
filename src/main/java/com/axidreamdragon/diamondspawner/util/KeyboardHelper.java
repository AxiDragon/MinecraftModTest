package com.axidreamdragon.diamondspawner.util;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.Minecraft;

public class KeyboardHelper {
    private static final long WINDOW_ID = Minecraft.getInstance().getWindow().getWindow();

    public static boolean isHoldingShift() {
        return InputConstants.isKeyDown(WINDOW_ID, GLFW.GLFW_KEY_LEFT_SHIFT)
                || InputConstants.isKeyDown(WINDOW_ID, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    public static boolean isHoldingControl() {
        return InputConstants.isKeyDown(WINDOW_ID, GLFW.GLFW_KEY_LEFT_CONTROL)
                || InputConstants.isKeyDown(WINDOW_ID, GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    public static boolean isHoldingSpace() {
        return InputConstants.isKeyDown(WINDOW_ID, GLFW.GLFW_KEY_SPACE);
    }
}
