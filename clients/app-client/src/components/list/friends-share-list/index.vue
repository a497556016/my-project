<template>
    <div class="friends-share-list">
        <van-list
            v-model="friendsShareData.loading"
            :finished="friendsShareData.finished"
            finished-text="没有更多了"
            @load="onLoad"
        >
            <template v-for="item in friendsShareData.data">
                <friend-share-panel :data="item"></friend-share-panel>
            </template>
        </van-list>
    </div>
</template>

<script>
    import { List} from 'vant'
    import FriendSharePanel from "../../panel/friend-share-panel/index";
    export default {
        name: "FriendsShareList",
        components: {
            FriendSharePanel,
            [List.name]: List
        },
        props: {
            friendsShareData: Object,
            onLoad: Function
        }
    }
</script>

<style scoped lang="less">
    .friends-share-list {
        /*width: 100%;*/

        .friend-share-panel:not(:last-child) {
            margin-bottom: 25px;
        }
    }
</style>