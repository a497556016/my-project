<template>
    <layout-menu-bar :style="{ margin: '16px' }"></layout-menu-bar>
</template>

<script>
    import LayoutMenuBar from './MenuBar'
    export default {
        name: "Content",
        components: {
            'layout-menu-bar': LayoutMenuBar
        }
    }
</script>

<style scoped>

</style>