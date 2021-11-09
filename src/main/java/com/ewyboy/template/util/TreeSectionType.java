package com.ewyboy.template.util;

public enum TreeSectionType {

    LOG(true),
    NETHER_WART(true),
    LEAF_NEED_BREAK(true),
    OTHER(false);

    private final boolean breakable;

    TreeSectionType(boolean breakable){
        this.breakable = breakable;
    }

    public boolean isBreakable(){
        return breakable;
    }

}
