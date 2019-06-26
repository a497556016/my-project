<template>
    <div>
        <curd-page ref="page" :columns="columns" :load-fun="loadTable" :editable="true" @add="onAdd" @edit="onEdit" @delete="onDelete">
            <a slot="url" slot-scope="{text}" :href="text" target="_blank">{{text}}</a>
            <template slot="status" slot-scope="{text, record, index}">
                <span class="task-status" v-if="text == '0'">无任务</span>
                <span class="task-status warn" v-else-if="text == '1'">已停止</span>
                <span class="task-status warn" v-else-if="text == '2'">已取消</span>
                <span class="task-status info" v-else>运行中</span>
            </template>
            <template slot="runStatus" slot-scope="{text, record, index}">
                <span class="task-status warn" v-if="text == 'Running'">运行中</span>
                <span class="task-status" v-else>已停止</span>
            </template>
            <template slot="handler" slot-scope="{text, record, index}">
                <a-button-group>
                    <a-button v-if="record.runStatus != 'Running'" @click="runTask(record.id)">执行</a-button>
                    <a-button v-if="record.runStatus == 'Running'" @click="shutdownTask(record.id)">关闭</a-button>
                    <a-button v-if="record.status != '3'" @click="startTask(record.id)">激活任务</a-button>
                    <a-button v-if="record.status == '3'" @click="stopTask(record.id)">取消任务</a-button>
                    <!--<a-button type="primary">编辑</a-button>-->
                </a-button-group>
            </template>
        </curd-page>

        <a-modal title="添加" v-model="addVisible" @ok="saveSpiderTask">
            <a-form>
                <a-form-item label="名称">
                    <a-input v-model="spiderTask.name"></a-input>
                </a-form-item>
                <a-form-item label="来源网站">
                    <a-input v-model="spiderTask.url"></a-input>
                </a-form-item>
                <a-form-item label="任务Bean实例">
                    <a-input v-model="spiderTask.jobInstance"></a-input>
                </a-form-item>
                <a-form-item label="cron规则">
                    <a-input v-model="spiderTask.cron"></a-input>
                </a-form-item>
                <a-form-item label="负责人">
                    <a-input v-model="spiderTask.owner"></a-input>
                </a-form-item>
            </a-form>
        </a-modal>
    </div>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";
    import spiderTaskService from '../../../api/tools/emoticon/task';
    export default {
        name: "EmoticonSpider",
        components: {CurdPage},
        data(){
            return {
                columns: [
                    {title: '名称', dataIndex: 'name'},
                    {title: '来源网站', dataIndex: 'url', slot: 'url'},
                    {title: 'cron规则', dataIndex: 'cron'},
                    {title: '负责人', dataIndex: 'owner'},
                    {title: '任务状态', dataIndex: 'status', slot: 'status'},
                    {title: '执行状态', dataIndex: 'runStatus', slot: 'runStatus'},
                    {title: '操作', align: 'center', slot:'handler'}
                ],
                data: [
                    {name: '斗图网表情包', website: 'https://doutula.com', cron: '0 0 2 * * *'}
                ],
                addVisible: false,
                spiderTask: {}
            }
        },
        methods: {
            loadTable(params){
                return spiderTaskService.selectPage(params);
            },
            onAdd(){
                this.addVisible = true;
            },
            onEdit(record){
                this.addVisible = true;
                this.spiderTask = record;
            },
            saveSpiderTask(){
                spiderTaskService.save(this.spiderTask);
            },
            onDelete(pageData, selectRows){
                this.$confirm({
                    content: '确认要删除吗？',
                    onOk(){
                        spiderTaskService.batchDelete(selectRows).then(res => {
                            if(res.code == 1){
                                this.$message.success('删除成功！');
                                this.$refs.page.reload();
                            }
                        });
                    }
                })
            },
            startTask(taskId){
                spiderTaskService.start(taskId).then(res => {
                    if(res.code == 1){
                        this.$message.success('启动成功！');
                        this.$refs.page.reload();
                    }else {
                        this.$message.error(res.msg);
                    }
                });
            },
            stopTask(taskId){
                spiderTaskService.stop(taskId).then(res => {
                    if(res.code == 1){
                        this.$message.success('关闭任务成功！');
                        this.$refs.page.reload();
                    }else {
                        this.$message.error(res.msg);
                    }
                });
            },
            runTask(taskId) {
                spiderTaskService.run(taskId).then(res => {
                    if(res.code == 1){
                        this.$message.success('任务已经执行！');
                        this.$refs.page.reload();
                    }else {
                        this.$message.error(res.msg);
                    }
                });
            },
            shutdownTask(taskId) {
                spiderTaskService.shutdown(taskId).then(res => {
                    if(res.code == 1){
                        this.$message.success('任务已经关闭！');
                        this.$refs.page.reload();
                    }else {
                        this.$message.error(res.msg);
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .task-status {
        background: #cccccc;
        border-radius: 5px;
        padding: 2px 5px;
        color: white;
    }
    .warn {
        background: #ff4d4f;
    }
    .info {
        background: #2f54eb;
    }
</style>