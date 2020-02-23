package com.test.model;

public class ServiceData {
	
	public String serviceName;
	public long serviceId;
	public long entryTime;
	public long exitTime;
	
	@Override
	public String toString() {
		return "ServiceData [serviceName=" + serviceName + ", serviceId=" + serviceId + ", execution time ="
				+ (exitTime - entryTime) + "]";
	}

	public ServiceData(String serviceName, long serviceId) {
		this.serviceName = serviceName;
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public long getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}

	public long getExitTime() {
		return exitTime;
	}

	public void setExitTime(long exitTime) {
		this.exitTime = exitTime;
	}
}
