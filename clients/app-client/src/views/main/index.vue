<template>
    <div>
        <keep-alive>
            <router-view />
        </keep-alive>
        <van-tabbar v-model="active" route @change="tabChange">
            <van-tabbar-item name="home" to="/main/home" icon="home-o">推荐</van-tabbar-item>
            <van-tabbar-item name="relation" to="/main/relation" icon="friends">圈子</van-tabbar-item>
            <van-tabbar-item name="user" to="/main/user" icon="user-o">个人中心</van-tabbar-item>
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
                active: 'home',
                savedPosition: {}
            }
        },
        mounted() {
            //初始化系统
            this.loadTopImages();
            window.onscroll = (e) => {
                // console.log(e.target.scrollingElement.scrollTop)
                this.savedPosition[this.active] = e.target.scrollingElement.scrollTop;
            }
        },
        activated(){

        },
        methods: {
            ...mapActions({
                loadTopImages: 'home/LOAD_TOP_IMAGES'
            }),
            tabChange(){
                console.log(this.savedPosition, this.active)
                setTimeout(() => {
                    window.scrollTo({top: this.savedPosition[this.active] || 0})
                }, 500)

            }
        }
    }
</script>

<style scoped>

</style>