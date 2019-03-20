<template>
    <div>
        <search-form ref="searchForm" :items="searchItems" @submit="(values) => $refs.table.search(values)"></search-form>
        <m-table ref="table" :columns="table.columns" :load-fun="selectPage" :action-btns="table.actionBtns">
            <a href="#" slot="username" slot-scope="{value}">{{value}}</a>
            <img slot="avatar" slot-scope="{value}" :src="value" :style="{width: '50px', height: '50px'}" />
            <a-button slot="action" slot-scope="{record}" @click="openUserEdit(record.id)">编辑</a-button>
        </m-table>

        <a-modal title="添加用户" v-model="addUserVisible" @ok="addUserSubmit">
            <a-form :form="addUserForm">
                <a-form-item label="用户名" :label-col="formItemLayout.labelCol" :wrapper-col="formItemLayout.wrapperCol">
                    <a-input v-decorator="['username', {rules: [{required: true, message: '请输入用户名！'}]}]"/>
                </a-form-item>
                <a-form-item label="密码" :label-col="formItemLayout.labelCol" :wrapper-col="formItemLayout.wrapperCol">
                    <a-input type="password" v-decorator="['password', {rules: [{required: true, message: '请输入密码！'}]}]"/>
                </a-form-item>
                <a-form-item label="手机" :label-col="formItemLayout.labelCol" :wrapper-col="formItemLayout.wrapperCol">
                    <a-input v-decorator="['phone', {rules: [{required: true, message: '请输入手机！'}]}]"/>
                </a-form-item>
                <a-form-item label="邮箱" :label-col="formItemLayout.labelCol" :wrapper-col="formItemLayout.wrapperCol">
                    <a-input v-decorator="['email', {rules: []}]"/>
                </a-form-item>
                <a-form-item label="性别" :label-col="formItemLayout.labelCol" :wrapper-col="formItemLayout.wrapperCol">
                    <a-radio-group v-decorator="['gender', {rules: [], initialValue: '0'}]" buttonStyle="solid">
                        <a-radio-button value="1">男</a-radio-button>
                        <a-radio-button value="0">女</a-radio-button>
                    </a-radio-group>
                </a-form-item>
                <a-form-item label="生日日期" :label-col="formItemLayout.labelCol" :wrapper-col="formItemLayout.wrapperCol">
                    <a-date-picker v-decorator="['birthday', {rules: []}]"></a-date-picker>
                </a-form-item>
                <a-form-item label="上传头像" :label-col="formItemLayout.labelCol" :wrapper-col="formItemLayout.wrapperCol">
                    <a-upload
                            name="avatar"
                            listType="picture-card"
                            class="avatar-uploader"
                            :showUploadList="false"
                            :customRequest="upload"
                            @change="onSelectFile"
                    >
                        <img v-if="imageUrl" :src="imageUrl" alt="avatar" />
                        <div v-else>
                            <a-icon :type="uploading ? 'loading' : 'plus'" />
                            <div class="ant-upload-text">点击上传</div>
                        </div>
                    </a-upload>
                </a-form-item>
            </a-form>
        </a-modal>

        <edit-modal @reloadTable="search"></edit-modal>
    </div>
</template>

<script>
    import SearchForm from '@/components/form/Search'
    import MTable from '@/components/table/Table'
    import AFormItem from "ant-design-vue/es/form/FormItem";
    import ARadioGroup from "ant-design-vue/es/radio/Group";
    import EditModal from './Edit'

    import {mapMutations, mapActions, mapState, mapGetters} from 'vuex'
    import {account, user, userEdit, file} from '@/store/types'

    function getBase64 (img, callback) {
        const reader = new FileReader()
        reader.addEventListener('load', () => callback(reader.result))
        reader.readAsDataURL(img)
    }

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
        components: {ARadioGroup, AFormItem, SearchForm, MTable, EditModal},
        data(){
            return {
                searchItems,
                table: {
                    columns,
                    actionBtns: [
                        {type: 'primary', text: '添加', icon: 'plus', handler: this.addUser},
                        {type: 'danger', text: '批量删除', icon: 'delete', disable(selectedRowKeys, currentPageData) {
                                return selectedRowKeys.length == 0;
                            }
                        }
                    ]
                },

                addUserVisible: false,
                addUserForm: this.$form.createForm(this),
                formItemLayout: {
                    labelCol: {span: 4},
                    wrapperCol: {span: 18, offset: 2}
                },

                uploading: false
            }
        },
        computed: {
            ...mapState({
                uploadedFile: state => state.file.uploadedFile
            }),
            ...mapGetters({
                imageUrl: file.getters.FILE_DOWNLOAD_PATH
            })
        },
        mounted() {

        },
        methods: {
            ...mapActions({
                saveUser: account.actions.REGISTER_USER,
                selectPage: user.actions.SELECT_PAGE,
                upload: file.actions.UPLOAD,
                openUserEdit: userEdit.actions.OPEN_USER_EDIT
            }),
            ...mapMutations({

            }),
            addUser(){
                this.addUserVisible = true;
            },
            search(){
                this.$refs.table.search(this.$refs.searchForm.form.getFieldsValue());
            },
            addUserSubmit(){
                this.addUserForm.validateFields((err, values) => {
                    if (!err) {
                        values.birthday = values.birthday.format('YYYY-MM-DD');
                        values.avatar = this.uploadedFile.path;
                        this.saveUser(values).then(res => {
                            this.addUserVisible = false;
                            this.search();
                        });
                    }
                });
            },
            onSelectFile(info){
                if (info.file.status === 'uploading') {
                    this.uploading = true
                    return
                }
                if (info.file.status === 'done') {
                    // Get this url from response in real world.
                    getBase64(info.file.originFileObj, (imageUrl) => {
                        this.imageUrl = imageUrl
                        this.uploading = false
                    })
                }
            }
        }
    }
</script>

<style scoped>

</style>