<template>
    <div class="posts-panel">
        <my-card>
            <div v-if="data.title" slot="title">
                <div class="van-ellipsis">{{data.title}}</div>
                <div v-if="data.author" class="author">
                    <img :src="data.author.avatar"/>
                    <div>{{data.author.name}}</div>
                </div>
            </div>
            <div class="content van-hairline--top-bottom">
                <div class="img">
                    <img v-if="data.image" :src="data.image"/>
                </div>
                <div>{{data.content}}</div>
            </div>
            <div class="footer" slot="footer">
                <div class="left">
                    <van-icon name="like-o"></van-icon>
                    <span>{{data.likes}}</span>
                    <van-icon name="star-o"></van-icon>
                    <span>{{data.stars}}</span>
                    <van-icon name="eye-o"></van-icon>
                    <span>{{reads}}</span>
                </div>
                <div class="right">{{data.desc}}</div>
            </div>
        </my-card>
    </div>
</template>

<script>
    import {Icon} from 'vant'
    import MyCard from "../../card/index";
    export default {
        name: "PostsPanel",
        components: {
            MyCard,
            [Icon.name]: Icon
        },
        props: {
            data: Object
        },
        computed: {
            reads(){
                const reads = this.data.reads || 0;
                if(reads > 1000) {
                    return (reads/1000).toFixed(2) + 'k';
                }else {
                    return reads;
                }
            }
        }
    }
</script>

<style scoped lang="less">
    @import "../../../assets/style/index";
    .posts-panel {
        .author {
            margin-top: 5px;
            height: 28px;
            line-height: 28px;
            display: flex;
            color: @thin-text-color;
            font-size: 12px;
            font-weight: normal;
            img{
                width: 23px;
                height: 23px;
                border-radius: 15px;
                margin-right: 10px;
                margin-top: 2px;
            }
        }
        .content {
            .img {
                width: 100%;
                max-height: 80px;
                overflow: hidden;
                img{
                    width: 100%;

                }
            }
            color: @light-text-color;
            padding: 5px 0;
            font-size: 14px;
        }
        .footer {
            color: @thin-text-color;
            height: 16px;
            line-height: 15px;
            .left {
                float: left;
                display: flex;
                .van-icon {
                    font-size: 14px;
                }
                span{
                    margin-left: 3px;
                    margin-right: 8px;
                }
            }
            .right {
                float: right;
            }
        }
    }
</style>