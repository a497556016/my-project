<template>
    <div>

        <search-form ref="searchForm" :items="searchItems" @submit="search"></search-form>

        <m-table ref="table" auto-load :load-fun="loadFun" :columns="columns" :key-field="keyField" :action-btns="toolBtns">
            <template v-for="slot in slots" :slot="slot" slot-scope="{text, record, index}">
                <slot :name="slot" :text="text" :record="record" :index="index"></slot>
            </template>
            <template v-if="moreActions.length > 0" slot="action" slot-scope="{record}">
                <a-button v-for="btn in moreActions" :type="btn.type" :icon="btn.icon" :shape="btn.shape" @click="btn.handler(record)">{{btn.text}}</a-button>
            </template>
            <a-button slot="edit" slot-scope="{record}" type="primary" shape="circle" icon="edit" @click="edit(record)"></a-button>
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
                slots: []
            }
        },
        computed: {

        },
        mounted(){
            this.buildRowAction();
        },
        methods: {
            buildRowAction() {
                //自定义列
                this.slots = this.columns.filter(col => col.slot).map(col => col.slot);

                //操作列
                if(this.moreActions.length>0) {
                    let width = 0;
                    this.moreActions.forEach(btn => {
                        width += (btn.text.length * 30 + 5);
                    })
                    this.columns.push({title: '操作', width: width, slot: 'action', fixed: 'right', align: 'center'})
                }

                //编辑操作列
                this.columns.push({title: '编辑', width: 65, slot: 'edit', fixed: 'right', align: 'center'})
            },
            edit(record){
                this.$emit('edit', record);
            },
            search(params){
                this.$refs.table.search(params);
            },
            reload(){
                const params = this.$refs.searchForm.getValues();
                console.log('搜索参数',params);
                this.search(params);
            }
        }
    }
</script>

<style scoped>

</style>