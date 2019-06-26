<template>
    <curd-page :search-items="searchItems" :load-fun="loadTableData" :columns="columns"></curd-page>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";
    import emoticonService from '../../../api/tools/emoticon'
    export default {
        name: "EmoticonCatalog",
        components: {CurdPage},
        data(){
            return {
                searchItems: [
                    {component: 'a-input', field: 'catalog', label: '类别'}
                ],
                columns: [
                    {title: '类别', dataIndex: 'catalog'},
                    {title: '时间', dataIndex: 'time'}
                ]
            }
        },
        methods: {
            loadTableData(params){
                params.sortField = 'createTime';
                params.sortDirection = 'desc';
                return emoticonService.selectCatalogPage(params);
            }
        }
    }
</script>

<style scoped>

</style>