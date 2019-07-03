<template>
    <div class="relation-page">
        <my-header-bar></my-header-bar>

        <van-pull-refresh v-model="friendsShare.refreshing" @refresh="refreshFriendsShare">
            <van-loading color="#fafafa" style="z-index: 2;text-align: center" v-if="friendsShare.refreshing"></van-loading>
            <personal-show-panel :sign-words="signWords" :back-img="backImg" :avatar="avatar"></personal-show-panel>

            <div class="list">
                <friends-share-list :friends-share-data="friendsShare" :on-load="getFriendsShareList"></friends-share-list>
            </div>
        </van-pull-refresh>
    </div>
</template>

<script>
    import {PullRefresh, Loading} from 'vant'
    import MyHeaderBar from "../../../components/headerbar/index";
    import PersonalShowPanel from "../../../components/panel/personal-show-panel/index";
    import FriendsShareList from "../../../components/list/friends-share-list";
    import {mapActions, mapState} from 'vuex'
    import {mapTypes} from '../../../store/types'
    export default {
        name: "index",
        components: {
            FriendsShareList, PersonalShowPanel, MyHeaderBar,
            [PullRefresh.name]: PullRefresh,
            [Loading.name]: Loading
        },
        data(){
            return {
                signWords: '明天的美好',
                backImg: this.$mock.Random.dataImage('500x100', '遇见自己'),
                avatar: this.$mock.Random.dataImage('60x60', '狗'),
            }
        },
        computed: {
            ...mapState({
                friendsShare: state => state[mapTypes.relation.value].friendsShare
            })
        },
        mounted() {
            // console.log(this.friendsShare);
            // this.getFriendsShareList();
        },
        methods: {
            ...mapActions({
                getFriendsShareList: mapTypes.relation.GET_FRIENDS_SHARE_LIST,
                refreshFriendsShare: mapTypes.relation.REFRESH_FRIENDS_SHARE
            })
        }
    }
</script>

<style scoped lang="less">
    .relation-page {
        .personal-show-panel {
            top: -150px;
        }
        .list {
            margin-top: -80px;
            margin-bottom: 50px;
        }
    }
</style>