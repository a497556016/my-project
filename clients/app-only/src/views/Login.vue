<template>
    <div class="login-page">
        <div class="form">
            <div class="im-form-item">
                <label class="im-label">用户名</label>
                <input class="im-input" v-model="user.username"/>
            </div>
            <div class="im-form-item">
                <label class="im-label">密码</label>
                <input class="im-input" type="password" v-model="user.password"/>
            </div>
            <div class="im-form-item" style="justify-content: center">
                <button class="im-button im-button-primary" @click="login(user)">登录</button>
                <button class="im-button" @click="register">注册</button>
            </div>
        </div>
    </div>
</template>

<script>
    import {createNamespacedHelpers} from 'vuex'
    const userStore = createNamespacedHelpers('user');
    import {user as userTypes} from '../store/types'
    export default {
        name: "Login",
        data(){
            return {
                user: {
                    username: '',
                    password: ''
                }
            }
        },
        methods: {
            ...userStore.mapActions({
                login: userTypes.LOGIN
            }),
            register(){
                this.$router.push({path: '/register'})
            }
        }
    }
</script>

<style scoped lang="less">
    .login-page {
        height: 100%;
        /*background: #d2dde6;*/

        .form {
            text-align: center;
            padding: 100px 25px 0 25px;

        }
    }
</style>