<template>
    <div class="user-page">
        <div class="user-info">
            <div class="nav-bar">
                <van-icon class="setting" name="setting-o" @click="onEdit"></van-icon>
            </div>
            <div class="avatar">
                <img :src="loginUser.avatar"/>
                <div @click="showAccountActions = true">{{loginUser.username}}</div>
                <div style="font-size: 13px;color: #cecece">{{loginUser.phone}}</div>
            </div>

        </div>

        <van-action-sheet
                v-model="showAccountActions"
                :actions="accountActions"
                cancel-text="取消"
                @select="onAccountActionSelect"
                @cancel="showAccountActions = false"
        />
    </div>
</template>

<script>
    import {ActionSheet, Icon} from 'vant'
    import {mapGetters, mapState, mapMutations} from 'vuex'

    const accountActions = [
        {name: '退出登录', className: 'warn-action'},
        {name: '切换账号'}
    ];

    export default {
        name: "User",
        components: {
            [ActionSheet.name]: ActionSheet,
            [Icon.name]: Icon
        },
        data(){
            return {
                showAccountActions: false,
                accountActions
            }
        },
        computed: {
            ...mapGetters({
                loginUser: 'account/GET_LOGIN_USER',
            })
        },
        methods: {
            ...mapMutations({
                logoutCurrentUser: 'account/LOGOUT_CURRENT_USER'
            }),
            onAccountActionSelect(item, index){
                if(index == 0) {
                    this.logoutCurrentUser();
                    this.$router.push({path: '/login', replace: true});
                }
            },
            onEdit(){
                this.$router.push({path: '/main/user/edit', replace: true})
            }
        }
    }
</script>

<style scoped lang="less">
    @import "../../../assets/style/index";

    .warn-action {
        color: @warn-color;
    }
    .user-page{
        .user-info{
            height: 160px;
            background: @primary-color;
            color: @primary-text-color;

            .nav-bar {
                height: 20px;

                .setting{
                    position: absolute;
                    top: 10px;
                    right: 20px;
                    font-size: 1.4em;
                }
            }
            .avatar {
                width: 100%;
                text-align: center;
                img{
                    width: 80px;
                    height: 80px;
                    border-radius: 40px;
                    background: white;
                }
            }
        }
    }
</style>