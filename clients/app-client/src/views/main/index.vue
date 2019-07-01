<template>
    <div>
        <router-view />
        <van-tabbar v-model="active" route>
            <van-tabbar-item name="home" replace to="/main/home" icon="home-o">主页</van-tabbar-item>
            <van-tabbar-item name="user" replace to="/main/user" icon="user-o">个人中心</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    import {Button, Tabbar, TabbarItem} from "vant";
    export default {
        name: "Main",
        components: {
            [Button.name]: Button,
            [Tabbar.name]: Tabbar,
            [TabbarItem.name]: TabbarItem
        },
        data(){
            return {
                active: 'home'
            }
        },
        mounted() {
            //初始化系统
            this.loadTopImages();
        },
        methods: {
            ...mapActions({
                loadTopImages: 'home/LOAD_TOP_IMAGES'
            })
        }
    }
</script>

<style scoped>

</style>