package net.hecco.heccolib.mixin;

import com.mojang.authlib.GameProfile;
import net.hecco.heccolib.HeccoLib;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {

    @Unique
    private static final Map<String, String> heccolib$CAPES = Map.ofEntries(
            Map.entry("bc56b2c8-9ef8-4532-b045-00f44804bca4", "heccolib"), //TheHecco
            Map.entry("1cedf927-5c8f-4650-95e9-808fc8f94d00", "bountifulfares"), //Yirmiri
            Map.entry("774e37fc-1ca4-4156-827e-661afa24cb56", "bountifulfares"), //_Artyrian
            Map.entry("d1dac9fe-3ef0-4ea8-997b-b7cdd6a92131", "bountifulfares"), //tellio_ari
            Map.entry("32290fa8-77ed-4794-9cba-25c09e7f4e1d", "bountifulfares"), //Diemond_Player
            Map.entry("385f22c1-4661-4982-b024-80996b0edbc5", "bountifulfares"), //WorkGoblin
            Map.entry("9778ff53-d83d-4233-8fa6-8aab7b89c4c0", "bountifulfares") //Stellari_
    );

    public AbstractClientPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Shadow
    @Nullable
    protected PlayerInfo getPlayerInfo() {return null;}

    @Inject(method = "getSkin", at = @At(value = "HEAD"), cancellable = true)
    public void heccolib$addSupporterCapes(CallbackInfoReturnable<PlayerSkin> cir) {
        if (heccolib$CAPES.containsKey(stringUUID)) {
            PlayerInfo playerinfo = this.getPlayerInfo();
            PlayerSkin skin = DefaultPlayerSkin.get(this.getUUID());
            if (playerinfo != null) {
                skin = playerinfo.getSkin();
            }
            ResourceLocation texture = skin.capeTexture();
            if (texture == null) {
                texture = ResourceLocation.fromNamespaceAndPath(HeccoLib.MOD_ID, "textures/capes/" + heccolib$CAPES.get(stringUUID) + ".png");
            }
            cir.setReturnValue(new PlayerSkin(skin.texture(), skin.textureUrl(), texture, texture, skin.model(), skin.secure()));
        }
    }
}
