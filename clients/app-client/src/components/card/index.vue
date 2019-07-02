<template>
    <div class="my-card" :style="cardStyle">
        <div class="title">
            <slot name="title"></slot>
        </div>
        <div class="content">
            <slot>

            </slot>
        </div>
        <div class="footer">
            <slot name="footer"></slot>
        </div>
    </div>
</template>

<script>
    const cardStyle = {borderTop: '0.02em solid #cddddd', borderBottom: '0.02em solid #cdcdcd'};
    export default {
        name: "MyCard",
        props: {
            marginSpace: {
                type: Number,
                default: 0
            },
            borderRadius: {
                type: Number,
                default: 2
            }
        },
        data(){
            if(this.marginSpace) {
                cardStyle.marginLeft = this.marginSpace + 'px';
                cardStyle.marginRight = this.marginSpace + 'px';
            }
            cardStyle.borderRadius = this.borderRadius + 'px';
            return {
                cardStyle
            }
        },
        mounted() {

        }
    }
</script>

<style scoped lang="less">
    @import "../../assets/style/index";
    .my-card {
        position: relative;
        /*width: 100%;*/
        margin-bottom: 15px;
        padding: 15px 15px;
        background: white;
        .title {
            font-size: 16px;
            font-weight: bold;
        }

        .content {
            /*padding: 10px 0;*/
            margin-top: 5px;
            margin-bottom: 5px;
            font-size: 13px;
            width: 100%;
            /*border-top: 0.01em solid #eee;*/
        }

        .footer {
            font-size: 13px;
        }
    }
</style>