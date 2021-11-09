package com.ewyboy.template.util;

import net.minecraft.util.math.BlockPos;

public class TreeSection {

    private final BlockPos blockPos;
    private final TreeSectionType treePartType;
    private final int sequence;

    public TreeSection(BlockPos blockPos, TreeSectionType treePartType, int sequence){
        this.blockPos = blockPos;
        this.treePartType = treePartType;
        this.sequence = sequence;
    }

    public BlockPos getBlockPos(){
        return blockPos;
    }

    public int getSequence(){
        return sequence;
    }

    public TreeSectionType getTreePartType(){
        return treePartType;
    }

}
