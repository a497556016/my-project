<template>
    <div class="edit-user-page">
        <my-nav-bar title="编辑个人信息" left-arrow :fixed="true" :border="false" @click-left="onBackHistory"></my-nav-bar>

        <div class="content">
            <cell-group>
                <field label="账号" v-model="loginUser.account" required clearable></field>
                <field label="用户名" v-model="loginUser.username" required clearable></field>
                <field label="手机" v-model="loginUser.phone" required clearable></field>
                <field label="性别" v-model="gender" required readonly clickable @click="showGenderPicker = true"></field>
                <van-button size="large" type="primary">保存</van-button>
            </cell-group>
        </div>

        <popup v-model="showGenderPicker" position="bottom">
            <picker
                    show-toolbar
                    :columns="genderData"
                    @cancel="showGenderPicker = false"
                    @confirm="onGenderConfirm"
            />
        </popup>
    </div>
</template>

<script>
    import MyNavBar from "../../../../components/navbar/index";
    import {CellGroup, SwitchCell, Field, Popup, Picker, Button} from 'vant'
    import {mapState} from 'vuex'
    export default {
        name: "EditUserPage",
        components: {
            MyNavBar,
            CellGroup,
            SwitchCell,
            Field,
            Popup,
            Picker,
            [Button.name]: Button
        },
        data(){
            return {
                showGenderPicker: false,
                genderData: [{text: '男', value: 1},{text: '女', value: 0}]
            }
        },
        computed: {
            ...mapState({
                loginUser: state => state.account.loginUser
            }),
            gender(){
                const gender = this.genderData.find(item => item.value == this.loginUser.gender);
                console.log(this.loginUser, gender)
                return gender?gender.text:null;
            }
        },
        methods: {
            onBackHistory(){
                this.$router.back();
            },
            onGenderConfirm(value){
                console.log(value)
                this.loginUser.gender = value.value
                this.showGenderPicker = false;
            }
        }
    }
</script>

<style scoped lang="less">
    @import "../../../../assets/style/index";
    .edit-user-page {
        .content{
            margin-top: 46px;
        }
    }
</style>