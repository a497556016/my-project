<template>
    <div class="title-bar">
        <div class="left-color" v-if="leftWidth" :style="{width: leftWidth+'px'}"></div>
        {{title}}
    </div>
</template>

<script>
    export default {
        name: "TitleBar",
        props: {
            title: String,
            leftWidth: {
                type: Number,
                default: 5
            }
        },
    }
</script>

<style scoped lang="less">
    @import "../../assets/style/index";
    .title-bar {
        display: flex;

        .left-color{
            background: @primary-color;
        }
    }
</style>