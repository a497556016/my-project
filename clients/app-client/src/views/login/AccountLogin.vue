<template>
    <div class="login-page">
        <van-cell-group>
            <van-field left-icon="user-o" placeholder="请输入账号" v-model="loginUser.account"></van-field>
            <van-field left-icon="lock" placeholder="请输入密码" v-model="loginUser.password" type="password"></van-field>
            <div class="submit-bar">
                <div>
                    <van-button class="submit-btn" type="primary" @click="login">登录</van-button>
                    <van-button class="verify-code-login" size="small" type="danger" plain @click="phoneLogin">手机验证码登录</van-button>
                </div>
            </div>
        </van-cell-group>
    </div>
</template>

<script>
    import {Button, CellGroup, Cell, Field} from 'vant'
    import {mapState, mapActions} from 'vuex'
    export default {
        name: "Login",
        components: {
            [Button.name]: Button,
            [CellGroup.name]: CellGroup,
            [Cell.name]: Cell,
            [Field.name]: Field
        },
        mounted(){
            console.log(this.$store)
        },
        computed: {
            ...mapState({
                loginUser: state => state.account.loginUser
            })
        },
        methods: {
            ...mapActions({
                loginByAccount: 'account/LOGIN_BY_ACCOUNT'
            }),
            login(){
                console.log(this.loginUser);
                this.loginByAccount().then((res) => {
                    if(res.code == 1) {
                        this.$toast.success('登录成功！');
                        this.$router.push({path: '/main/home', replace: true})
                    }else {
                        this.$toast.fail(res.msg);
                    }
                });
            },
            phoneLogin(){
                this.$router.push({path: '/login/phone'})
            }
        }
    }
</script>

<style scoped lang="less">
    .login-page {
        padding-top: 35%;
        .submit-bar {
            text-align: center;

            .submit-btn{
                margin: 5px 0;
                font-size: 15px;
            }

            .verify-code-login {
                position: absolute;
                right: 20px;
                bottom: 5px;
                padding: 0 2px;
            }
        }
    }
</style>