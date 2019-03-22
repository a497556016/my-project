<template>
    <a-modal title="添加用户" :visible="visible" @ok="addUserSubmit" @cancel="closeUserAdd">
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
                        :showUploadList="false"
                        :customRequest="upload"
                >
                    <img v-if="imageUrl" :src="imageUrl" alt="avatar" />
                    <div v-else>
                        <a-icon :type="'plus'" />
                        <div>点击上传</div>
                    </div>
                </a-upload>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapMutations, mapActions, mapState, mapGetters} from 'vuex'
    import {account, user, userAdd, file} from '@/store/types'

    export default {
        name: "Add",
        props: {
            afterSuccess: Function
        },
        data(){
            return {
                addUserForm: this.$form.createForm(this),
                formItemLayout: {
                    labelCol: {span: 4},
                    wrapperCol: {span: 18, offset: 2}
                }
            }
        },
        computed: {
            ...mapState({
                uploadedFile: state => state.file.uploadedFile,
                visible: state => state.userAdd.addModalVisible
            }),
            ...mapGetters({
                imageUrl: file.getters.FILE_DOWNLOAD_PATH
            })
        },
        methods: {
            ...mapActions({
                saveUser: account.actions.REGISTER_USER,
                upload: file.actions.UPLOAD
            }),
            ...mapMutations({
                closeUserAdd: userAdd.CLOSE_ADD
            }),
            addUserSubmit(){
                this.addUserForm.validateFields((err, values) => {
                    if (!err) {
                        if(values.birthday)
                        values.birthday = values.birthday.format('YYYY-MM-DD');
                        values.avatar = this.uploadedFile.path;
                        values.thumbAvatar = this.uploadedFile.thumbPath;
                        this.saveUser(values).then(res => {
                            this.closeUserAdd();
                            this.afterSuccess();
                        });
                    }
                });
            },
            // onSelectFile(info){
            //     if (info.file.status === 'uploading') {
            //         this.uploading = true
            //         return
            //     }
            //     if (info.file.status === 'done') {
            //         // Get this url from response in real world.
            //         getBase64(info.file.originFileObj, (imageUrl) => {
            //             this.imageUrl = imageUrl
            //             this.uploading = false
            //         })
            //     }
            // }
        }
    }
</script>

<style scoped>

</style>