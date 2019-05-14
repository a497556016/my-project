<template>
    <div>

        <search-form ref="searchForm" :items="searchItems" @submit="search"></search-form>

        <m-table ref="table" auto-load :load-fun="loadFun" :columns="columns" :key-field="keyField" :action-btns="toolBtns">
            <template slot="action" slot-scope="{record}">
                <a-button v-for="btn in actionBtns" @click="btn.handler(record)">{{btn.text}}</a-button>
            </template>
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
            toolBtns: {
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
            },
            moreActions: {
                type: Array,
                default(){
                    return []
                }
            }
        },
        data(){
            return {
                actionBtns: [
                    {type: 'default', text: '编辑', handler: this.edit},
                    ...this.moreActions
                ]
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