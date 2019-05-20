<template>
    <a-table
            :columns="_columns"
            :data-source="dataSource"
            :row-key="getRowKey"
            :rowSelection="rowSelection"
            :pagination="pagination"
            :loading="loading"
            @change="tableChange"
            bordered
            >
        <template :slot="actionBtns?'title':''" slot-scope="currentPageData">
            <div class="space-between">
                <a-button v-for="btn in actionBtns"
                          :type="btn.type"
                          :disabled="btn.disable ? btn.disable(rowSelection.selectedRowKeys, currentPageData) : false"
                          @click="btn.handler?btn.handler(currentPageData, rowSelection.selectedRowKeys):null">
                    <a-icon v-if="btn.icon" :type="btn.icon"></a-icon>
                    {{btn.text}}
                </a-button>
            </div>
        </template>
        <template v-for="s in slots" :slot="s+'_outer'" slot-scope="text, record, index">
            <slot :name="s" :record="record" :text="text" :index="index"></slot>
        </template>
    </a-table>
</template>

<script>
    const defaultPageSize = 10;
    const maxPageSize = 1000;
    export default {
        name: "Table",
        props: {
            columns: Array,
            data: Array,
            page: {
                type: Object,
                default(){
                    return {
                        showQuickJumper: true,
                        showSizeChanger: true,
                        current: 1,
                        pageSize: defaultPageSize,
                        total: 0,
                        showTotal: (t) => `共${t}条`
                    }
                }
            },
            pageSize: {
                type: Number,
                default: defaultPageSize
            },
            keyField: {
                type: String,
                default: 'id'
            },
            autoLoad: Boolean,
            loadFun: Function,
            actionBtns: Array
        },
        model: {
            prop: 'data',
            event: 'change'
        },
        data(){
            return {
                dataSource: this.data,
                rowSelection: {selectedRowKeys: [], onChange: (selectedRowKeys) => this.rowSelection.selectedRowKeys = selectedRowKeys},
                pagination: this.page,
                loading: false,
                where: {}
            }
        },
        watch: {
            dataSource: {
                handler(data){
                    this.$emit('update:change', data);
                },
                deep: true
            },
            data: {
                handler(data){
                    this.dataSource = data;
                },
                deep: true
            }
        },
        computed: {
            _columns(){
                return this.columns.map(col => {
                    if(col.slot){
                        col.scopedSlots = { customRender: col.slot+'_outer' }
                    }
                    if(col.fixed && !col.width) {
                        col.width = 100;
                    }
                    return col;
                });
            },
            slots(){
                return this.columns.filter(col => col.slot).map(col => col.slot);
            }
        },
        mounted(){
            console.log(this)
            if(this.autoLoad) { //自动加载
                this.load(1);
            }
            const tableContents = document.getElementsByClassName('ant-table-body');
            console.log(tableContents);
            if(tableContents.length > 0) {
                tableContents[0].style.overflowX='auto';
            }
        },
        methods: {
            getRowKey(record) {
                const rowKey = eval('record.'+this.keyField);
                // console.log(rowKey);
                return rowKey;
            },
            load(current){
                if(this.loadFun){
                    this.loading = true;
                    this.loadFun(Object.assign({
                        current: current || (this.pagination?this.pagination.current:1),
                        size: this.pagination?this.pagination.pageSize:maxPageSize
                    }, this.where)).then((res) => {
                        this.loading = false;
                        this.dataSource = res.data || res.content;
                        this.pagination.total = res.total || res.totalElements;
                    });
                }
            },
            tableChange(pagination, filters, sorter){
                this.pagination = pagination;
                this.load();
            },
            search(params) {
                this.where = params;
                this.load(1);
            }
        }
    }
</script>

<style scoped>

</style>