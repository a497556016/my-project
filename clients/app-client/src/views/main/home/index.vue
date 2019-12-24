<template>
    <div class="home-page">
        <my-header-bar @search="(value) => $toast(value)"></my-header-bar>


        <div class="content-box">
            <van-swipe :autoplay="3000" indicator-color="white">
                <van-swipe-item v-for="img in topImages">
                    <img class="swipe-img" :src="img.path"/>
                </van-swipe-item>
            </van-swipe>

            <posts-list :posts-data="commendPosts" :on-load="getCommendPosts" :on-refresh="refreshRecommendPosts" :view-tail="viewTail"></posts-list>
        </div>

        <float-button @click="toAddPosts()"></float-button>
    </div>
</template>

<script>
    import {Swipe, SwipeCell, SwipeItem, Row, Col, Icon, Field} from 'vant'
    import {mapGetters, mapState, mapActions, mapMutations} from 'vuex'
    import mapTypes from '../../../store/types'
    import MyHeaderBar from "../../../components/headerbar";
    import FloatButton from "../../../components/button/float-button/index";
    import PostsList from "../../../components/list/posts-list/index";
    export default {
        name: "Home",
        components: {
            PostsList,
            FloatButton,
            MyHeaderBar,
            [Swipe.name]: Swipe,
            [SwipeCell.name]: SwipeCell,
            [SwipeItem.name]: SwipeItem,
            [Row.name]: Row,
            [Col.name]: Col,
            [Icon.name]: Icon,
            [Field.name]: Field,
        },
        data(){
            return {

            }
        },
        mounted(){

        },
        computed: {
            ...mapState({
                topImages: state => state.home.topImages,
                commendPosts: state => state.home.commendPosts
            })
        },
        methods: {
            ...mapActions({
                getCommendPosts: mapTypes.home.GET_COMMEND_POSTS,
                refreshRecommendPosts: mapTypes.home.REFRESH_COMMEND_POSTS
            }),
            ...mapMutations({
                setViewPost: mapTypes.home.SET_VIEW_POST
            }),
            toAddPosts(){
                this.$toast('去吧，皮卡丘！');
                this.$router.push({path: '/posts/create'})
            },
            viewTail(item) {
                console.log(item)
                this.setViewPost(item);
                this.$router.push({
                    path: "/posts/detail",
                    query: {
                        id: item.id
                    }
                })
            }
        }
    }
</script>

<style scoped lang="less">
    @import "../../../assets/style/index";
    .home-page {
        .content-box {
            position: relative;
            top: 46px;
            margin-bottom: 50px;
            .swipe-img {
                height: 150px;
                width: 100%;
            }

        }
    }
</style>