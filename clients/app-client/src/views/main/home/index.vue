<template>
    <div class="home-page">
        <my-header-bar @search="(value) => $toast(value)"></my-header-bar>
        <div class="content-box">
            <van-swipe :autoplay="3000" indicator-color="white">
                <van-swipe-item v-for="img in topImages">
                    <img class="swipe-img" :src="img.path"/>
                </van-swipe-item>
            </van-swipe>

            <van-list
                v-model="commendPosts.loading"
                :finished="commendPosts.finished"
                finished-text="没有更多了"
                @load="getCommendPosts"
            >
                <posts-panel v-for="item in commendPosts.data" :title="item.title" :content="item.content" :desc="item.desc" :image="item.image" :author="item.author"></posts-panel>
            </van-list>
        </div>
    </div>
</template>

<script>
    import {Swipe, SwipeCell, SwipeItem, Row, Col, Icon, Field, List} from 'vant'
    import {mapGetters, mapState, mapActions} from 'vuex'
    import {mapTypes} from '../../../store/types'
    import MyHeaderBar from "../../../components/headerbar";
    import PostsPanel from "../../../components/panel/posts-panel/index";
    export default {
        name: "Home",
        components: {
            PostsPanel,
            MyHeaderBar,
            [Swipe.name]: Swipe,
            [SwipeCell.name]: SwipeCell,
            [SwipeItem.name]: SwipeItem,
            [Row.name]: Row,
            [Col.name]: Col,
            [Icon.name]: Icon,
            [Field.name]: Field,
            [List.name]: List
        },
        data(){
            return {

            }
        },
        mounted(){
            // this.getCommendPosts();
        },
        computed: {
            ...mapState({
                topImages: state => state.home.topImages,
                commendPosts: state => state.home.commendPosts
            })
        },
        methods: {
            ...mapActions({
                getCommendPosts: mapTypes.home.GET_COMMEND_POSTS
            })
        }
    }
</script>

<style scoped lang="less">
    @import "../../../assets/style/index";
    .home-page {
        .content-box {
            position: relative;
            top: 50px;
            margin-bottom: 50px;
            .swipe-img {
                height: 150px;
                width: 100%;
            }

            /*.panel-content {
                padding: 5px 15px;
                font-size: 13px;
                color: @light-text-color
            }*/

            .van-list {
                background: #e7e7e7;
            }
        }
    }
</style>