package com.bugworm.pjdi;

import com.sun.jdi.VirtualMachineManager;

import java.util.Optional;

/**
 * Builder class for VirtualMachineWrapper.
 */
public abstract class WrapperBuilder<T extends WrapperBuilder<T>>{

    protected final VirtualMachineManager manager;

    protected Optional<Boolean> suspendMode = Optional.empty();

    public WrapperBuilder(VirtualMachineManager vmm){
        manager = vmm;
    }

    public T suspend(boolean suspend){
        suspendMode = Optional.of(suspend);
        return (T)this;
    }
}
