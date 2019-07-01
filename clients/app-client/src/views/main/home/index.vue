<template>
    <div class="home-page">
        <my-header-bar @search="(value) => $toast(value)"></my-header-bar>
        <div class="content-box">
            <van-swipe :autoplay="3000" indicator-color="white">
                <van-swipe-item v-for="img in topImages">
                    <img class="swipe-img" :src="img.path"/>
                </van-swipe-item>
            </van-swipe>
            <div class="grid">
                <van-row class="grid-row" type="flex" justify="space-between" v-for="row in menuRowCols">
                    <van-col class="grid-cell" :span="col.span" v-for="col in row">
                        <van-icon :name="col.icon" size="36" :color="col.color"></van-icon>
                        <div class="text">{{col.title}}</div>
                    </van-col>
                </van-row>
            </div>
        </div>
    </div>
</template>

<script>
    import {Swipe, SwipeCell, SwipeItem, Row, Col, Icon, Field} from 'vant'
    import {mapGetters, mapState} from 'vuex'
    import MyHeaderBar from "../../../components/headerbar/index";
    export default {
        name: "Home",
        components: {
            MyHeaderBar,
            [Swipe.name]: Swipe,
            [SwipeCell.name]: SwipeCell,
            [SwipeItem.name]: SwipeItem,
            [Row.name]: Row,
            [Col.name]: Col,
            [Icon.name]: Icon,
            [Field.name]: Field
        },
        data(){
            return {

            }
        },
        computed: {
            ...mapState({
                topImages: state => state.home.topImages
            }),
            ...mapGetters({
                menuRowCols: 'home/GET_MENU_ROW_COLS'
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
            .swipe-img {
                height: 150px;
                width: 100%;
            }

            .grid {
                .grid-row {
                    margin: 15px 15px 0 15px;
                    .grid-cell {
                        position: relative;
                        text-align: center;
                        border: 0.02em solid #eee;
                        background: #efefef;
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
            }
        }
    }
</style>