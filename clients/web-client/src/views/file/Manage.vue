<template>
    <div>
        <search-form ref="searchForm" :items="searchItems" @submit="query"></search-form>

        <m-table ref="table" :load-fun="query" :columns="columns"></m-table>
    </div>
</template>

<script>
    import {mapState, mapMutations, mapActions} from 'vuex'
    import SearchForm from '@/components/form/Search'
    import MTable from '@/components/table/Table'

    import {fileManage} from '../../store/types'
    export default {
        name: "Manage",
        components: {SearchForm, MTable},
        data(){
            return {
                searchItems: [
                    {component: 'a-input', field: 'fileName', placeholder: '请输入文件名称'}
                ],
                columns: [
                    {title: '文件名称', dataIndex: 'name'},
                    {title: '相对路径', dataIndex: 'path'},
                    {title: '文件类型', dataIndex: 'contentType'},
                    {title: '占用空间', dataIndex: 'size'},
                    {title: '上传时间', dataIndex: 'createTime'}
                ]
            }
        },
        computed: {

        },
        mounted(){
            this.search();
        },
        methods: {
            ...mapActions({
                query: fileManage.actions.QUERY
            }),
            search(param){
                this.$refs.table.search(param);
            }
        }
    }
</script>

<style scoped>

</style>