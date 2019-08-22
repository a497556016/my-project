<template>
    <div>
        <curd-page ref="page" :search-items="searchItems" :columns="columns" :load-fun="loadTableData" @add="addRole" @edit="editRole" @delete="deleteRole" :more-actions="moreActions">
            <a-button slot="assign" slot-scope="{text, record, index}" @click="assignPermission(record)" shape="circle" icon="diff"></a-button>
        </curd-page>

        <a-modal v-model="showEdit" @ok="saveRole">
            <role-edit></role-edit>
        </a-modal>

        <a-modal title="分配权限" v-model="showAssignPermission" @ok="saveAssignPermissions">
            <assign-permission></assign-permission>
        </a-modal>
    </div>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";
    import RoleEdit from "./Edit"

    import {createNamespacedHelpers} from 'vuex'
    const roleStore = createNamespacedHelpers("role");
    const accountStore = createNamespacedHelpers("account");
    const permissionStore = createNamespacedHelpers("permission");
    import types from '../../../store/types'
    const {roleTypes, accountTypes, permissionTypes} = types;
    import AssignPermission from "./AssignPermission";
    export default {
        name: "Manage",
        components: {AssignPermission, CurdPage, RoleEdit},
        data(){
            return {
                searchItems: [
                    {component: 'a-input', field: 'name', placeholder: '请输入角色名称'}
                ],
                columns: [
                    {title: '名称', dataIndex: 'name'},
                    {title: '编码', dataIndex: 'code'},
                    {title: '创建时间', dataIndex: 'createTime'},
                    {title: '创建人', dataIndex: 'creater'},
                    {title: '分配权限', dataIndex: 'id', width: 100, slot: 'assign', align: 'center'}
                ],

                showEdit: false,

                moreActions: [
                    // {text: '分配权限', handler(record){}},
                    // {text: '分配权限', handler(record){}}
                ],

                showAssignPermission: false
            }
        },
        computed: {
            ...accountStore.mapGetters({
                userInfo: accountTypes.getters.GET_USER_INFO
            })
        },
        methods: {
            ...roleStore.mapActions({
                loadTableData: roleTypes.QUERY_ROLE_LIST,
                saveEditRole: roleTypes.SAVE_EDIT_ROLE,
                saveAssignPermissions: roleTypes.SAVE_ASSIGNED_PERMISSIONS,
                deleteRoles: roleTypes.DELETE_ROLES
            }),
            ...permissionStore.mapActions({
                queryPermissions: permissionTypes.QUERY_LIST,
            }),
            ...roleStore.mapMutations({
                setEditRole: roleTypes.SET_EDIT_ROLE_DATA,
                setAssignPermissionRole: roleTypes.SET_ASSIGN_PERMISSION_ROLE,
                setAssignedPermissions: roleTypes.SET_ASSIGNED_PERMISSIONS
            }),
            assignPermission(record){
                console.log(record)
                this.setAssignPermissionRole(record);
                this.queryPermissions().then(res => {
                    this.setAssignedPermissions(res.data);

                    this.showAssignPermission = true;
                })
            },
            addRole(){
                this.setEditRole({
                    creater: this.userInfo.username
                });
                this.showEdit = true;
            },
            editRole(record){
                this.showEdit = true;
                record.modifier = this.userInfo.username;
                this.setEditRole(record);
            },
            saveRole(){
                this.saveEditRole().then(res => {
                    this.showEdit = false;
                    this.$message.success("保存成功！");
                    this.$refs.page.reload();
                })
            },
            async deleteRole(data, selectKeys) {
                await this.deleteRoles(selectKeys);
                this.$refs.page.reload();
            }
        }
    }
</script>

<style scoped>

</style>