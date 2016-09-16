package hellfirepvp.astralsorcery.common.entities;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.ClientGuiHandler;
import hellfirepvp.astralsorcery.common.item.ItemEntityPlacer;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.util.ItemUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: EntityTelescope
 * Created by HellFirePvP
 * Date: 08.05.2016 / 23:11
 */
public class EntityTelescope extends EntityLivingBase {

    public EntityTelescope(World worldIn) {
        super(worldIn);
        getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
        setSize(0.9F, 1.5F);
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand) {
        if(player.isSneaking()) {
            if(!worldObj.isRemote) {
                setDead();
                if(!player.isCreative()) {
                    ItemUtils.dropItemNaturally(worldObj, posX, posY + 0.3, posZ, new ItemStack(ItemsAS.entityPlacer, 1, ItemEntityPlacer.PlacerType.TELESCOPE.getMeta()));
                }
            }
        } else {
            if (player.worldObj.isRemote) {
                player.openGui(AstralSorcery.instance, ClientGuiHandler.EnumClientGui.TELESCOPE.ordinal(), player.worldObj, getEntityId(), 0, 0);
            }
        }

        return EnumActionResult.SUCCESS;
    }



    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return null;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }

}