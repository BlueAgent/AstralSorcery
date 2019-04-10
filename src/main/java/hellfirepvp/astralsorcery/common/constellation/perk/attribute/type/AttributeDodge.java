/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2019
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.constellation.perk.attribute.type;

import hellfirepvp.astralsorcery.common.constellation.perk.PerkAttributeHelper;
import hellfirepvp.astralsorcery.common.constellation.perk.attribute.AttributeTypeRegistry;
import hellfirepvp.astralsorcery.common.constellation.perk.attribute.PerkAttributeModifier;
import hellfirepvp.astralsorcery.common.constellation.perk.attribute.PerkAttributeType;
import hellfirepvp.astralsorcery.common.constellation.perk.attribute.modifier.AttributeModifierDodge;
import hellfirepvp.astralsorcery.common.data.research.ResearchManager;
import hellfirepvp.astralsorcery.common.event.AttributeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: AttributeDodge
 * Created by HellFirePvP
 * Date: 14.07.2018 / 07:42
 */
public class AttributeDodge extends PerkAttributeType {

    public AttributeDodge() {
        super(AttributeTypeRegistry.ATTR_TYPE_INC_DODGE);
    }

    @Nonnull
    @Override
    public PerkAttributeModifier createModifier(float modifier, PerkAttributeModifier.Mode mode) {
        return new AttributeModifierDodge(getTypeString(), mode, modifier);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onDamageTaken(LivingDamageEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        Side side = player.world.isRemote ? Side.CLIENT : Side.SERVER;
        if (!hasTypeApplied(player, side)) {
            return;
        }
        float chance = PerkAttributeHelper.getOrCreateMap(player, side)
                .modifyValue(player, ResearchManager.getProgress(player, side), getTypeString(), 0F);
        chance /= 100.0F;
        chance = AttributeEvent.postProcessModded(player, this, chance);
        if (chance >= rand.nextFloat()) {
            event.setCanceled(true);
        }
    }

}
