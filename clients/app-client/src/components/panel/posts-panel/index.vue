<template>
    <div class="posts-panel">
        <my-card>
            <div v-if="title" slot="title">
                <div>{{title}}</div>
                <div v-if="author" class="author">
                    <img :src="author.avatar"/>
                    <div>{{author.name}}</div>
                </div>
            </div>
            <div class="content van-hairline--top-bottom">
                <div class="img">
                    <img v-if="image" :src="image"/>
                </div>
                <div>{{content}}</div>
            </div>
            <div class="footer" slot="footer">{{desc}}</div>
        </my-card>
    </div>
</template>

<script>
    import MyCard from "../../card/index";
    export default {
        name: "PostsPanel",
        components: {MyCard},
        props: {
            title: String,
            content: String,
            desc: String,
            image: String,
            author: Object
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
        }
        .footer {
            text-align: right;
            color: @thin-text-color;
        }
    }
</style>