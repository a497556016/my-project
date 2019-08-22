<template>
    <div>
        <curd-page ref="page" :search-items="searchItems" :columns="columns" :load-fun="loadTableData" @add="addPermission" @edit="editPermission" :more-actions="moreActions">
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

    import {mapState, mapGetters, mapActions, mapMutations} from 'vuex'
    import {permission, account} from '../../../store/types'
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
            ...mapGetters({
                userInfo: account.getters.GET_USER_INFO
            })
        },
        methods: {
            ...mapActions({
                loadTableData: permission.QUERY_PERMISSION_RESOURCE_LIST,
                saveEditPermission: permission.SAVE_EDIT_PERMISSION,
                saveAssignedResources: permission.SAVE_ASSIGNED_RESOURCES,
                setAssignedResources: permission.SET_ASSIGNED_RESOURCES
            }),
            ...mapMutations({
                setEditPermission: permission.SET_EDIT_PERMISSION_DATA
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
            }
        }
    }
</script>

<style scoped>

</style>