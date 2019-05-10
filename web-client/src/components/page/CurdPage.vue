<template>
    <div>
        <search-form ref="searchForm" :items="searchItems" @submit="search"></search-form>

        <m-table ref="table" auto-load :load-fun="loadFun" :columns="columns" :key-field="keyField" :action-btns="actionBtns">
            <a-button slot="action" slot-scope="{record}" @click="edit(record)">编辑</a-button>
        </m-table>
    </div>
</template>

<script>
    import SearchForm from '@/components/form/Search'
    import MTable from '@/components/table/Table'

    export default {
        name: "CurdPage",
        components: {SearchForm, MTable},
        props: {
            searchItems: Array,
            columns: Array,
            keyField: {
                type: String,
                default: 'id'
            },
            loadFun: Function,
            actionBtns: {
                type: Array,
                default() {
                    const that = this;
                    return [
                        {
                            type: 'primary', text: '添加', handler(pageData) {
                                console.log(pageData);
                                that.$emit('add', pageData);
                            }
                        },
                        {
                            type: 'danger', text: '删除', handler(pageData, selectedRowKeys) {
                                const selectRows = pageData.filter(d => selectedRowKeys.includes(eval('d.'+that.keyField)));
                                console.log(selectRows);
                                that.$emit('delete', pageData, selectRows);
                            },
                            disable(selectedRowKeys, pageData) {
                                if(!selectedRowKeys||selectedRowKeys.length==0){
                                    return true;
                                }
                                return false;
                            }
                        }
                    ]
                }
            }
        },
        data(){
            return {

            }
        },
        computed: {

        },
        mounted(){
            this.buildRowAction();
        },
        methods: {
            buildRowAction() {
                this.columns.push({title: '操作', slot: 'action', fixed: 'right'})
            },
            edit(record){
                this.$emit('edit', record);
            },
            search(params){
                this.$refs.table.search(params);
            }
        }
    }
</script>

<style scoped>

</style>