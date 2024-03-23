package cn.ksmcbrigade.cllb.mixin;

import cn.ksmcbrigade.cllb.ChunkLoadingLimitBreached;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.function.Consumer;

@Mixin(SimpleOption.class)
public abstract class OptionsMixin2<T>{

    @Mutable
    @Shadow @Final private SimpleOption.Callbacks<T> callbacks;

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/client/option/SimpleOption$TooltipFactory;Lnet/minecraft/client/option/SimpleOption$ValueTextGetter;Lnet/minecraft/client/option/SimpleOption$Callbacks;Ljava/lang/Object;Ljava/util/function/Consumer;)V",at = @At("TAIL"))
    public void init(String key, SimpleOption.TooltipFactory<T> tooltipFactory, SimpleOption.ValueTextGetter<T> valueTextGetter, SimpleOption.Callbacks<T> callbacks, Object defaultValue, Consumer<T> changeCallback, CallbackInfo ci) throws IOException {
        if(!ChunkLoadingLimitBreached.init){
            ChunkLoadingLimitBreached.init();
        }
        if(key.contains("renderDistance") || key.contains("options.simulationDistance")){
            if(callbacks instanceof SimpleOption.ValidatingIntSliderCallbacks call){
                this.callbacks = (SimpleOption.Callbacks<T>) new SimpleOption.ValidatingIntSliderCallbacks(call.minInclusive(), ChunkLoadingLimitBreached.max);
            }
        }
    }
}
