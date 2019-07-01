<template>
    <div class="verify-code">
        <van-cell>
            <van-password-input @click="showKeyboard = true" v-model="value" :length="4" info="请输入4位数验证码"></van-password-input>
        </van-cell>
        <van-cell>
            <van-button size="large" type="primary" @click="confirmVerifyCode">确认登录</van-button>
        </van-cell>

        <!-- 数字键盘 -->
        <van-number-keyboard
                :show="showKeyboard"
                @input="onInput"
                @delete="onDelete"
                @blur="showKeyboard = false"
        />
    </div>
</template>

<script>
    import {Button, PasswordInput, NumberKeyboard, CellGroup, Cell} from 'vant'
    import {mapActions} from 'vuex'
    export default {
        name: "PasswordInput",
        components: {
            [Button.name]: Button,
            [PasswordInput.name]: PasswordInput,
            [NumberKeyboard.name]: NumberKeyboard,
            [CellGroup.name]: CellGroup,
            [Cell.name]: Cell
        },
        data(){
            return {
                showKeyboard: false,
                value: ''
            }
        },
        methods: {
            ...mapActions({
                loginByPhoneCode: 'account/LOGIN_BY_PHONE_CODE'
            }),
            onInput(key){
                this.value = (this.value + key).slice(0, 6);
            },
            onDelete(){
                this.value = this.value.slice(0, this.value.length - 1);
            },
            confirmVerifyCode(){
                console.log(this.value);
                if(this.value == '1234') {
                    this.loginByPhoneCode(this.value).then((res) => {
                        if(res.code == 1) {
                            this.$toast('登录成功！');
                            this.$router.push({path: '/', replace: true})
                        }else {
                            this.$toast.fail(res.msg);
                        }
                    });
                }else {
                    this.$toast('验证码错误！');
                }
            }
        }
    }
</script>

<style scoped lang="less">
    .verify-code {
        margin-top: 30%;
    }
</style>