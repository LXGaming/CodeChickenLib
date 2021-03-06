package codechicken.lib.internal.optifine.mixin;

import codechicken.lib.internal.optifine.OptiFineGameSettingsBridge;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.settings.IteratableOption;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin (targets = "net.optifine.gui.GuiPerformanceSettingsOF", remap = false)
public abstract class GuiPerformanceSettingsOFMixin extends Screen {

    public GuiPerformanceSettingsOFMixin(ITextComponent titleIn) {
        super(titleIn);
    }

    @SuppressWarnings ("UnresolvedMixinReference")
    @Inject (
            method = "actionPerformed",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onActionPerformed(Widget widget, CallbackInfo callbackInfo) {
        if (!(widget instanceof OptionButton)) {
            return;
        }

        OptionButton button = (OptionButton) widget;
        if (!(button.func_238517_a_() instanceof IteratableOption)) {
            return;
        }

        IteratableOption option = (IteratableOption) button.func_238517_a_();
        if (!(option.getBaseMessageTranslation() instanceof TranslationTextComponent)) {
            return;
        }

        TranslationTextComponent translation = (TranslationTextComponent) option.getBaseMessageTranslation();
        if (!translation.getKey().equals("of.options.FAST_RENDER")) {
            return;
        }

        GameSettings gameSettings = Minecraft.getInstance().gameSettings;
        if (!(gameSettings instanceof OptiFineGameSettingsBridge) || !((OptiFineGameSettingsBridge) gameSettings).bridge$isFastRender()) {
            return;
        }

        Minecraft.getInstance().displayGuiScreen(new ConfirmScreen(callback -> {
            ((OptiFineGameSettingsBridge) gameSettings).bridge$setFastRender(callback);
            Minecraft.getInstance().displayGuiScreen(this);
        }, new TranslationTextComponent("ccl.optifine.confirm.title"), new TranslationTextComponent("ccl.optifine.confirm.description")));
    }
}