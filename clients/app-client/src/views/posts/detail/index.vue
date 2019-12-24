<template>
    <div>
        <my-nav-bar :title="viewPost.title" left-arrow :fixed="true" :border="false" @click-left="onBackHistory"></my-nav-bar>

        <div class="content-body">
            <div class="info">
                <div class="title">{{viewPost.title}}</div>
                <div class="author">
                    <img class="avatar" :src="viewPost.author.avatar">
                    <div class="name">{{viewPost.author.name}}</div>
                    <div class="date">{{viewPost.desc}}</div>
                </div>
            </div>
            <div class="article">
                <div class="image">
                    <img v-if="viewPost.image" :src="viewPost.image"/>
                </div>
                <div>
                    <p>{{viewPost.content}}</p>
                </div>
            </div>

        </div>

        <div class="footer">
            <div class="likes">
                <span><i class="fa fa-thumbs-up"></i>赞同 153</span>
                <span><i class="fa fa-thumbs-down"></i>反对</span>
            </div>
            <div class="actions">
                <div class="action">
                    <div class="icon">
                        <i class="fa fa-star-o"></i>
                    </div>
                    <div class="text">收藏 100</div>
                </div>
                <div class="action">
                    <div class="icon">
                        <i class="fa fa-heart-o"></i>
                    </div>
                    <div class="text">评论 15</div>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
    import MyNavBar from "../../../components/navbar/index";
    import {mapGetters} from "vuex"
    import mapTypes from "../../../store/types"
    export default {
        name: "PostDetail",
        components: {MyNavBar},
        props: {
            id: String
        },
        computed: {
            ...mapGetters({
                viewPost: mapTypes.home.GET_VIEW_POST
            })
        },
        mounted() {

        },
        activated() {
            console.log(this.$route);
        },
        methods: {
            onBackHistory(){
                this.$router.back();
            }
        }
    }
</script>

<style scoped lang="less">
    .content-body {
        position: relative;
        top: 46px;
        bottom: 45px;


        background: #d7d7d7;

        .info {
            background: white;
            margin-bottom: 20px;
            .title {
                border-bottom: 1px solid #eeeeee;
                height: 30px;
                line-height: 30px;
                padding: 10px 10px;
                font-weight: bold;
                font-size: 18px;
            }
            .author {
                padding: 5px 10px;
                height: 30px;
                color: #808080;
                font-size: 12px;
                .avatar {
                    width: 30px;

                    float: left;
                }
                .name {
                    line-height: 40px;
                    float: left;
                    margin-left: 15px;
                }
                .date{
                    line-height: 40px;
                    float: right;
                }
            }
        }

        .article {
            background: white;
            padding: 10px 10px;
            .image {
                width: 100%;
                img{
                    width: 100%;
                }
            }
        }


    }

    .footer {
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 45px;
        line-height: 45px;
        border-top: 1px solid #eeeeee;
        display: flex;
        justify-content: space-between;
        padding: 10px 0px;
        font-size: 14px;
        font-weight: 600;
        .likes{
            color: #2a2ac9;
            padding: 0 10px;
            border-radius: 25px;
            background: #e4dff9;
            margin-left: 15px;
            /*height: 30px;
            line-height: 30px;
            margin-top: 10px;*/
            span:not(:first-child){
                border-left: 1px solid #b0b1c2;
            }
            span{
                padding: 0 10px;
            }
        }
        .actions {
            padding: 0 10px;
            display: flex;
            .action {
                margin: 0 10px;
                text-align: center;
                display: flex;
                flex-direction: column;
                .icon {
                    display: inline-block;
                    height: 25px;
                    line-height: 25px;
                    font-size: 20px;
                }
                .text {
                    height: 30px;
                    line-height: 30px;
                    font-size: 10px;
                }
            }
        }
    }
</style>