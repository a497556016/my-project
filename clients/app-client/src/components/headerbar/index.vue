<template>
    <div class="my-head-bar">
        <div class="left">
            <div class="icon">
                <van-icon name="scan"></van-icon>
            </div>
        </div>
        <div class="center">
            <van-search v-model="headerBar.searchWords" :background="backgroundColor" shape="round" :placeholder="headerBar.placeholder" @search="onSearch"></van-search>
        </div>
        <div class="right">

            <div class="icon">
                <van-icon name="ellipsis"></van-icon>
            </div>
        </div>


    </div>
</template>

<script>
    import {Icon, Search} from 'vant'
    import {mapState} from 'vuex'
    export default {
        name: "MyHeaderBar",
        components: {
            [Icon.name]: Icon,
            [Search.name]: Search
        },
        props: {
            searchWords: {
                type: String,
                default: '请输入关键字搜索'
            }
        },
        data(){
           return {
               backgroundColor: ''
           }
        },
        computed: {
            ...mapState({
                headerBar: state => state.home.headerBar
            })
        },
        mounted(){
            const style = document.defaultView.getComputedStyle(this.$el);
            this.backgroundColor = style.backgroundColor;
        },
        methods: {
            onSearch(){
                this.$emit('search', this.headerBar.searchWords||this.headerBar.placeholder);
            }
        }
    }
</script>

<style scoped lang="less">
    @import "../../assets/style/index";
    .my-head-bar {
        background: @primary-color;
        height: 50px;
        line-height: 58px;
        position: fixed;
        z-index: 10;
        width: 100%;
        display: flex;



        .left {
            width: 30px;
            padding-left: 15px;

            .icon{
                color: @primary-text-color;
                font-size: 23px;
            }
        }
        .center{
            width: 180px;
            height: 50px;

            .van-search{
                padding: 8px 10px;
            }
        }
        .right {
            flex: 1;
            text-align: right;
            padding-right: 15px;

            .icon{
                color: @primary-text-color;
                font-size: 23px;
            }
        }
    }
</style>