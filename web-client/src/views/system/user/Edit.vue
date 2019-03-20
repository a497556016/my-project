<template>
    <a-modal title="编辑用户" :visible="openEdit" @cancel="closeEdit" @ok="handleSubmit">
        <a-form :form="form">
            <a-form-item label="用户名" v-bind="formItemLayout">
                <a-input disabled v-decorator="['username', {rules: [{required: true, message: '请输入用户名！'}]}]"></a-input>
            </a-form-item>
            <a-form-item label="密码" v-bind="formItemLayout">
                <a-input v-decorator="['password', {rules: []}]"></a-input>
            </a-form-item>
            <a-form-item label="确认密码" v-bind="formItemLayout">
                <a-input v-decorator="['confirmPassword', {rules: [{validator: confirmPassword, trigger: 'blur'}]}]"></a-input>
            </a-form-item>
            <a-form-item label="手机" v-bind="formItemLayout">
                <a-input v-decorator="['phone', {rules: [{required: true, message: '请输入手机！'}]}]"/>
            </a-form-item>
            <a-form-item label="邮箱" v-bind="formItemLayout">
                <a-input v-decorator="['email', {rules: []}]"/>
            </a-form-item>
            <a-form-item label="性别" v-bind="formItemLayout">
                <a-radio-group v-decorator="['gender', {initialValue: '0', rules: []}]" buttonStyle="solid">
                    <a-radio-button value="1">男</a-radio-button>
                    <a-radio-button value="0">女</a-radio-button>
                </a-radio-group>
            </a-form-item>
            <a-form-item label="生日日期" v-bind="formItemLayout">
                <a-date-picker v-decorator="['birthday', {rules: []}]"></a-date-picker>
            </a-form-item>
            <a-form-item label="上传头像" v-bind="formItemLayout">
                <a-upload
                        listType="picture-card"
                        :showUploadList="false"
                        :customRequest="upload"
                >
                    <img v-if="avatarPath" :src="avatarPath" style="width: 100%;height: 90px;" />
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
    import {mapGetters, mapState, mapMutations, mapActions} from 'vuex'

    import {userEdit, file} from '@/store/types'

    import util from '@/util'

    export default {
        name: "Edit",
        data(){
            return {
                // form: this.$form.createForm(this),
                formItemLayout: {
                    labelCol: { span: 6 },
                    wrapperCol: { span: 18 },
                },
            }
        },
        watch: {
            uploadedFile(file){
                this.editUserInfo.avatar = file.path;
            },
            editUserInfo(user) {
                this.form.setFieldsValue(user);
            }
        },
        created(){
            this.form = this.$form.createForm(this, {
                onFieldsChange: (_, changedFields) => {
                    this.$emit('change', changedFields);
                },
                mapPropsToFields: () => {
                    const fields = {};
                    for(let f in this.editUserInfo){
                        fields[f] = this.$form.createFormField({
                            value: this.editUserInfo[f],
                        })
                    }
                    return fields;
                },
                onValuesChange: (_, values) =>{
                    console.log(values);
                    // Synchronize to vuex store in real time
                    this.setEditUserInfo(values)
                },
            });
        },
        computed: {
            ...mapState({
                openEdit: state => state.userEdit.openEdit,
                editUserInfo: state => state.userEdit.editUserInfo,
                uploadedFile: state => state.file.uploadedFile
            }),
            ...mapGetters({
                avatarPath: userEdit.getters.AVATAR_PATH
            })
        },
        methods: {
            ...mapMutations({
                closeEdit: userEdit.mutations.CLOSE_EDIT,
                setEditUserInfo: userEdit.mutations.SET_EDIT_USER_INFO
            }),
            ...mapActions({
                updateUser: userEdit.actions.MODIFY_USER,
                upload: file.actions.UPLOAD,
            }),
            handleSubmit () {
                util.getValuesWithValid(this.form).then(values => {
                    this.updateUser().then(() => {
                        console.log('完成更新')
                        this.$emit('reloadTable');
                    });
                })
            },
            confirmPassword(rule, value, callback){
                const password = this.form.getFieldValue('password');

                console.log(password)
                if(password && password !== value){
                    callback('两次输入不一致！');
                }else {
                    callback();
                }
            }
        }
    }
</script>

<style scoped>

</style>