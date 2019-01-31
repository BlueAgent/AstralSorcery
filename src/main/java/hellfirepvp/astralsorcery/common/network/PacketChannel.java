/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2018
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.network;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.ClientProxy;
import hellfirepvp.astralsorcery.common.network.packet.ClientReplyPacket;
import hellfirepvp.astralsorcery.common.network.packet.client.*;
import hellfirepvp.astralsorcery.common.network.packet.server.*;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: PacketChannel
 * Created by HellFirePvP
 * Date: 07.05.2016 / 01:11
 */
public class PacketChannel {

    public static final SimpleNetworkWrapper CHANNEL = new SimpleNetworkWrapper(AstralSorcery.NAME) {

        @Override
        public void sendToServer(IMessage message) {
            if(message instanceof ClientReplyPacket && !PacketChannel.canBeSent()) {
                return;
            }
            super.sendToServer(message);
        }
    };

    @SideOnly(Side.CLIENT)
    private static boolean canBeSent() {
        return ClientProxy.connected;
    }

    public static void init() {
        int id = 0;

        //(server -> client)
        CHANNEL.registerMessage(PktSyncKnowledge.class, PktSyncKnowledge.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktSyncData.class, PktSyncData.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktParticleEvent.class, PktParticleEvent.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktCraftingTableFix.class, PktCraftingTableFix.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktProgressionUpdate.class, PktProgressionUpdate.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktPlayEffect.class, PktPlayEffect.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktRequestSeed.class, PktRequestSeed.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktUnlockPerk.class, PktUnlockPerk.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktAttunementAltarState.class, PktAttunementAltarState.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktRotateTelescope.class, PktRotateTelescope.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktLightningEffect.class, PktLightningEffect.class, id++, Side.CLIENT);
        //CHANNEL.registerMessage(PktSyncMinetweakerChanges.class, PktSyncMinetweakerChanges.class, id++, Side.CLIENT);
        //CHANNEL.registerMessage(PktSyncMinetweakerChanges.Compound.class, PktSyncMinetweakerChanges.Compound.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktDualParticleEvent.class, PktDualParticleEvent.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktOreScan.class, PktOreScan.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktSyncCharge.class, PktSyncCharge.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktSyncStepAssist.class, PktSyncStepAssist.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktUpdateGateways.class, PktUpdateGateways.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktBurnParchment.class, PktBurnParchment.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktParticleDataEvent.class, PktParticleDataEvent.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktShootEntity.class, PktShootEntity.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktLiquidInteractionBurst.class, PktLiquidInteractionBurst.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktPlayLiquidSpring.class, PktPlayLiquidSpring.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktFinalizeLogin.class, PktFinalizeLogin.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktRequestSextantTarget.class, PktRequestSextantTarget.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktSyncPerkActivity.class, PktSyncPerkActivity.class, id++, Side.CLIENT);
        CHANNEL.registerMessage(PktRequestPerkSealAction.class, PktRequestPerkSealAction.class, id++, Side.CLIENT);

        //(client -> server)
        CHANNEL.registerMessage(PktDiscoverConstellation.class, PktDiscoverConstellation.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktRequestSeed.class, PktRequestSeed.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktUnlockPerk.class, PktUnlockPerk.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktAttunementAltarState.class, PktAttunementAltarState.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktAttuneConstellation.class, PktAttuneConstellation.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktRotateTelescope.class, PktRotateTelescope.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktRequestTeleport.class, PktRequestTeleport.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktBurnParchment.class, PktBurnParchment.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktEngraveGlass.class, PktEngraveGlass.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktElytraCapeState.class, PktElytraCapeState.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktClearBlockStorageStack.class, PktClearBlockStorageStack.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktSetSextantTarget.class, PktSetSextantTarget.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktRequestSextantTarget.class, PktRequestSextantTarget.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktRequestPerkSealAction.class, PktRequestPerkSealAction.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktRemoveKnowledgeFragment.class, PktRemoveKnowledgeFragment.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktPerkGemModification.class, PktPerkGemModification.class, id++, Side.SERVER);
        CHANNEL.registerMessage(PktPlayerStatus.class, PktPlayerStatus.class, id++, Side.SERVER);

        /*Method registerPacket = ReflectionHelper.findMethod(
                EnumConnectionState.class,
                EnumConnectionState.PLAY,
                new String[] { "registerPacket", "func_179245_a", "a" },
                EnumPacketDirection.class, Class.class);
        registerPacket.setAccessible(true);

        try {
            registerPacket.invoke(EnumConnectionState.HANDSHAKING, EnumPacketDirection.CLIENTBOUND, PktWorldHandlerSyncEarly.class);
        } catch (Exception e) {}*/
    }

    public static NetworkRegistry.TargetPoint pointFromPos(World world, Vec3i pos, double range) {
        return new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range);
    }

}
