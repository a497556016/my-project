<template>
    <div :style="{display: noticeBoxVisible?'block':'none'}">
        <div :style="{position: 'absolute', left: 0, top: 0, width: '100%', height: '100%', background: '#9f9f9f', opacity: 0.1, zIndex: 99998}" @click="hideNoticeBox"></div>
        <a-card title="通知消息" :bordered="true" :style="{position: 'absolute', right: '100px', top: '30px', width: '400px', zIndex: 99999, boxShadow: '0 0 10px #ccc'}">
            <a-icon type="close" slot="extra" @click="hideNoticeBox" :style="{cursor: 'pointer'}"/>

            <a-list :dataSource="noticesData" itemLayout="horizontal" :style="{overflow: 'auto', maxHeight: '500px'}">
                <a-list-item slot="renderItem" slot-scope="item, index">
                    <a slot="actions">已读</a>
                    <a slot="actions">详情</a>
                    <a-list-item-meta :description="item.description">
                        <a slot="title" href="#">{{item.title}}</a>
                    </a-list-item-meta>
                </a-list-item>

                <div slot="loadMore" :style="{textAlign: 'center', marginTop: '10px'}">
                    <div :style="{display: hasMore?'block':'none'}">
                        <a-spin v-if="noticeLoading"></a-spin>
                        <a v-else @click="loadMoreNotices">加载更多...</a>
                    </div>
                    <div :style="{display: hasMore?'none':'block'}">没有更多了</div>
                </div>
            </a-list>
        </a-card>
    </div>

</template>

<script>
    import {mapState, mapGetters, mapMutations, mapActions} from 'vuex'

    import {layout} from '@/store/types'

    export default {
        name: "NoticeBox",
        computed: {
            ...mapState({
                noticeBoxVisible: state => state.layout.noticeBoxVisible,
                noticeLoading: state => state.layout.noticeLoading,
                noticesData: state => state.layout.noticesData,
                hasMore: state => state.layout.hasMore
            }),
            ...mapGetters({

            })
        },
        methods: {
            ...mapMutations({
                hideNoticeBox: layout.HIDE_NOTICE_BOX
            }),
            ...mapActions({
                loadMoreNotices: layout.LOAD_MORE_NOTICES
            })
        }
    }
</script>

<style scoped>

</style>