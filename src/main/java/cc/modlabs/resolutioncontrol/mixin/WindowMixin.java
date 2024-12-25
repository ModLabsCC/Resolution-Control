package cc.modlabs.resolutioncontrol.mixin;

import cc.modlabs.resolutioncontrol.ResolutionControlMod;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Window.class)
public abstract class WindowMixin {
	@Inject(at = @At("RETURN"), method = "getFramebufferWidth", cancellable = true)
	private void getFramebufferWidth(CallbackInfoReturnable<Integer> ci) {
		if (ResolutionControlMod.getInstance().isScreenshotting()) {
			ci.setReturnValue(ResolutionControlMod.getInstance().getScreenshotWidth());
		} else {
			var value = scale(ci.getReturnValueI());
			ci.setReturnValue(value);
		}
	}
	
	@Inject(at = @At("RETURN"), method = "getFramebufferHeight", cancellable = true)
	private void getFramebufferHeight(CallbackInfoReturnable<Integer> ci) {
		if (ResolutionControlMod.getInstance().isScreenshotting()) {
			ci.setReturnValue(ResolutionControlMod.getInstance().getScreenshotHeight());
		} else {
			var value = scale(ci.getReturnValueI());
			ci.setReturnValue(value);
		}
	}
	
	private int scale(int value) {
		double scaleFactor = ResolutionControlMod.getInstance().getCurrentScaleFactor();
		return Math.max(MathHelper.ceil(((double) value) * scaleFactor), 1);
	}
	
	@Inject(at = @At("RETURN"), method = "getScaleFactor", cancellable = true)
	private void getScaleFactor(CallbackInfoReturnable<Double> ci) {
		ci.setReturnValue(ci.getReturnValueD() * ResolutionControlMod.getInstance().getCurrentScaleFactor());
	}
	
	@Inject(at = @At("RETURN"), method = "onFramebufferSizeChanged")
	private void onFramebufferSizeChanged(CallbackInfo ci) {
		ResolutionControlMod.getInstance().onResolutionChanged();
	}
	
	@Inject(at = @At("RETURN"), method = "updateFramebufferSize")
	private void onUpdateFramebufferSize(CallbackInfo ci) {
		ResolutionControlMod.getInstance().onResolutionChanged();
	}
}
