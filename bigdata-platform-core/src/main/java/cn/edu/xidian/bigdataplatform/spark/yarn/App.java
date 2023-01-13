package cn.edu.xidian.bigdataplatform.spark.yarn;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-08
 * @description:
 */
/**
 * Copyright 2023 json.cn
 */
import java.util.Date;

/**
 * Auto-generated: 2023-01-08 22:23:28
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class App {

    private String id;
    private String user;
    private String name;
    private String queue;
    private String state;
    private String finalStatus;
    private int progress;
    private String trackingUI;
    private String trackingUrl;
    private String diagnostics;
    private long clusterId;
    private String applicationType;
    private String applicationTags;
    private int priority;
    private long startedTime;
    private long launchTime;
    private long finishedTime;
    private long elapsedTime;
    private String amContainerLogs;
    private String amHostHttpAddress;
    private String masterNodeId;
    private int allocatedMB;
    private int allocatedVCores;
    private int reservedMB;
    private int reservedVCores;
    private int runningContainers;
    private long memorySeconds;
    private int vcoreSeconds;
    private int queueUsagePercentage;
    private int clusterUsagePercentage;
    private ResourceSecondsMap resourceSecondsMap;
    private int preemptedResourceMB;
    private int preemptedResourceVCores;
    private int numNonAMContainerPreempted;
    private int numAMContainerPreempted;
    private int preemptedMemorySeconds;
    private int preemptedVcoreSeconds;
    private PreemptedResourceSecondsMap preemptedResourceSecondsMap;
    private String logAggregationStatus;
    private boolean unmanagedApplication;
    private String amNodeLabelExpression;
    private Timeouts timeouts;

    public App() {
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public String getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
    public String getQueue() {
        return queue;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }
    public String getFinalStatus() {
        return finalStatus;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    public int getProgress() {
        return progress;
    }

    public void setTrackingUI(String trackingUI) {
        this.trackingUI = trackingUI;
    }
    public String getTrackingUI() {
        return trackingUI;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }
    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setDiagnostics(String diagnostics) {
        this.diagnostics = diagnostics;
    }
    public String getDiagnostics() {
        return diagnostics;
    }

    public void setClusterId(long clusterId) {
        this.clusterId = clusterId;
    }
    public long getClusterId() {
        return clusterId;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationTags(String applicationTags) {
        this.applicationTags = applicationTags;
    }
    public String getApplicationTags() {
        return applicationTags;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return priority;
    }

    public void setStartedTime(long startedTime) {
        this.startedTime = startedTime;
    }
    public long getStartedTime() {
        return startedTime;
    }

    public void setLaunchTime(long launchTime) {
        this.launchTime = launchTime;
    }
    public long getLaunchTime() {
        return launchTime;
    }

    public void setFinishedTime(long finishedTime) {
        this.finishedTime = finishedTime;
    }
    public long getFinishedTime() {
        return finishedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setAmContainerLogs(String amContainerLogs) {
        this.amContainerLogs = amContainerLogs;
    }
    public String getAmContainerLogs() {
        return amContainerLogs;
    }

    public void setAmHostHttpAddress(String amHostHttpAddress) {
        this.amHostHttpAddress = amHostHttpAddress;
    }
    public String getAmHostHttpAddress() {
        return amHostHttpAddress;
    }

    public void setMasterNodeId(String masterNodeId) {
        this.masterNodeId = masterNodeId;
    }
    public String getMasterNodeId() {
        return masterNodeId;
    }

    public void setAllocatedMB(int allocatedMB) {
        this.allocatedMB = allocatedMB;
    }
    public int getAllocatedMB() {
        return allocatedMB;
    }

    public void setAllocatedVCores(int allocatedVCores) {
        this.allocatedVCores = allocatedVCores;
    }
    public int getAllocatedVCores() {
        return allocatedVCores;
    }

    public void setReservedMB(int reservedMB) {
        this.reservedMB = reservedMB;
    }
    public int getReservedMB() {
        return reservedMB;
    }

    public void setReservedVCores(int reservedVCores) {
        this.reservedVCores = reservedVCores;
    }
    public int getReservedVCores() {
        return reservedVCores;
    }

    public void setRunningContainers(int runningContainers) {
        this.runningContainers = runningContainers;
    }
    public int getRunningContainers() {
        return runningContainers;
    }

    public void setMemorySeconds(long memorySeconds) {
        this.memorySeconds = memorySeconds;
    }
    public long getMemorySeconds() {
        return memorySeconds;
    }

    public void setVcoreSeconds(int vcoreSeconds) {
        this.vcoreSeconds = vcoreSeconds;
    }
    public int getVcoreSeconds() {
        return vcoreSeconds;
    }

    public void setQueueUsagePercentage(int queueUsagePercentage) {
        this.queueUsagePercentage = queueUsagePercentage;
    }
    public int getQueueUsagePercentage() {
        return queueUsagePercentage;
    }

    public void setClusterUsagePercentage(int clusterUsagePercentage) {
        this.clusterUsagePercentage = clusterUsagePercentage;
    }
    public int getClusterUsagePercentage() {
        return clusterUsagePercentage;
    }

    public void setResourceSecondsMap(ResourceSecondsMap resourceSecondsMap) {
        this.resourceSecondsMap = resourceSecondsMap;
    }
    public ResourceSecondsMap getResourceSecondsMap() {
        return resourceSecondsMap;
    }

    public void setPreemptedResourceMB(int preemptedResourceMB) {
        this.preemptedResourceMB = preemptedResourceMB;
    }
    public int getPreemptedResourceMB() {
        return preemptedResourceMB;
    }

    public void setPreemptedResourceVCores(int preemptedResourceVCores) {
        this.preemptedResourceVCores = preemptedResourceVCores;
    }
    public int getPreemptedResourceVCores() {
        return preemptedResourceVCores;
    }

    public void setNumNonAMContainerPreempted(int numNonAMContainerPreempted) {
        this.numNonAMContainerPreempted = numNonAMContainerPreempted;
    }
    public int getNumNonAMContainerPreempted() {
        return numNonAMContainerPreempted;
    }

    public void setNumAMContainerPreempted(int numAMContainerPreempted) {
        this.numAMContainerPreempted = numAMContainerPreempted;
    }
    public int getNumAMContainerPreempted() {
        return numAMContainerPreempted;
    }

    public void setPreemptedMemorySeconds(int preemptedMemorySeconds) {
        this.preemptedMemorySeconds = preemptedMemorySeconds;
    }
    public int getPreemptedMemorySeconds() {
        return preemptedMemorySeconds;
    }

    public void setPreemptedVcoreSeconds(int preemptedVcoreSeconds) {
        this.preemptedVcoreSeconds = preemptedVcoreSeconds;
    }
    public int getPreemptedVcoreSeconds() {
        return preemptedVcoreSeconds;
    }

    public void setPreemptedResourceSecondsMap(PreemptedResourceSecondsMap preemptedResourceSecondsMap) {
        this.preemptedResourceSecondsMap = preemptedResourceSecondsMap;
    }
    public PreemptedResourceSecondsMap getPreemptedResourceSecondsMap() {
        return preemptedResourceSecondsMap;
    }

    public void setLogAggregationStatus(String logAggregationStatus) {
        this.logAggregationStatus = logAggregationStatus;
    }
    public String getLogAggregationStatus() {
        return logAggregationStatus;
    }

    public void setUnmanagedApplication(boolean unmanagedApplication) {
        this.unmanagedApplication = unmanagedApplication;
    }
    public boolean getUnmanagedApplication() {
        return unmanagedApplication;
    }

    public void setAmNodeLabelExpression(String amNodeLabelExpression) {
        this.amNodeLabelExpression = amNodeLabelExpression;
    }
    public String getAmNodeLabelExpression() {
        return amNodeLabelExpression;
    }

    public void setTimeouts(Timeouts timeouts) {
        this.timeouts = timeouts;
    }
    public Timeouts getTimeouts() {
        return timeouts;
    }

}