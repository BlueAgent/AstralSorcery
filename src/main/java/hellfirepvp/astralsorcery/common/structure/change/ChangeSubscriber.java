/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2019
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.structure.change;

import com.google.common.collect.Lists;
import hellfirepvp.astralsorcery.common.data.world.WorldCacheManager;
import hellfirepvp.astralsorcery.common.data.world.data.StructureMatchingBuffer;
import hellfirepvp.astralsorcery.common.structure.ObservableArea;
import hellfirepvp.astralsorcery.common.structure.StructureMatcher;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import hellfirepvp.astralsorcery.common.util.nbt.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ChangeSubscriber
 * Created by HellFirePvP
 * Date: 02.12.2018 / 11:53
 */
public class ChangeSubscriber<T extends StructureMatcher> {

    private BlockPos requester;
    private T matcher;

    private BlockStateChangeSet changeSet = new BlockStateChangeSet();
    private Boolean isMatching = null;

    private Collection<ChunkPos> affectedChunkCache = null;

    public ChangeSubscriber(BlockPos requester, T matcher) {
        this.requester = requester;
        this.matcher = matcher;
    }

    public BlockPos getRequester() {
        return requester;
    }

    public T getMatcher() {
        return matcher;
    }

    public Collection<ChunkPos> getObservableChunks() {
        if (affectedChunkCache == null) {
            affectedChunkCache = Lists.newArrayList(getMatcher().getObservableArea().getAffectedChunks(getRequester()));
        }
        return affectedChunkCache;
    }

    public boolean observes(BlockPos pos) {
        return this.getMatcher().getObservableArea().observes(pos.subtract(getRequester()));
    }

    public void addChange(BlockPos pos, IBlockState oldState, IBlockState newState) {
        this.changeSet.addChange(pos.subtract(getRequester()), oldState, newState);
    }

    public boolean matches(World world) {
        if (this.isMatching != null && this.changeSet.isEmpty()) {
            return isMatching;
        }

        this.isMatching = this.matcher.notifyChange(world, this.requester, this.changeSet);
        this.changeSet.reset();
        WorldCacheManager.getOrLoadData(world, WorldCacheManager.SaveKey.STRUCTURE_MATCH).markDirty();

        return this.isMatching;
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.affectedChunkCache = null;

        this.matcher.readFromNBT(tag.getCompoundTag("matchData"));
        this.changeSet.readFromNBT(tag.getCompoundTag("changeData"));
        this.requester = NBTHelper.readBlockPosFromNBT(tag);
        if (tag.hasKey("isMatching")) {
            this.isMatching = tag.getBoolean("isMatching");
        } else {
            this.isMatching = null;
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        NBTTagCompound areaData = new NBTTagCompound();
        this.matcher.writeToNBT(areaData);
        tag.setTag("matchData", areaData);

        NBTTagCompound changeData = new NBTTagCompound();
        this.changeSet.writeToNBT(changeData);
        tag.setTag("changeData", changeData);

        NBTHelper.writeBlockPosToNBT(this.requester, tag);
        if (this.isMatching != null) {
            tag.setBoolean("isMatching", this.isMatching);
        }
    }
}
