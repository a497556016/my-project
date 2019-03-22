<template>
    <div>
        <search-form ref="searchForm" :items="searchItems" @submit="(values) => $refs.table.search(values)"></search-form>
        <m-table ref="table" :columns="table.columns" :load-fun="selectPage" :action-btns="table.actionBtns">
            <a href="#" slot="username" slot-scope="{value}">{{value}}</a>
            <template slot="avatar" slot-scope="{value}">
                <img v-if="value" :src="value" :style="{width: '50px', height: '50px'}" />
            </template>
            <a-button slot="action" slot-scope="{record}" @click="openUserEdit(record.id)">编辑</a-button>
        </m-table>

        <add-modal :after-success="search"></add-modal>

        <edit-modal :after-success="search"></edit-modal>
    </div>
</template>

<script>
    import SearchForm from '@/components/form/Search'
    import MTable from '@/components/table/Table'
    import EditModal from './Edit'
    import AddModal from './Add'

    import {mapMutations, mapActions, mapState, mapGetters} from 'vuex'
    import {user, userEdit, userAdd} from '@/store/types'

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
        {title: '操作', slot: 'action', fixed: 'right'}
    ];
    export default {
        name: "UserManagePage",
        components: {SearchForm, MTable, EditModal, AddModal},
        data(){
            const that = this;
            return {
                searchItems,
                table: {
                    columns,
                    actionBtns: [
                        {type: 'primary', text: '添加', icon: 'plus', handler: this.openUserAdd},
                        {type: 'danger', text: '批量删除', icon: 'delete',
                            disable(selectedRowKeys, currentPageData) {
                                that.setSelectedKeys(selectedRowKeys);
                                return selectedRowKeys.length == 0;
                            },
                            handler: that.batchDelete
                        }
                    ]
                },
            }
        },
        computed: {

        },
        mounted() {

        },
        methods: {
            ...mapActions({
                selectPage: user.actions.SELECT_PAGE,
                openUserEdit: userEdit.actions.OPEN_USER_EDIT,
                batchDelete: user.actions.BATCH_DELETE
            }),
            ...mapMutations({
                openUserAdd: userAdd.OPEN_ADD,
                setSelectedKeys: user.mutations.SET_SELECTED_KEYS
            }),
            search(){
                this.$refs.table.search(this.$refs.searchForm.form.getFieldsValue());
            },
            batchDelete(){
                this.$confirm({
                    title: '确认要删除吗？',
                    onOk(){
                        this.$store.dispatch(user.actions.BATCH_DELETE).then(res => {
                            this.$message.success('删除完成！');
                            this.search();
                        })
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>