<template>
    <div>
        <!--<search-form ref="searchForm" :items="searchItems" @submit="(values) => $refs.table.search(values)"></search-form>
        <m-table ref="table" auto-load :columns="table.columns" :load-fun="selectPage" :action-btns="table.actionBtns">
            <a href="#" slot="username" slot-scope="{value}">{{value}}</a>
            <template slot="avatar" slot-scope="{value}">
                <img v-if="value" :src="value" :style="{width: '50px', height: '50px'}" />
            </template>
            <a-button slot="action" slot-scope="{record}" @click="openUserEdit(record.id)">编辑</a-button>
        </m-table>-->

        <curd-page ref="page" :search-items="searchItems" :columns="table.columns" :load-fun="selectPage" @add="openUserAdd" @delete="batchDelete" @edit="(record) => openUserEdit(record.id)">
            <a href="#" slot="username" slot-scope="{text}">{{text}}</a>
            <template slot="avatar" slot-scope="{text}">
                <img v-if="text" :src="text" :style="{width: '50px', height: '50px'}" />
            </template>

            <a-button slot="assign" slot-scope="{record}" @click="assignRole(record)" shape="circle" icon="diff"></a-button>
        </curd-page>

        <add-modal :after-success="search"></add-modal>

        <edit-modal :after-success="search"></edit-modal>

        <a-modal title="分配角色" v-model="showAssignRole" @ok="saveAssignRoles">
            <assign-role></assign-role>
        </a-modal>
    </div>
</template>

<script>
    // import SearchForm from '@/components/form/Search'
    // import MTable from '@/components/table/Table'
    import EditModal from './Edit'
    import AddModal from './Add'

    import {mapMutations, mapActions, mapState, mapGetters} from 'vuex'
    import {role as roleTypes, user, userEdit, userAdd} from '@/store/types'
    import CurdPage from "../../../components/page/CurdPage";
    import AssignRole from "./AssignRole";

    const searchItems = [
        {component: 'a-input', field: 'username', placeholder: '请输入用户名'},
    ];
    const columns = [
        {title: 'ID', dataIndex: 'id'},
        {title: '账号', dataIndex: 'username', slot: 'username'},
        {title: '手机号码', dataIndex: 'phone'},
        {title: '邮箱', dataIndex: 'email'},
        {title: '性别', dataIndex: 'gender'},
        {title: '生日日期', dataIndex: 'birthday'},
        {title: '头像', dataIndex: 'avatar', slot: 'avatar'},
        {title: '角色分配', slot: 'assign', fixed: 'right', align: 'center'}
    ];
    export default {
        name: "UserManagePage",
        components: {
            AssignRole,
            CurdPage,
            // SearchForm, MTable,
            EditModal, AddModal},
        data(){
            const that = this;
            return {
                searchItems,
                table: {
                    columns,
                },

                showAssignRole: false
            }
        },
        computed: {
            ...mapGetters({
                // userAssignedInfo: user.getters.GET_USER_ASSIGNED_INFO
            })
        },
        mounted() {

        },
        methods: {
            ...mapActions({
                selectPage: user.actions.SELECT_PAGE,
                openUserEdit: userEdit.actions.OPEN_USER_EDIT,
                // batchDelete: user.actions.BATCH_DELETE,
                assignUserRoles: user.actions.ASSIGN_USER_ROLES,
                queryRoleList: roleTypes.QUERY_ROLE_LIST
            }),
            ...mapMutations({
                openUserAdd: userAdd.OPEN_ADD,
                setSelectedKeys: user.mutations.SET_SELECTED_KEYS,
                setAssignRoleUser: user.mutations.SET_ASSIGN_ROLE_USER,
                setRoleList: user.mutations.SET_ROLE_LIST
            }),
            search(){
                this.$refs.page.reload();
            },
            batchDelete(pageData, selectRows){
                this.setSelectedKeys(selectRows);
                this.$confirm({
                    title: '确认要删除吗？',
                    onOk(){
                        this.$store.dispatch(user.actions.BATCH_DELETE).then(res => {
                            this.$message.success('删除完成！');
                            this.search();
                        })
                    }
                });
            },
            assignRole(user){
                console.log(user)
                this.setAssignRoleUser(user);
                this.queryRoleList({
                    current: 1,
                    size: 10
                }).then(res => {
                    if(res.code == 1){
                        this.setRoleList(res.data);

                        this.showAssignRole = true;
                    }
                });
            },
            saveAssignRoles(){
                // console.log(this.userAssignedInfo);
                this.assignUserRoles().then(res => {
                    this.$message.success("角色分配完成！");
                });
            }
        }
    }
</script>

<style scoped>

</style>