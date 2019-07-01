<template>
    <div class="phone-login">
        <van-field left-icon="phone" type="number" placeholder="请输入手机号码" v-model="loginUser.phone"></van-field>
        <van-cell>
            <van-button size="large" type="primary" @click="nextStep">下一步</van-button>
        </van-cell>
    </div>
</template>

<script>
    import {Button, Field, CellGroup, Cell} from 'vant'
    import {mapState, mapGetters} from 'vuex'
    export default {
        name: "LoginPhoneCode",
        components: {
            [Button.name]: Button,
            [Field.name]: Field,
            [CellGroup.name]: CellGroup,
            [Cell.name]: Cell
        },
        computed: {
            ...mapState({
                loginUser: state => state.account.loginUser
            })
        },
        methods: {

            nextStep(){
                if(!this.loginUser.phone){
                    this.$toast('请输入手机号码！')
                }else {
                    this.$router.push({path: '/login/verifyCode'})
                }
            }
        }
    }
</script>

<style scoped lang="less">
    .phone-login {
        margin-top: 30%;
    }
</style>