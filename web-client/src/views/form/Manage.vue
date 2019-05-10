<template>
    <div>
        <curd-page
                :search-items="searchItems"
                :columns="columns"
                :load-fun="loadTableData"
                :key-field="'id.counter'"
                @add="onAdd"
                @delete="onDelete"
                @edit="onEdit"
        >

        </curd-page>
    </div>
</template>

<script>
    import {mapState, mapMutations, mapActions} from 'vuex'

    import {formManage as formTypes, layout as layoutTypes} from '@/store/types'
    import CurdPage from "../../components/page/CurdPage";

    export default {
        name: "Manage",
        components: {CurdPage},
        data() {
            return {
                searchItems: [
                    {component: 'a-input', field: 'fileName', placeholder: '请输入文件名称'}
                ],
                columns: [
                    {title: '名称', dataIndex: 'title'},
                    {title: '描述', dataIndex: 'desc'},
                    {title: '创建时间', dataIndex: 'id.date'},
                    {title: '创建用户', dataIndex: 'createBy'}
                ]
            }
        },
        computed: {},
        mounted() {

        },
        methods: {
            ...mapActions({
                loadTableData: formTypes.LOAD_TABLE_DATA
            }),
            ...mapMutations({
                addMenuTab: layoutTypes.ADD_MENU_TAB
            }),
            onAdd(){
                this.addMenuTab('FormCreate');
            },
            onDelete(data, selectRows) {
                console.log(selectRows);
            },
            onEdit(record) {
                console.log(record);
            }
        }
    }
</script>

<style scoped>

</style>