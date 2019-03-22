<template>
    <div>
        <a-layout-header style="background: #fff; padding: 0">
            <a-icon
                    style="float: left;line-height: 64px;margin: 0 24px;cursor: pointer;"
                    :type="iconCollapsed ? 'menu-unfold' : 'menu-fold'"
                    @click="()=> iconCollapsed = !iconCollapsed"
            />
            <a-menu
                    mode="horizontal"
                    :defaultSelectedKeys="defaultSelectedMenus"
                    :style="{ lineHeight: '64px', float: 'left' }"
            >
                <a-menu-item v-for="menu in menus" :key="menu.id" @click="menuHandler(menu)">{{menu.name}}</a-menu-item>
            </a-menu>

            <div style="float: right; display: flex; flex-flow: row;">
                <a @click="showNoticeBox">
                    <a-badge  :count="1" :style="{margin: '10px'}">
                        <a-avatar :style="{ background: '#eee' }" :size="40" :src="avatar" />
                    </a-badge>
                </a>
                <!--<img :style="{borderRadius: '25px', width: '40px', height: '40px', background: '#eee', margin: '10px'}" src="../../assets/logo.png">-->
                <a-dropdown :trigger="['click']" :style="{margin: '0 10px'}">
                    <a class="ant-dropdown-link" href="#">
                        {{userInfo?userInfo.username:''}} &nbsp;&nbsp;<a-icon type="down" />
                    </a>
                    <a-menu slot="overlay">
                        <a-menu-item>
                            <a href="javascript:;" @click="logout()">退出登录</a>
                        </a-menu-item>
                    </a-menu>
                </a-dropdown>
            </div>
        </a-layout-header>

        <notice-box></notice-box>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import {account, layout} from '@/store/types'

    import NoticeBox from './NoticeBox'

    export default {
        name: "Header",
        components: {NoticeBox},
        props: {
            menus: {
                type: Array,
                default(){
                    return []
                }
            },
            defaultSelectedMenus: {
                type: Array
            },
            collapsed: {
                type: Boolean,
                default: false
            }
        },
        data(){
            return {
                iconCollapsed: this.collapsed,
                // avatar: require('../../assets/logo.png')
            }
        },
        computed: {
            ...mapGetters({
                userInfo: account.getters.GET_USER_INFO,
                avatar: account.getters.GET_AVATAR
            })
        },
        watch: {
            iconCollapsed(val){
                this.$emit('collapseChange', val);
            }
        },
        methods: {
            ...mapMutations({
                logout: account.mutations.LOGOUT,
                showNoticeBox: layout.SHOW_NOTICE_BOX
            }),
            ...mapActions({
                changeSubMenus: layout.CHANGE_SUB_MENUS
            }),
            menuHandler(menu) {
                this.changeSubMenus(menu.route);
            }
        }
    }
</script>

<style scoped>

</style>