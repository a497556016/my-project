<template>
    <div class="posts-list">
        <van-pull-refresh ref="pullRefresh" v-model="postsData.refreshing" @refresh="onRefresh">
            <van-list
                    v-model="postsData.loading"
                    :finished="postsData.finished"
                    finished-text="没有更多了"
                    @load="onLoad"
            >
                <template v-for="(item, index) in postsData.data">
                    <div class="refresh-index" v-if="postsData.refreshIndex != 0 && postsData.refreshIndex == index" @click="doRefresh">
                        上次看到这儿 点击刷新
                    </div>
                    <a @click="viewTail(item)">
                        <posts-panel :data="item"></posts-panel>
                    </a>
                </template>
                <van-skeleton title avatar :row="3" :loading="postsData.loading"></van-skeleton>
            </van-list>
        </van-pull-refresh>
    </div>
</template>

<script>
    import {List, Skeleton, PullRefresh, Icon} from 'vant'
    import PostsPanel from "../../../components/panel/posts-panel/index";
    export default {
        name: "PostsList",
        components: {
            PostsPanel,
            [List.name]: List,
            [Skeleton.name]: Skeleton,
            [PullRefresh.name]: PullRefresh,
            [Icon.name]: Icon
        },
        props: {
            postsData: Object,
            onLoad: Function,
            onRefresh: Function,
            viewTail: Function
        },
        data(){
            return {

            }
        },
        methods: {
            doRefresh(){
                //滚动到顶点
                console.log(this.$refs.pullRefresh)
                window.scrollTo({
                    top: 0
                })
                this.onRefresh();
            }
        }
    }
</script>

<style scoped lang="less">
    .posts-list {
        background: #e7e7e7;

        .refresh-index {
            text-align: center;
            color: #2f54eb;
            margin-bottom: 15px;
            font-size: 13px;
        }
    }
</style>