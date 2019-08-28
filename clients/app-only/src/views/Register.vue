<template>
    <div class="register-page">
        <div class="form">
            <div class="im-form-item">
                <label class="im-label">用户名</label>
                <input class="im-input" v-model="user.username"/>
            </div>
            <div class="im-form-item">
                <label class="im-label">密码</label>
                <input class="im-input" type="password" v-model="user.password"/>
            </div>
            <div class="im-form-item">
                <label class="im-label">昵称</label>
                <input class="im-input" v-model="user.nickname"/>
            </div>
            <div class="im-form-item">
                <label class="im-label">手机号码</label>
                <input class="im-input" type="number" v-model="user.phone"/>
            </div>
            <div class="im-form-item">
                <button class="im-button im-button-primary im-button-flat" @click="submit">提交</button>
            </div>
        </div>
    </div>
</template>

<script>
    import {createNamespacedHelpers} from 'vuex'
    const userStore = createNamespacedHelpers('user');
    import {user as userTypes} from '../store/types'
    export default {
        name: "Register",
        data(){
            return {
                user: {}
            }
        },
        methods: {
            ...userStore.mapActions({
                register: userTypes.REGISTER
            }),
            submit(){
                this.register(this.user);
            }
        }
    }
</script>

<style scoped lang="less">
    .register-page {
        height: 100%;

        .form{
            padding: 25px;
            text-align: center;
        }
    }
</style>