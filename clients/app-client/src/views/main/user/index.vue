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

        <van-grid :column-num="menuLayout.cols" :gutter="menuLayout.space" :border="false">
            <van-grid-item v-for="menu in menuItems" :icon="menu.icon" :text="menu.title" :style="{color: menu.color}"></van-grid-item>
        </van-grid>
        <!--<div class="grid">
            <van-row class="grid-row" type="flex" justify="space-between" v-for="row in menuRowCols">
                <van-col class="grid-cell" :span="col.span" v-for="col in row">
                    <van-icon :name="col.icon" size="36" :color="col.color"></van-icon>
                    <div class="text">{{col.title}}</div>
                </van-col>
            </van-row>
        </div>-->

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
    import {ActionSheet, Icon, Grid, GridItem} from 'vant'
    import {mapGetters, mapState, mapMutations} from 'vuex'
    import {mapTypes} from '../../../store/types'

    const accountActions = [
        {name: '退出登录', className: 'warn-action'},
        {name: '切换账号'}
    ];

    export default {
        name: "User",
        components: {
            [ActionSheet.name]: ActionSheet,
            [Icon.name]: Icon,
            [Grid.name]: Grid,
            [GridItem.name]: GridItem,
        },
        data(){
            return {
                showAccountActions: false,
                accountActions
            }
        },
        computed: {
            ...mapState({
                menuItems: state => state[mapTypes.menus.value].menuItems,
                menuLayout: state => state[mapTypes.menus.value].menuLayout
            }),
            ...mapGetters({
                loginUser: mapTypes.account.GET_LOGIN_USER,
                menuRowCols: mapTypes.menus.GET_MENU_ROW_COLS,
            })
        },
        methods: {
            ...mapMutations({
                logoutCurrentUser: mapTypes.account.LOGOUT_CURRENT_USER
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

        /*.grid {
            .grid-row {
                margin: 15px 15px 0 15px;
                .grid-cell {
                    position: relative;
                    text-align: center;
                    border: 0.02em solid #f7f3ed;
                    background: #f1f1f1;
                    height: 80px;

                    .van-icon {
                        position: relative;
                        top: 10px;
                    }
                    .text {
                        position: absolute;
                        bottom: 5px;
                        width: 100%;
                        text-align: center;
                        font-size: 13px;
                    }
                }
            }
        }*/
    }
</style>