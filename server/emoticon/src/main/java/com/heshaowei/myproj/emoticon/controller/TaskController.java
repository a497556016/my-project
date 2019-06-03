package com.heshaowei.myproj.emoticon.controller;

import com.google.common.collect.Maps;
import com.heshaowei.myproj.base.BaseController;
import com.heshaowei.myproj.base.CurdController;
import com.heshaowei.myproj.bean.response.PageResult;
import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.emoticon.dto.SpiderTaskDTO;
import com.heshaowei.myproj.emoticon.entity.SpiderTask;
import com.heshaowei.myproj.emoticon.job.IJobHandler;
import com.heshaowei.myproj.emoticon.repository.SpiderTaskRepository;
import com.heshaowei.myproj.emoticon.service.SpiderTaskService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/task")
public class TaskController extends CurdController<SpiderTaskRepository, SpiderTask, SpiderTaskDTO> {

    @Autowired
    private SpiderTaskService spiderTaskService;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    private final Map<Long, ScheduledFuture<?>> spiderTaskMap = Maps.newHashMap();

    private IJobHandler getJobHander(String jobInstance) throws NotFoundException {
        Object obj = this.webApplicationContext.getBean(jobInstance);
        if(null != obj && obj instanceof IJobHandler) {
            IJobHandler jobHandler = (IJobHandler) obj;
            return jobHandler;
        }else {
            throw new NotFoundException("对应任务实例不存在！");
        }
    }

    @GetMapping("/start")
    public Result start(Long taskId){
        SpiderTask task = this.repository.findById(taskId).get();
        try {
            IJobHandler jobHandler = this.getJobHander(task.getJobInstance());
            ScheduledFuture<?> future = this.threadPoolTaskScheduler.schedule(() -> {
                jobHandler.execute();
            }, new CronTrigger(task.getCron()));
            this.spiderTaskMap.put(taskId, future);
        } catch (NotFoundException e) {
            return Result.error(e.getMessage());
        }

        return Result.success();
    }

    @GetMapping("/stop")
    public Result stop(Long taskId) {
        ScheduledFuture<?> future = this.spiderTaskMap.get(taskId);
        if(null != future) {
            future.cancel(true);
        }
        return Result.success();
    }

    /**
     * 一次性运行
     * @param taskId
     * @return
     */
    @GetMapping("/run")
    public Result run(Long taskId){
        SpiderTask task = this.repository.findById(taskId).get();
        try {
            IJobHandler jobHandler = this.getJobHander(task.getJobInstance());
            this.threadPoolTaskScheduler.execute(() -> {
                jobHandler.execute();
            });
        } catch (NotFoundException e) {
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/shutdown")
    public Result shutdown(Long taskId){
        SpiderTask task = this.repository.findById(taskId).get();
        try {
            IJobHandler jobHandler = this.getJobHander(task.getJobInstance());
            jobHandler.close();
        } catch (NotFoundException e) {
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/selectPage")
    @Override
    public PageResult<SpiderTaskDTO> selectPage(int current, int size, HttpServletRequest request){
        SpiderTaskDTO dto = new SpiderTaskDTO();
        Page<SpiderTask> page = this.spiderTaskService.findByPage(current, size, super.buildExample(request, dto));
        PageResult<SpiderTaskDTO> pageResult = super.pageConvert(dto, page);
        pageResult.getData().forEach(spiderTaskDTO -> {
            ScheduledFuture<?> future = spiderTaskMap.get(spiderTaskDTO.getId());
            if(null == future) {
                spiderTaskDTO.setStatus("0"); //没有任务运行
            }else if (future.isDone()){
                spiderTaskDTO.setStatus("1"); //已经停止
            }else if (future.isCancelled()) {
                spiderTaskDTO.setStatus("2"); //已经取消
            }else {
                spiderTaskDTO.setStatus("3"); //运行中状态
            }
            try {
                IJobHandler jobHandler = this.getJobHander(spiderTaskDTO.getJobInstance());
                spiderTaskDTO.setRunStatus(jobHandler.status());
            } catch (NotFoundException e) {

            }
        });
        return pageResult;
    }
}
