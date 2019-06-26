<template>
    <div>
        <hello-world :msg="'你好啊！'"></hello-world>
    </div>
</template>

<script>
    import HelloWorld from "../components/HelloWorld";
    export default {
        name: "Main",
        components: {HelloWorld}
    }
</script>

<style scoped>

</style>