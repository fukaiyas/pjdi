package com.bugworm.pjdi;

import com.sun.jdi.*;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.request.EventRequestManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Wrapper for com.sun.jdi.VirtualMachine.
 */
public class VirtualMachineWrapper implements VirtualMachine {

    /** VirtualMachine object */
    private final VirtualMachine virtualMachine;

    private ExecutorService executorService;

    /**
     * Constructs a VirtualMachineWrapper with the specified VirtualMachine object.
     * @param vm
     */
    public VirtualMachineWrapper(VirtualMachine vm){
        virtualMachine = vm;
    }

    public ExecutorService getExecutorService(){
        return executorService;
    }

    public void setExecutorService(ExecutorService es){
        executorService = es;
    }


    //-------- Delegation -------
    @Override
    public List<ReferenceType> classesByName(String s) {
        return virtualMachine.classesByName(s);
    }

    @Override
    public List<ReferenceType> allClasses() {
        return virtualMachine.allClasses();
    }

    @Override
    public void redefineClasses(Map<? extends ReferenceType, byte[]> map) {
        virtualMachine.redefineClasses(map);
    }

    @Override
    public List<ThreadReference> allThreads() {
        return virtualMachine.allThreads();
    }

    @Override
    public void suspend() {
        virtualMachine.suspend();
    }

    @Override
    public void resume() {
        virtualMachine.resume();
    }

    @Override
    public List<ThreadGroupReference> topLevelThreadGroups() {
        return virtualMachine.topLevelThreadGroups();
    }

    @Override
    public EventQueue eventQueue() {
        return virtualMachine.eventQueue();
    }

    @Override
    public EventRequestManager eventRequestManager() {
        return virtualMachine.eventRequestManager();
    }

    @Override
    public BooleanValue mirrorOf(boolean b) {
        return virtualMachine.mirrorOf(b);
    }

    @Override
    public ByteValue mirrorOf(byte b) {
        return virtualMachine.mirrorOf(b);
    }

    @Override
    public CharValue mirrorOf(char c) {
        return virtualMachine.mirrorOf(c);
    }

    @Override
    public ShortValue mirrorOf(short i) {
        return virtualMachine.mirrorOf(i);
    }

    @Override
    public IntegerValue mirrorOf(int i) {
        return virtualMachine.mirrorOf(i);
    }

    @Override
    public LongValue mirrorOf(long l) {
        return virtualMachine.mirrorOf(l);
    }

    @Override
    public FloatValue mirrorOf(float v) {
        return virtualMachine.mirrorOf(v);
    }

    @Override
    public DoubleValue mirrorOf(double v) {
        return virtualMachine.mirrorOf(v);
    }

    @Override
    public StringReference mirrorOf(String s) {
        return virtualMachine.mirrorOf(s);
    }

    @Override
    public VoidValue mirrorOfVoid() {
        return virtualMachine.mirrorOfVoid();
    }

    @Override
    public Process process() {
        return virtualMachine.process();
    }

    @Override
    public void dispose() {
        virtualMachine.dispose();
    }

    @Override
    public void exit(int i) {
        virtualMachine.exit(i);
    }

    @Override
    public boolean canWatchFieldModification() {
        return virtualMachine.canWatchFieldModification();
    }

    @Override
    public boolean canWatchFieldAccess() {
        return virtualMachine.canWatchFieldAccess();
    }

    @Override
    public boolean canGetBytecodes() {
        return virtualMachine.canGetBytecodes();
    }

    @Override
    public boolean canGetSyntheticAttribute() {
        return virtualMachine.canGetSyntheticAttribute();
    }

    @Override
    public boolean canGetOwnedMonitorInfo() {
        return virtualMachine.canGetOwnedMonitorInfo();
    }

    @Override
    public boolean canGetCurrentContendedMonitor() {
        return virtualMachine.canGetCurrentContendedMonitor();
    }

    @Override
    public boolean canGetMonitorInfo() {
        return virtualMachine.canGetMonitorInfo();
    }

    @Override
    public boolean canUseInstanceFilters() {
        return virtualMachine.canUseInstanceFilters();
    }

    @Override
    public boolean canRedefineClasses() {
        return virtualMachine.canRedefineClasses();
    }

    @Override
    public boolean canAddMethod() {
        return virtualMachine.canAddMethod();
    }

    @Override
    public boolean canUnrestrictedlyRedefineClasses() {
        return virtualMachine.canUnrestrictedlyRedefineClasses();
    }

    @Override
    public boolean canPopFrames() {
        return virtualMachine.canPopFrames();
    }

    @Override
    public boolean canGetSourceDebugExtension() {
        return virtualMachine.canGetSourceDebugExtension();
    }

    @Override
    public boolean canRequestVMDeathEvent() {
        return virtualMachine.canRequestVMDeathEvent();
    }

    @Override
    public boolean canGetMethodReturnValues() {
        return virtualMachine.canGetMethodReturnValues();
    }

    @Override
    public boolean canGetInstanceInfo() {
        return virtualMachine.canGetInstanceInfo();
    }

    @Override
    public boolean canUseSourceNameFilters() {
        return virtualMachine.canUseSourceNameFilters();
    }

    @Override
    public boolean canForceEarlyReturn() {
        return virtualMachine.canForceEarlyReturn();
    }

    @Override
    public boolean canBeModified() {
        return virtualMachine.canBeModified();
    }

    @Override
    public boolean canRequestMonitorEvents() {
        return virtualMachine.canRequestMonitorEvents();
    }

    @Override
    public boolean canGetMonitorFrameInfo() {
        return virtualMachine.canGetMonitorFrameInfo();
    }

    @Override
    public boolean canGetClassFileVersion() {
        return virtualMachine.canGetClassFileVersion();
    }

    @Override
    public boolean canGetConstantPool() {
        return virtualMachine.canGetConstantPool();
    }

    @Override
    public void setDefaultStratum(String s) {
        virtualMachine.setDefaultStratum(s);
    }

    @Override
    public String getDefaultStratum() {
        return virtualMachine.getDefaultStratum();
    }

    @Override
    public long[] instanceCounts(List<? extends ReferenceType> list) {
        return virtualMachine.instanceCounts(list);
    }

    @Override
    public String description() {
        return virtualMachine.description();
    }

    @Override
    public String version() {
        return virtualMachine.version();
    }

    @Override
    public String name() {
        return virtualMachine.name();
    }

    @Override
    public void setDebugTraceMode(int i) {
        virtualMachine.setDebugTraceMode(i);
    }

    /**
     * Get the original VirtualMachine object.
     * @return the original VirtualMachine object.
     */
    @Override
    public VirtualMachine virtualMachine() {
        return virtualMachine.virtualMachine();
    }
}
