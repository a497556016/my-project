import BaseService from "../../../base";

class SpiderTaskService extends BaseService{

    start(taskId){
        return this.get('/start', {taskId});
    }

    stop(taskId){
        return this.get('/stop', {taskId});
    }

    run(taskId){
        return this.get('/run', {taskId});
    }

    shutdown(taskId){
        return this.get('/shutdown', {taskId});
    }
}

export default new SpiderTaskService('/emoticon-server/task')