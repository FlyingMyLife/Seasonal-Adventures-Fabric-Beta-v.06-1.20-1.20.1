package net.packages.seasonal_adventures.mixin;

import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.packages.seasonal_adventures.util.AnimatedPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements AnimatedPlayer {

    @Unique
    private final ModifierLayer<IAnimation> seasonalAdventuresAnimationContainer = new ModifierLayer<>();

    /**
     * Add the animation registration to the end of the constructor
     * Or you can use {@link dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess#REGISTER_ANIMATION_EVENT} event for this
     */
    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(ClientWorld world, GameProfile profile, CallbackInfo ci) {
        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object)this).addAnimLayer(100, seasonalAdventuresAnimationContainer);
    }

    @Override
    public ModifierLayer<IAnimation> seasonalAdventuresGetModAnimation() {
        return seasonalAdventuresAnimationContainer;
    }
}