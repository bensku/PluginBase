package org.extendedalpha.pluginbase.inventoryapi.attributes;

public enum Operation {

    /**
     * Will add <tt>amount</tt> to <tt>output</tt>. Will be applied before any other operation.
     */
    ADD_NUMBER(0),

    /**
     * Will add <tt>amount * base</tt> to <tt>output</tt> after applying all {@link #ADD_NUMBER} operations and before applying any {@link #ADD_PERCENTAGE} operations.
     */
    MULTIPLY_PERCENTAGE(1),

    /**
     * Will add <tt>output * (1+amount)</tt> to the output, after applying all other operations.
     */
    ADD_PERCENTAGE(2);
    public int id;

    Operation(int id){
        this.id=id;
    }

    public static Operation fromID(int id){
        for(Operation o : values())
            if(o.id==id)
                return o;
        return null;
    }

}