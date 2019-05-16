<template>
    <div>
        <curd-page ref="page" :search-items="searchItems" :columns="columns" :load-fun="loadTableData" @add="addRole" @edit="editRole" :more-actions="moreActions">
            <a-button slot="assign" slot-scope="{text, record, index}" @click="assignPermission(record)" shape="circle" icon="diff"></a-button>
        </curd-page>

        <a-modal v-model="showEdit" @ok="saveRole">
            <role-edit></role-edit>
        </a-modal>
    </div>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";
    import RoleEdit from "./Edit"

    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import {role, account} from '../../../store/types'
    export default {
        name: "Manage",
        components: {CurdPage, RoleEdit},
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
                loadTableData: role.QUERY_ROLE_LIST,
                saveEditRole: role.SAVE_EDIT_ROLE
            }),
            ...mapMutations({
                setEditRole: role.SET_EDIT_ROLE_DATA
            }),
            assignPermission(record){
                console.log(record)
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
            }
        }
    }
</script>

<style scoped>

</style>