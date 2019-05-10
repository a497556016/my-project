<template>
    <div>
        <curd-page :search-items="searchItems" :columns="columns" :load-fun="loadTableData"></curd-page>
    </div>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";

    import {mapActions} from 'vuex'
    import {formManage} from '../../../store/types'
    export default {
        name: "Manage",
        components: {CurdPage},
        data(){
            return {
                searchItems: [
                    {component: 'a-input', field: 'name', placeholder: '请输入角色名称'}
                ],
                columns: [
                    {title: '名称', dataIndex: 'name'}
                ]
            }
        },
        methods: {
            ...mapActions({
                loadTableData: formManage.LOAD_TABLE_DATA
            })
        }
    }
</script>

<style scoped>

</style>