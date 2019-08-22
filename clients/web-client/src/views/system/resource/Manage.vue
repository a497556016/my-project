<template>
    <div>
        <curd-page ref="page" :search-items="searchItems" :columns="columns" :load-fun="loadTableData" @add="addResource" @edit="editResource" @delete="deleteResource" :more-actions="moreActions">
            <a-button slot="assign" slot-scope="{text, record, index}" @click="assignPermission(record)" shape="circle" icon="diff"></a-button>
        </curd-page>

        <a-modal v-model="showEdit" @ok="saveResource">
            <resource-edit></resource-edit>
        </a-modal>
    </div>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";
    import ResourceEdit from "./Edit"

    import {createNamespacedHelpers} from 'vuex'

    const accountStore = createNamespacedHelpers("account");
    const resourceStore = createNamespacedHelpers("resource");
    import types from '../../../store/types'
    export default {
        name: "Manage",
        components: {CurdPage, ResourceEdit},
        data(){
            return {
                searchItems: [
                    {component: 'a-input', field: 'name', placeholder: '请输入资源名称或路径'}
                ],
                columns: [
                    {title: '名称', dataIndex: 'name'},
                    {title: '路径', dataIndex: 'path'},
                    {title: '类型', dataIndex: 'type'},
                    {title: '创建时间', dataIndex: 'createTime'},
                    {title: '创建人', dataIndex: 'creater'}
                ],

                showEdit: false,

                moreActions: [
                    // {text: '分配权限', handler(record){}},
                    // {text: '分配权限', handler(record){}}
                ]
            }
        },
        computed: {
            ...accountStore.mapGetters({
                userInfo: types.accountTypes.getters.GET_USER_INFO
            })
        },
        methods: {
            ...resourceStore.mapActions({
                loadTableData: types.resourceTypes.QUERY_LIST,
                saveEditResource: types.resourceTypes.SAVE_EDIT_RESOURCE,
                deleteResources: types.resourceTypes.DELETE_RESOURCES
            }),
            ...resourceStore.mapMutations({
                setEditResource: types.resourceTypes.SET_EDIT_RESOURCE_DATA
            }),
            assignPermission(record){
                console.log(record)
            },
            addResource(){
                this.setEditResource({
                    creater: this.userInfo.username
                });
                this.showEdit = true;
            },
            editResource(record){
                this.showEdit = true;
                record.modifier = this.userInfo.username;
                this.setEditResource(record);
            },
            saveResource(){
                this.saveEditResource().then(() => {
                    this.showEdit = false;
                    // this.$message.success("保存成功！");
                    this.$refs.page.reload();
                })
            },
            deleteResource(pageData, selectRows){
                this.deleteResources(selectRows).then(() => {
                    this.$refs.page.reload();
                });
            }
        }
    }
</script>

<style scoped>

</style>