<template>
    <div>
        <search-form :items="formItems" @submit="search">
        </search-form>
        <a-table :columns="columns" :data-source="data" :row-key="record => record.id" :rowSelection="rowSelection" :pagination="pagination" :loading="loading" bordered>
            <template slot="title" slot-scope="currentPageData">
                <div class="space-between-6">
                <a-button type="primary" @click="newTableVisible = true"><a-icon type="plus"></a-icon>新建表</a-button>
                <a-button type="default" @click="addFromDataTable()">添加已有表</a-button>
                <a-button type="danger" :disabled="!hasSelectedRows" @click="deleteConfirmVisible = true">批量删除</a-button>
                </div>
            </template>
            <template slot="text" slot-scope="name">
                <a href="#">{{name}}</a>
            </template>
            <a slot="action" slot-scope="text, record" @click="createServerCode(record)">生成服务端代码</a>
        </a-table>

        <a-modal
                title="新建表"
                :visible="newTableVisible"
                @ok="buildTable"
                :confirmLoading="confirmLoading"
                @cancel="newTableVisible = false"
                :width="720"
        >
            <a-form :form="tableForm" layout="horizontal">
                <a-form-item label="模块名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['moduleName', {rules: [{required: true}]}]"></a-input>
                </a-form-item>
                <a-form-item label="表名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['tableName', {rules: [{required: true}]}]"></a-input>
                </a-form-item>
                <a-form-item label="表描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['tableDesc', {rules: [{required: true}]}]"></a-input>
                </a-form-item>
            </a-form>
            <a-table :columns="tableColumns" :data-source="columnData" :row-key="record => record.key" :pagination="false" bordered>
                <template slot="title">
                    <a-button @click="addOneRow">添加</a-button>
                </template>

                <template slot="name" slot-scope="name,record">
                    <input-cell v-model="record.name"></input-cell>
                </template>

                <template slot="type" slot-scope="type,record">
                    <select-cell v-model="record.type" :items="[
                                {name: '字符串',value: 'varchar'},
                                {name: '整数',value: 'int'},
                                {name: '小数',value: 'double'},
                                {name: '布尔类型',value: 'bit'}
                            ]"></select-cell>
                </template>

                <template slot="length" slot-scope="length,record">
                    <input-number-cell v-model="record.length"></input-number-cell>
                </template>

                <template slot="nullable" slot-scope="nullable,record">
                    <checkbox-cell v-model="record.nullable"></checkbox-cell>
                </template>

                <template slot="defaultValue" slot-scope="defaultValue,record">
                    <input-cell v-model="record.defaultValue"></input-cell>
                </template>

                <template slot="comment" slot-scope="comment,record">
                    <input-cell v-model="record.comment"></input-cell>
                </template>
            </a-table>
        </a-modal>

        <a-modal
                title="从数据库已有表添加到业务表单"
                :visible="addFromDataTableVisible"
                :confirmLoading="confirmLoading"
                :width="600"
                @ok="saveFromDatabaseTable"
                @cancel="addFromDataTableVisible = false"
        >
            <a-select showSearch :filterOption="filterOption" v-model="databaseTable" labelInValue placeholder="请选择一个表" style="width: 100%;">
                <a-select-option v-for="table in databaseTables" :key="table.tableName" :value="table.tableName">{{table.tableDesc}}</a-select-option>
            </a-select>
        </a-modal>

        <a-modal
                title="确认要删除的数据项"
                :visible="deleteConfirmVisible"
                @ok="batchDelete"
                @cancel="deleteConfirmVisible = false"
        >
            <a-checkbox :checked="deleteItems.data" @change="(e) => deleteItems.data = e.target.checked">删除表数据</a-checkbox>
            <a-checkbox :checked="deleteItems.table" @change="(e) => deleteItems.table = e.target.checked">删除相关的数据库表</a-checkbox>
            <a-checkbox :checked="deleteItems.code" @change="(e) => deleteItems.code = e.target.checked">删除已生成的代码</a-checkbox>
        </a-modal>
    </div>
</template>

