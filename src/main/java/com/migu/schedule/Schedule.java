package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*类名和方法不能修改
 */
public class Schedule {

    /**
     * 系统初始化
     * 系统初始化，会清空所有数据，包括已经注册到系统的服务节点信息、以及添加的任务信息，全部都被清理。执行该命令后，系统恢复到最初始的状态。
     *
     * @return E001初始化成功
     */
    public int init() {
        //全部清理
        nodeIdList.clear();
        hangUpList.clear();
        allList.clear();
        runTaskList.clear();
        return ReturnCodeKeys.E001;
    }

    // 服务节点注册列表
    private static List<Integer> nodeIdList = new ArrayList<Integer>(10);

    // 挂起列表 key=taskid ,value=consumption
    private static Map<Integer, Integer> hangUpList =
            new HashMap<Integer, Integer>();

    // 所有任务列表，包括运行及挂起，key=taskid ,value=consumption
    private static Map<Integer, Integer> allList =
            new HashMap<Integer, Integer>();

    // 运行中的任务列表
    private static List<TaskInfo> runTaskList = new ArrayList<TaskInfo>(10);

    /**
     * 服务节点注册
     *
     * @param nodeId
     * @return
     */
    public int registerNode(int nodeId)
    {
        if (nodeId <= 0)
        {
            return ReturnCodeKeys.E004;
        }
        if (nodeIdList.contains(nodeId))
        {
            return ReturnCodeKeys.E005;
        }
        nodeIdList.add(nodeId);
        return ReturnCodeKeys.E003;
    }

    /**
     * 服务节点注销
     *
     * @param nodeId
     * @return
     */
    public int unregisterNode(int nodeId)
    {
        if (nodeId <= 0)
        {
            return ReturnCodeKeys.E004;
        }
        if (!nodeIdList.contains(nodeId))
        {
            return ReturnCodeKeys.E007;
        }
        nodeIdList.remove(nodeId);
        return ReturnCodeKeys.E006;
    }

    /**
     * 添加任务
     *
     * @param taskId
     * @param consumption
     * @return
     */
    public int addTask(int taskId, int consumption)
    {
        if (taskId <= 0)
        {
            return ReturnCodeKeys.E009;
        }
        if (hangUpList.keySet().contains(taskId))
        {
            return ReturnCodeKeys.E010;
        }
        hangUpList.put(taskId, consumption);
        allList.put(taskId, consumption);
        return ReturnCodeKeys.E008;
    }

    /**
     * 删除任务
     *
     * @param taskId
     * @return
     */
    public int deleteTask(int taskId)
    {
        if (taskId <= 0)
        {
            return ReturnCodeKeys.E009;
        }
        if (!allList.keySet().contains(taskId))
        {
            return ReturnCodeKeys.E012;
        }
        TaskInfo removetaskInfo = null;
        if (hangUpList.keySet().contains(taskId))
        {
            hangUpList.remove(taskId);
        }
        else
        {
            for (TaskInfo taskInfo : runTaskList)
            {
                if (taskId == taskInfo.getTaskId())
                {
                    removetaskInfo = taskInfo;
                }
            }
            if (null != removetaskInfo)
            {
                runTaskList.remove(removetaskInfo);
            }
        }
        allList.remove(taskId);
        return ReturnCodeKeys.E011;
    }

    /**
     * 任务调度
     *
     * @param threshold
     * @return
     */
    public int scheduleTask(int threshold)
    {
        if (threshold <= 0)
        {
            return ReturnCodeKeys.E002;
        }
        runTaskList.clear();

        //所有的消耗率列表
        List<Integer> consumptions = new ArrayList<Integer>(allList.values());


        return ReturnCodeKeys.E000;
    }

    /**
     * 查询任务状态列表
     * @param tasks
     * @return
     */
    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }

}
