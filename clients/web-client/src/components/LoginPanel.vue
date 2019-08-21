<template>
    <div style="width: 100%; height: 100%;display: flex; flex-flow: column; justify-content: center;">
        <div style="display: flex; flex-flow: row; justify-content: center">
            <div class="outer-panel">
                <a-card title="登录" class="inner-panel">
                    <a-form :form="form" @submit="handleSubmit">
                        <a-form-item label="用户名" :label-col="labelCol" :wrapper-col="wrapperCol">
                            <a-input v-decorator="['username', {rules: [{required: true, message: '请输入用户名！'}]}]" placeholder="888888" />
                        </a-form-item>
                        <a-form-item label="密码" :label-col="labelCol" :wrapper-col="wrapperCol">
                            <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码！'}]}]" type="password" placeholder="888888" />
                        </a-form-item>
                        <div style="text-align: center;" class="space-between-18">
                            <a-button type="primary" html-type="submit">登录</a-button>
                            <a-button html-type="reset">重置</a-button>
                        </div>
                    </a-form>
                </a-card>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "LoginPanel",
        props: [],
        data(){
            return {
                form: this.$form.createForm(this),
                labelCol: {
                    span: 5
                },
                wrapperCol: {
                    span: 19
                }
            }
        },
        methods: {
            handleSubmit(e){
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        this.$emit('login', values);
                        console.log('Received values of form: ', values);
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .outer-panel {
        padding: 28px 25px;
        border: 1px solid rgba(132, 220, 217, 0.49);
        /* opacity: 0.3; */
        z-index: 1;
        box-shadow: 0 0 10px rgba(233, 232, 208, 0.68);
        border-radius: 4px;
        background: rgba(80, 93, 255, 0.09);
    }
    .inner-panel {
        width: 400px;
        border: 1px solid rgba(124, 232, 234, 0.56);
        z-index: 9;
        /* box-shadow: rgb(61, 72, 140) 0px 0px 30px; */
        background: rgba(53, 73, 94, 0.22);
    }
</style>