<script>
    import SearchForm from '@/components/form/Search';
    import InputCell from '@/components/table/editor/InputCell';
    import SelectCell from '@/components/table/editor/SelectCell';
    import InputNumberCell from '@/components/table/editor/InputNumberCell';
    import CheckboxCell from '@/components/table/editor/CheckboxCell';

    import ExtendTableService from '@/service/core/extend-table';

    import {mapMutations} from 'vuex';

    export default {
        name: "FormManagePage",
        components: {
            SearchForm,
            InputCell,
            SelectCell,
            InputNumberCell,
            CheckboxCell
        },
        data(){
            return {
                extendTableService: new ExtendTableService(),
                formItems: [
                    {component: 'a-input', field: 'moduleName', placeholder: '请输入模块名称'},
                    {component: 'a-input', field: 'tableName', placeholder: '请输入表名称'},
                    {component: 'a-select', field: 'area', placeholder: '请选择区域', data: [{name: 'item1', value: 1}]}
                ],
                columns: [
                    {title: 'ID', dataIndex: 'id'},
                    {title: '模块名称', dataIndex: 'moduleName', scopedSlots: { customRender: 'text' },},
                    {title: '表名称', dataIndex: 'tableName'},
                    {title: '描述', dataIndex: 'tableDesc'},
                    {title: '创建时间', dataIndex: 'createTime'},
                    {title: '操作', key: 'operation', fixed: 'right', width: 100, scopedSlots: { customRender: 'action' },}
                ],
                data: [],
                pagination: {},
                rowSelection: {selectedRowKeys: [], onChange: (selectedRowKeys) => this.rowSelection.selectedRowKeys = selectedRowKeys},

                loading: false,
                newTableVisible: false,
                confirmLoading: false,
                tableForm: this.$form.createForm(this),
                labelCol: {span: 4},
                wrapperCol: {span: 20},
                tableColumns: [
                    {title: '名称', dataIndex: 'name', scopedSlots: { customRender: 'name' }},
                    {title: '类型', dataIndex: 'type', scopedSlots: { customRender: 'type' }, width: 100},
                    {title: '长度', dataIndex: 'length', scopedSlots: { customRender: 'length' }},
                    {title: '为空', dataIndex: 'nullable', scopedSlots: { customRender: 'nullable' }},
                    {title: '默认值', dataIndex: 'defaultValue', scopedSlots: { customRender: 'defaultValue' }},
                    {title: '描述', dataIndex: 'comment', scopedSlots: { customRender: 'comment' }}
                ],
                columnData: [],

                addFromDataTableVisible: false,
                databaseTables: [],
                databaseTable: {},

                deleteConfirmVisible: false,
                deleteItems: {
                    data: false,
                    table: false,
                    code: false
                }
            }
        },
        computed: {
            hasSelectedRows(){
                return this.rowSelection.selectedRowKeys.length > 0;
            }
        },
        mounted(){
            this.search();
        },
        methods: {
            ...mapMutations([
                'loadingStart',
                'loadingEnd'
            ]),
            search(values){
                this.loading = true;
                this.extendTableService.selectPage().then(res => {
                    this.loading = false;
                    console.log(res);
                    this.data = res.data;
                })
            },
            buildTable(){
                this.tableForm.validateFields((errors, values) => {
                    if(!errors){
                        const tableInfo = values;
                        tableInfo.columns = this.columnData;
                        this.confirmLoading = true;
                        this.extendTableService.createTable(tableInfo).then(res => {
                            this.$message.info('创建完成！');
                            // this.newTableVisible = false;
                            this.confirmLoading = false;
                            this.search();
                        });
                    }
                })
            },
            addOneRow(){
                this.columnData.push({
                    key: new Date().getTime(),
                    name: 'test',
                    type: 'varchar',
                    length: 255,
                    nullable: true,
                    defaultValue: '',
                    comment: ''
                })
            },
            onCellChange(record, dataIndex, value){
                console.log(value);
                // const record = this.columnData.find(d => d.key == key);
                if(record) {
                    record[dataIndex] = value;
                }
            },
            addFromDataTable(){

                this.extendTableService.getDatabaseTables().then(res => {
                    this.databaseTables = res.data;
                    this.addFromDataTableVisible = true;
                });
            },
            filterOption(value, option){
                console.log(option);
                return option.componentOptions.children[0].text.toLowerCase().includes(value.toLowerCase());
            },
            saveFromDatabaseTable(){
                let tableName = this.databaseTable.key;
                const index = tableName.indexOf('_');
                if(index>0){
                    const moduleName = tableName.substring(0, index);
                    tableName = tableName.substring(index+1);

                    this.confirmLoading = true;
                    this.extendTableService.saveFromDatabaseTable({
                        moduleName,
                        tableName,
                        tableDesc: this.databaseTable.label
                    }).then(res => {
                        this.confirmLoading = false;
                        this.$message.success('保存成功！');
                        this.search();
                    });
                }else{
                    this.$message.error('表名不规范！');
                }
            },
            batchDelete(){
                this.extendTableService.batchDelete({
                    ids: this.rowSelection.selectedRowKeys,
                    deleteItems: this.deleteItems
                }).then(res => {
                    this.$success({
                        title: '删除成功！'
                    });
                    this.deleteConfirmVisible = false;
                });
            },
            createServerCode(record){
                const that = this;
                this.$confirm({title: '确认要生成服务端代码吗？', onOk(){
                        that.extendTableService.createServerCode(record.id).then(res => {
                            that.$success({title: '执行成功！', content: '代码已经生成到对应extend模块下，重启服务后生效！'});
                    });
                }});

            }
        }
    }
</script>

<style scoped>

</style>