<template>
    <div class="friend-share-panel">
        <div class="avatar">
            <van-image v-if="data.avatar" :src="data.avatar"></van-image>
        </div>
        <div class="content">
            <div class="name">{{data.name}}</div>
            <div class="words">{{data.content}}</div>
            <div class="videos" v-if="data.videos.length">
                <video-player @click="$toast('???')" ref="videoPlayer" :options="videoOptions" style="margin-bottom: 15px"></video-player>
            </div>
            <div class="images" v-if="data.images">
                <van-image v-for="(img, index) in data.images" width="85" height="85" :src="img.src" @click="imagePreview(index)"></van-image>
            </div>
            <div class="desc">
                {{data.date}}
            </div>
            <div class="likes" v-if="data.likes.length">
                <van-icon name="like-o"></van-icon>
                <span>{{data.likes.map(like => like.name).join(', ')}}</span>
            </div>
            <div class="comments" v-if="data.comments.length">
                <div class="comment" v-for="comment in data.comments">
                    <div><span class="name">{{comment.from}}</span></div>
                    <div v-if="comment.to">回复<span class="name">{{comment.to}}</span>: </div>
                    <div>{{comment.comment}}</div>
                </div>
            </div>
        </div>

        <van-image-preview v-model="showImagePreview" :images="images" :start-position="startPosition"></van-image-preview>
    </div>
</template>

<script>
    import {Icon, Image} from 'vant'

    import VideoPlayer from '../../media/video'

    const videoOptions = {
        muted: true,
        autoplay: true,
        height: 150,
        sources: [],
    }

    export default {
        name: "FriendSharePanel",
        components: {
            VideoPlayer,
            [Icon.name]: Icon,
            [Image.name]: Image
        },
        props: {
            data: Object
        },
        data(){
            videoOptions.sources.push(...this.data.videos);
            return {
                showImagePreview: false,
                images: [],
                startPosition: 0,
                videoOptions
            }
        },
        mounted(){
            const videoPlayer = this.$refs.videoPlayer;
            if(videoPlayer) {
                videoPlayer.player.options_.width = videoPlayer.$el.clientWidth;
                console.log(videoPlayer.player);
            }
        },
        computed: {

        },
        methods: {
            imagePreview(index){
                this.images = this.data.images.map(img => img.src);
                this.startPosition = index;
                this.showImagePreview = true;
            }
        }
    }
</script>

<style scoped lang="less">
    @import "../../../assets/style/index";
    @import "~vant/lib/style/var";
    .friend-share-panel {
        display: flex;
        /*justify-content: flex-start;*/
        .avatar {
            width: 60px;
            padding-left: 15px;
            .van-image{
                width: 50px;
                height: 50px;
                border-radius: 5px;
                background: #d2dde6;
            }
        }
        .content {
            flex: 1;
            padding: 8px;
            .name {
                font-weight: bold;
                color: @blue;
            }
            .words {
                font-size: 15px;
                padding: 8px 8px 8px 0;
                color: @text-color;
            }
            .videos {
                padding: 8px 8px 8px 0;
            }
            .images {
                display: flex;
                flex-wrap: wrap;
                justify-content: left;
                margin-bottom: 15px;
            }
            .desc {
                font-size: 11px;
                color: @light-text-color;
            }
            .likes {
                font-size: 13px;
                font-weight: bold;
                background: #efefef;
                margin-top: 10px;
                padding: 4px 5px;
                color: @blue;
                display: flex;
                line-height: 14px;

                .van-icon {
                    /*margin-top: 5px;*/
                    margin-right: 5px;
                }
            }
            .comments {
                border-top: 0.01em solid #d2dde6;
                font-size: 13px;
                background: #efefef;
                padding: 5px;
                .comment {
                    display: flex;
                    flex-wrap: wrap;
                    margin: 5px 0;
                    .name {
                        color: @blue;
                    }
                }
            }
        }
    }
</style>