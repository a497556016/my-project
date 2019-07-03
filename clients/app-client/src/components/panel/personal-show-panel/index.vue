<template>
    <div class="personal-show-panel">
        <div class="sign-words">{{signWords}}</div>
        <img class="back-img" v-if="backImg" :src="backImg"/>
        <img class="avatar" :src="avatar"/>

    </div>
</template>

<script>
    export default {
        name: "PersonalShowPanel",
        props: {
            signWords: String,
            backImg: String,
            avatar: String
        }
    }
</script>

<style scoped lang="less">
    @import "../../../assets/style/index";
    .personal-show-panel {
        position: relative;
        height: 350px;
        width: 100%;
        background: @primary-color;
        .sign-words {
            position: absolute;
            bottom: -28px;
            right: 70px;
            font-size: 13px;
            color: @thin-text-color;
            padding: 5px 15px;
        }
        .back-img{
            position: absolute;
            left: 0;
            right: 0;
            bottom: 0;
            top: 0;
            width: 100%;
            height: 100%;
        }
        .avatar {
            border: 0.02em solid @thin-text-color;
            border-radius: 5px;
            width: 60px;
            height: 60px;
            position: absolute;
            bottom: -25px;
            right: 15px;
            background: white;
        }
    }
</style>