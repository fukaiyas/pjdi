package com.bugworm.pjdi;

import com.sun.jdi.VirtualMachineManager;

import java.util.Optional;

/**
 * Builder class for VirtualMachineWrapper.
 */
public abstract class WrapperBuilder<T extends WrapperBuilder<T>>{

    /** VirtualMachineManager object */
    protected final VirtualMachineManager manager;

    /** Name for selecting Connector */
    protected Optional<String> connectorName = Optional.empty();

    //TODO Map<String, String>を持っておいて(Map<String, Optional<String>>かも)、任意のプロパティを設定可能にしておくか？

    /**
     * Factory method for ListeningConnectorWrapperBuilder with default VirtualMachineManager object.
     * @return ListeningConnectorWrapperBuilder object
     */
    public static ListeningConnectorWrapperBuilder withListeningConnector(){
        return new ListeningConnectorWrapperBuilder();
    }

    /**
     * Factory method for AttachingConnectorWrapperBuider with default VirtualMachineManager object.
     * @return AttachingConnectorWrapperBuider object
     */
    public static AttachingConnectorWrapperBuilder withAttachingConnector(){
        return new AttachingConnectorWrapperBuilder();
    }

    /**
     * Factory method for LaunchingConnectorWrapperBuilder with default VirtualMachineManager object.
     * @return LaunchingConnectorWrapperBuilder object
     */
    public static LaunchingConnectorWrapperBuilder withLaunchingConnector(){
        return new LaunchingConnectorWrapperBuilder();
    }

    /**
     * Constructs a WrapperBuilder with the specified VirtualMachineManager object.
     * @param vmm VirtualMachineManager object
     */
    public WrapperBuilder(VirtualMachineManager vmm){
        manager = vmm;
    }

    /**
     * Sets the connector's name to select.
     * @param name Connector's name
     * @return This instance
     */
    public T connectorName(String name){
        connectorName = Optional.of(name);
        return (T)this;
    }
}
