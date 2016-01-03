package com.bugworm.pjdi;

import com.sun.jdi.VirtualMachineManager;

import java.util.Optional;

/**
 * Builder class for VirtualMachineWrapper.
 */
public abstract class WrapperBuilder<T extends WrapperBuilder<T>>{

    /** VirtualMachineManager object */
    protected final VirtualMachineManager manager;

    /** Whether to suspend when target VM started */
    protected Optional<Boolean> suspendMode = Optional.empty();

    /** Name for selecting Connector */
    protected Optional<String> name = Optional.empty();

    /**
     * Factory method for ListeningConnectorWrapperBuilder with default VirtualMachineManager object.
     * @return ListeningConnectorWrapperBuilder object
     */
    public static ListeningConnectorWrapperBuilder withListeningConnector(){
        return new ListeningConnectorWrapperBuilder();
    }

    /**
     * Constructs a WrapperBuilder with the specified VirtualMachineManager object.
     * @param vmm VirtualMachineManager object
     */
    public WrapperBuilder(VirtualMachineManager vmm){
        manager = vmm;
    }

    /**
     * Sets the suspend property.
     * @param suspend Whether to suspend when target VM started
     * @return This instance
     */
    public T suspend(boolean suspend){
        suspendMode = Optional.of(suspend);
        return (T)this;
    }
}
