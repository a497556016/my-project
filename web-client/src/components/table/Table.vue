<template>
    <a-table
            :columns="_columns"
            :data-source="dataSource"
            :row-key="record => record[keyField]"
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
                          @click="btn.handler?btn.handler(currentPageData):null">
                    <a-icon v-if="btn.icon" :type="btn.icon"></a-icon>
                    {{btn.text}}
                </a-button>
            </div>
        </template>
        <template v-for="s in slots" :slot="s+'_outer'" slot-scope="d, record">
            <slot :name="s" :record="record" :value="d"></slot>
        </template>
    </a-table>
</template>

<script>
    import Vue from 'vue'
    export default {
        name: "Table",
        props: {
            columns: Array,
            data: Array,
            pageSize: {
                type: Number,
                default: 10
            },
            keyField: {
                type: String,
                default: 'id'
            },
            loadFun: Function,
            actionBtns: Array
        },
        data(){
            return {
                dataSource: this.data,
                rowSelection: {selectedRowKeys: [], onChange: (selectedRowKeys) => this.rowSelection.selectedRowKeys = selectedRowKeys},
                pagination: {
                    showQuickJumper: true,
                    showSizeChanger: true,
                    current: 1,
                    pageSize: this.pageSize,
                    total: 0,
                    showTotal: (t) => `共${t}条`
                },
                loading: false,
                where: {}
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
            this.load(1);
            const tableContents = document.getElementsByClassName('ant-table-body');
            console.log(tableContents);
            if(tableContents.length > 0) {
                tableContents[0].style.overflowX='auto';
            }
        },
        methods: {
            load(current){
                if(this.loadFun){
                    this.loading = true;
                    this.loadFun(Object.assign({
                        current: current || this.pagination.current,
                        size: this.pagination.pageSize
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