package com.applinzi.testwhy.domian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskPage implements Serializable{
	private Integer nowPage;
	private Integer pageCount;
	private Integer taskCount;
	private List<Task>taskList = new ArrayList<Task>();
	public TaskPage() {}
	public TaskPage(Integer nowPage, Integer pageCount, Integer taskCount,
			List<Task> taskList) {
		this.nowPage = nowPage;
		this.pageCount = pageCount;
		this.taskCount = taskCount;
		this.taskList = taskList;
	}
	public Integer getNowPage() {
		return nowPage;
	}
	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}
	public List<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	
}
