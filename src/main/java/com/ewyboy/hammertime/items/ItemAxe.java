package com.ewyboy.hammertime.items;

import com.ewyboy.bibliotheca.common.interfaces.IItemRenderer;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gnu.trove.set.hash.THashSet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Created by EwyBoy
 */
public class ItemAxe extends ItemBaseTool implements IItemRenderer {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.LOG, Blocks.LOG2, Blocks.PLANKS, Blocks.CRAFTING_TABLE, Blocks.CHEST);

    public ItemAxe(ToolMaterial toolMaterial) {
        super(toolMaterial, EFFECTIVE_ON);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return super.onBlockDestroyed(stack, world, state, pos, entityLiving);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return fellTree(itemstack, pos, player);
    }

    public static boolean detectTree(World world, BlockPos origin) {
        BlockPos pos = null;
        Stack<BlockPos> candidates = new Stack<>();
        candidates.add(origin);

        while(!candidates.isEmpty()) {
            BlockPos candidate = candidates.pop();
            if((pos == null || candidate.getY() > pos.getY()) && isLog(world, candidate)) {
                pos = candidate.up();
                while(isLog(world, pos)) {
                    pos = pos.up();
                }
                candidates.add(pos.north());
                candidates.add(pos.east());
                candidates.add(pos.south());
                candidates.add(pos.west());
            }
        }

        if(pos == null) {
            return false;
        }

        int d = 3;
        int o = -1; // -(d-1)/2
        int leaves = 0;
        for(int x = 0; x < d; x++) {
            for(int y = 0; y < d; y++) {
                for(int z = 0; z < d; z++) {
                    BlockPos leaf = pos.add(o + x, o + y, o + z);
                    IBlockState state = world.getBlockState(leaf);
                    if(state.getBlock().isLeaves(state, world, leaf)) {
                        if(++leaves >= 5) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isLog(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isWood(world, pos);
    }

    public static boolean fellTree(ItemStack itemstack, BlockPos start, EntityPlayer player) {
        if(player.getEntityWorld().isRemote) {
            return true;
        }
        MinecraftForge.EVENT_BUS.register(new TreeChopTask(itemstack, start, player, 1));
        return true;
    }

    public static class TreeChopTask {

        public final World world;
        public final EntityPlayer player;
        public final ItemStack tool;
        public final int blocksPerTick;

        public Queue<BlockPos> blocks = Lists.newLinkedList();
        public Set<BlockPos> visited = new THashSet<>();

        public TreeChopTask(ItemStack tool, BlockPos start, EntityPlayer player, int blocksPerTick) {
            this.world = player.getEntityWorld();
            this.player = player;
            this.tool = tool;
            this.blocksPerTick = blocksPerTick;

            this.blocks.add(start);
        }

        @SubscribeEvent
        public void chopChop(TickEvent.WorldTickEvent event) {
            if(event.side.isClient()) {
                finish();
                return;
            }

            if(event.world.provider.getDimension() != world.provider.getDimension()) {
                return;
            }

            int left = blocksPerTick;

            BlockPos pos;
            while(left > 0) {
                if (tool.getItemDamage() >= tool.getMaxDamage()) {
                    player.getHeldItem(player.swingingHand).shrink(1);
                    finish();
                    break;
                }

                if(blocks.isEmpty()) {
                    finish();
                    return;
                }

                pos = blocks.remove();
                if(!visited.add(pos)) {
                    continue;
                }

                if(!isLog(world, pos)) {
                    continue;
                }

                for(EnumFacing facing : new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST }) {
                    BlockPos pos2 = pos.offset(facing);
                    if(!visited.contains(pos2)) {
                        blocks.add(pos2);
                    }
                }

                for(int x = 0; x < 3; x++) {
                    for(int z = 0; z < 3; z++) {
                        BlockPos pos2 = pos.add(-1 + x, 1, -1 + z);
                        if(!visited.contains(pos2)) {
                            blocks.add(pos2);
                        }
                    }
                }
                world.destroyBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), true);
                tool.damageItem(1, player);
                left--;
            }
        }

        private void finish() {
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
