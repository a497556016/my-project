<template>
    <div>
        <curd-page ref="page" :search-items="searchItems" :columns="columns" :load-fun="loadTableData" @add="addPermission" @edit="editPermission" @delete="deletePermission" :more-actions="moreActions">
            <a-button slot="assign" slot-scope="{text, record, index}" @click="assignPermission(record)" shape="circle" icon="diff"></a-button>
        </curd-page>

        <a-modal title="编辑权限" v-model="showEdit" @ok="savePermission">
            <permission-edit></permission-edit>
        </a-modal>

        <a-modal title="分配资源" v-model="showAssignResource" @ok="saveAssignedResources">
            <assign-resource></assign-resource>
        </a-modal>
    </div>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";
    import PermissionEdit from "./Edit"
    import AssignResource from "./AssignResource"

    import {createNamespacedHelpers} from 'vuex'
    const permissionStore = createNamespacedHelpers("permission");
    const accountStore = createNamespacedHelpers("account");
    // import {permission} from '../../../store/types'
    import types from '../../../store/types'
    const {permissionTypes, accountTypes} = types;
    export default {
        name: "Manage",
        components: {CurdPage, PermissionEdit, AssignResource},
        data(){
            return {
                searchItems: [
                    {component: 'a-input', field: 'name', placeholder: '请输入权限名称'}
                ],
                columns: [
                    {title: '名称', dataIndex: 'name'},
                    {title: '编码', dataIndex: 'code'},
                    {title: '创建时间', dataIndex: 'createTime'},
                    {title: '创建人', dataIndex: 'creater'},
                    {title: '分配资源', dataIndex: 'id', width: 100, slot: 'assign', align: 'center'}
                ],

                showEdit: false,

                showAssignResource: false,

                moreActions: [
                    // {text: '分配权限', handler(record){}},
                    // {text: '分配权限', handler(record){}}
                ]
            }
        },
        computed: {
            ...accountStore.mapGetters({
                userInfo: accountTypes.getters.GET_USER_INFO
            })
        },
        methods: {
            ...permissionStore.mapActions({
                loadTableData: permissionTypes.QUERY_PERMISSION_RESOURCE_LIST,
                saveEditPermission: permissionTypes.SAVE_EDIT_PERMISSION,
                saveAssignedResources: permissionTypes.SAVE_ASSIGNED_RESOURCES,
                setAssignedResources: permissionTypes.SET_ASSIGNED_RESOURCES,
                deletePermissions: permissionTypes.DELETE_PERMISSIONS
            }),
            ...permissionStore.mapMutations({
                setEditPermission: permissionTypes.SET_EDIT_PERMISSION_DATA
            }),
            assignPermission(record){
                console.log(record)
                this.setAssignedResources(record).then(() => {
                    this.showAssignResource = true;
                });
            },
            addPermission(){
                this.setEditPermission({
                    creater: this.userInfo.username
                });
                this.showEdit = true;
            },
            editPermission(record){
                record.modifier = this.userInfo.username;
                this.setEditPermission(record);
                this.showEdit = true;
            },
            savePermission(){
                this.saveEditPermission().then(() => {
                    this.$refs.page.reload();
                    this.showEdit = false;
                })
            },
            async deletePermission(pageData, selectKeys){
                await this.deletePermissions(selectKeys);
                this.$refs.page.reload();
            }
        }
    }
</script>

<style scoped>

</style>