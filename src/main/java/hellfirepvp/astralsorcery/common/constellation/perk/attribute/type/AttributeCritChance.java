/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2018
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.constellation.perk.attribute.type;

import hellfirepvp.astralsorcery.common.constellation.perk.PerkAttributeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: AttributeCritChance
 * Created by HellFirePvP
 * Date: 13.07.2018 / 19:06
 */
public class AttributeCritChance extends PerkAttributeType {

    public AttributeCritChance() {
        super(AttributeTypeRegistry.ATTR_TYPE_INC_CRIT_CHANCE);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onArrowCt(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityArrow) {
            EntityArrow arrow = (EntityArrow) event.getEntity();
            if (arrow.shootingEntity != null && arrow.shootingEntity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) arrow.shootingEntity;
                Side side = player.world.isRemote ? Side.CLIENT : Side.SERVER;
                if (!hasTypeApplied(player, side)) {
                    return;
                }
                float critChance = PerkAttributeHelper.getOrCreateMap(player, side)
                        .modifyValue(getTypeString(), 0F);
                critChance *= PerkAttributeHelper.getOrCreateMap(player, side).getModifier(AttributeTypeRegistry.ATTR_TYPE_INC_PERK_EFFECT);
                if ((critChance / 100F) >= rand.nextFloat()) {
                    arrow.setIsCritical(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onCrit(CriticalHitEvent event) {
        if (event.isVanillaCritical() || event.getResult() == Event.Result.ALLOW) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        Side side = player.world.isRemote ? Side.CLIENT : Side.SERVER;
        if (!hasTypeApplied(player, side)) {
            return;
        }

        float critChance = PerkAttributeHelper.getOrCreateMap(player, side)
                .modifyValue(getTypeString(), 0F);
        critChance *= PerkAttributeHelper.getOrCreateMap(player, side).getModifier(AttributeTypeRegistry.ATTR_TYPE_INC_PERK_EFFECT);
        if ((critChance / 100F) >= rand.nextFloat()) {
            event.setResult(Event.Result.ALLOW);
        }
    }

}
